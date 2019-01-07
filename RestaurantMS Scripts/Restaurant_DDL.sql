drop database restaurant;

CREATE DATABASE Restaurant;

USE Restaurant;

#Creates entities / relations tables
CREATE TABLE Staff(
	Staff_ID INT,
    Job_Title VARCHAR(10) NOT NULL,
    First_Name VARCHAR(20) NOT NULL,
    Last_Name VARCHAR(20),
    Gender VARCHAR(6) NOT NULL,
	Street VARCHAR(30) NOT NULL,
    City VARCHAR(20) NOT NULL,
    State VARCHAR(20) NOT NULL,
    Zip INT NOT NULL,
    Phone_Number VARCHAR(20),
    Pay_Rate FLOAT NOT NULL, 
    Start_Date DATE,
    Kitchen_Role VARCHAR(15),
    Monthly_Bonus FLOAT DEFAULT NULL,
    PRIMARY KEY(Staff_ID)
    );
    
CREATE TABLE Shifts(
	Shift_ID INT,
    Days VARCHAR(15) NOT NULL,
    Shift_Time VARCHAR(10) NOT NULL,
    PRIMARY KEY(Shift_ID)
    );
    

CREATE TABLE Order_Info(
	Order_Number INT,
    Order_Date DATE NOT NULL, 
    Tip_Amount FLOAT,
    Order_Total FLOAT NOT NULL,
    Payment_Method VARCHAR(10) DEFAULT 'card',
    PRIMARY KEY (Order_Number)
    );
    
    
CREATE TABLE Customer(
	Customer_ID INT,
    First_Name VARCHAR(20) NOT NULL,
    Last_Name VARCHAR(20),
    Phone_Number VARCHAR(20),
    PRIMARY KEY(Customer_ID)
    );

CREATE TABLE Seats(
	Table_Number INT,
    Table_Type VARCHAR(20) NOT NULL,
    Num_Of_Seats INT NOT NULL,
    PRIMARY KEY (Table_Number)
    );
    
CREATE TABLE Menu_Item(
	Menu_Item_Number INT,
    Item_Name VARCHAR(30) NOT NULL,
    Item_Type VARCHAR(15) NOT NULL,
    Price FLOAT NOT NULL,
    Gluten_Free BOOLEAN DEFAULT NULL,
    Contain_Nuts BOOLEAN DEFAULT NULL,
    Ingredients VARCHAR(100),
    PRIMARY KEY (Menu_Item_Number)
    );
    
    
# Creates relationship tables

CREATE TABLE Shift_Assignment(
	Staff INT,
    Shift INT,
    FOREIGN KEY (Staff) REFERENCES Staff(Staff_id),
    FOREIGN KEY (Shift) REFERENCES Shifts(Shift_id)
    );
    
CREATE TABLE Supervises(
	Manager INT CHECK (Manager IN (SELECT Staff_ID FROM Staff WHERE Job_Title = 'Manager')),
    Employee INT CHECK(Employee IN( SELECT Staff_ID FROM Staff WHERE Job_Title <> 'Manager'))
    );
    
CREATE TABLE Handles(
	Staff INT,
    Order_Info INT
    );
    
CREATE TABLE Cust_Orders(
	Customer INT,
    Seats INT,
    Order_Info INT
	);
    
    
CREATE TABLE OrderLine(
	Order_Info INT,
    Menu_Item INT,
    Quantity INT
    );


ALTER TABLE Customer MODIFY Customer_ID INT AUTO_INCREMENT;
ALTER TABLE order_info MODIFY order_number INT AUTO_INCREMENT;


# Indexes 
CREATE INDEX StaffInd ON Staff(Staff_ID, Job_Title);
CREATE INDEX ShiftAssigInd ON Shift_Assignment(Shift);
CREATE INDEX ShiftInd ON Shifts(Days, Shift_Time);
CREATE INDEX MenuInd ON Menu_Item(Menu_Item_Number,item_type);
CREATE INDEX OrderLineInd ON OrderLine(Menu_Item);


# Triggers -------------------------------------------------------------------------------------

CREATE TABLE menu_item_audit(
	menu_item_number INT,
    item_name VARCHAR(50),
    changedat DATETIME DEFAULT NULL,
    action VARCHAR(50) DEFAULT NULL
);

DELIMITER $$
	CREATE TRIGGER before_menu_item_update
		BEFORE UPDATE ON menu_item
        FOR EACH ROW
	BEGIN 
		INSERT INTO menu_item_audit
        SET action = 'update',
			menu_item_number = OLD.menu_item_number,
            item_name = OLD.item_name,
            changedat = NOW();
	END$$
DELIMITER ;

CREATE TABLE HistoryEmployeePayRaise(
	staff_id INT,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    old_pay_rate FLOAT,
    new_pay_rate FLOAT
);

DELIMITER $$
	CREATE TRIGGER EmployeeRaiseTrig
    BEFORE UPDATE ON Staff
    FOR EACH ROW
    BEGIN
		IF(NEW.pay_rate > OLD.pay_rate + .50) THEN
			INSERT INTO HistoryEmployeePayRaise SET
            staff_ID = OLD.staff_id,
            first_name = OLD.first_name,
            last_name = OLD.last_name,
            old_pay_rate = OLD.pay_rate,
            new_pay_rate = NEW.pay_rate;
		END IF;
	END$$
DELIMITER ;

CREATE TABLE Order_Audit( 
    Order_Total FLOAT,
          Action VARCHAR(100) DEFAULT NULL
);

DELIMITER $$
	CREATE TRIGGER AmountCheckTrig
		BEFORE INSERT ON Order_Info
		FOR EACH ROW
        BEGIN
			IF(NEW.Order_Total < 10.00) THEN
				INSERT INTO Order_Audit VALUES(NEW.Order_Total, 'Order Total didnt qualify due to not hitting $10 purchase');
			END IF;
	END$$
DELIMITER ;


# Procedures -----------------------------------------------------------------------------------

# Returns the total revenue of the restaurant 
DELIMITER //
CREATE PROCEDURE GetTotalRevenue()
	BEGIN
		DECLARE total_revenue FLOAT DEFAULT 0;
		SELECT SUM(order_total) INTO total_revenue
		FROM order_info;
        
        SELECT total_revenue;
    END//
DELIMITER ;

#Returns the total amount ordered for a particular menu item 
DELIMITER //
CREATE PROCEDURE GetTotalQuantityOrdered(IN itemName VARCHAR(50))
	BEGIN 
    DECLARE item_num INT;
    DECLARE total_quantity INT DEFAULT 0;
    
    SELECT menu_item_number INTO item_num
    FROM Menu_Item
    WHERE itemName = Menu_Item.item_name;
    
    SELECT SUM(quantity) INTO total_quantity
    FROM OrderLine 
    WHERE item_num = OrderLine.menu_item;
    
    SELECT total_quantity;
END//
DELIMITER ; 

#Returns the orderline (items ordered and their quantity) for a certain order
DELIMITER //
CREATE PROCEDURE GetOrderLine(IN orderNum INT)
	BEGIN
		SELECT Item_Name, Quantity 
        FROM OrderLine JOIN Menu_Item ON OrderLine.menu_item = Menu_Item.menu_item_number
        WHERE order_info = orderNum;
	END//
DELIMITER ;

#Returns significant order details for order history 
DELIMITER //
CREATE PROCEDURE GetOrderDetails()
	BEGIN 
		SELECT Customer.first_name, Customer.last_name, Seats.table_number, Order_Info.order_number, Order_Info.order_date, Order_Info.tip_amount, Order_Info.order_total, Order_Info.payment_method 
		FROM Cust_Orders 
			INNER JOIN Customer ON Cust_Orders.customer = Customer.customer_id 
			INNER JOIN Seats ON Cust_Orders.seats = Seats.table_number 
			INNER JOIN Order_Info ON Cust_Orders.order_info = Order_Info.order_number;
    END//
DELIMITER ;

#Inserts customer into customer table if customer does not exist
DELIMITER // 
CREATE PROCEDURE CustomerInsert(IN firstName VARCHAR(20), IN lastName VARCHAR(20))
	BEGIN 
		IF (SELECT * FROM Customer WHERE first_name = firstName AND last_name = lastName) <> null THEN
			INSERT INTO Customer VALUES (firstName, lastName);
        END IF;
    END//
DELIMITER ; 

#Returns menu items according to item type argument 
DELIMITER //
CREATE PROCEDURE GetItemsBasedOnCategory(IN itemType VARCHAR(20))
	BEGIN
		SELECT * FROM Menu_Item WHERE item_type = itemType;
    END//
DELIMITER ;

# Views ----------------------------------------------------------------------------------


CREATE VIEW aboveAvgFoodItems AS
	SELECT Menu_Item.menu_item_number, Menu_Item.item_name
    FROM Menu_Item, OrderLine
    WHERE Menu_Item.menu_item_number = OrderLine.Menu_Item AND OrderLine.quantity > 
		(SELECT AVG(quantity)
        FROM OrderLine);
        
CREATE VIEW MainEntreeInfor AS
	SELECT Item_Name, Gluten_Free, Contain_Nuts, Ingredients
    FROM Menu_Item
    WHERE Item_Type = 'Main Entree';

CREATE VIEW OrderDetails AS 
	SELECT Customer.first_name, Customer.last_name, Seats.table_number, Order_Info.order_number, Order_Info.order_date, Order_Info.tip_amount, Order_Info.order_total, Order_Info.payment_method 
		FROM Cust_Orders 
			INNER JOIN Customer ON Cust_Orders.customer = Customer.customer_id 
			INNER JOIN Seats ON Cust_Orders.seats = Seats.table_number 
			INNER JOIN Order_Info ON Cust_Orders.order_info = Order_Info.order_number;
