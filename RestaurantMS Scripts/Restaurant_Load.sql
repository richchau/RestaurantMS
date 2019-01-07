LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Order_Info.txt' INTO TABLE Order_Info;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Customer.txt' INTO TABLE Customer;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Seats.txt' INTO TABLE Seats;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Menu_Item.txt' INTO TABLE Menu_Item;




INSERT INTO Staff
Values (10, 'Bartender', 'Smith', 'Louis', 'Male', '2559 Belmont Ave', 'San Jose', 'CA', 95181, '408-893-7712', 18.00, '2018-01-27', NULL, NULL);
INSERT INTO Staff
Values (11, 'Bartender', 'Tymon', 'Carideo', 'Male', '2558 Cruz Ave.', 'San Jose', 'CA', 95137, '408-235-1053', 18.00, '2018-01-27', NULL, NULL);
INSERT INTO Staff
Values (20, 'Waiter', 'Jeanna', 'August', 'Female', '2557 Snell Ave.', 'San Jose', 'CA', 95181, '408-678-3547', 15.00, '2018-01-27', NULL, NULL);
INSERT INTO Staff
Values (21, 'Waiter', 'Linda', 'Toyota', 'Female', '2551 Cruz Ave.', 'San Jose', 'CA', 95181, '408-956-7149', 15.00, '2018-01-27', NULL, NULL);
INSERT INTO Staff
Values (22, 'Waiter', 'Tom', 'Lasang', 'Male', '2552 Santa Ave.', 'San Jose', 'CA', 95136, '408-477-9086', 15.00, '2018-04-03', NULL, NULL);
INSERT INTO Staff
Values (23, 'Waiter', 'Judy', 'Plaskett', 'Female', '2553 Cruz Ave.', 'San Jose', 'CA', 95181, '408-988-4783', 15.00, '2018-04-07', NULL, NULL);
INSERT INTO Staff
Values(30,'Chef', 'Stephen',	'Curry',	'Male',	'2554 Virginia Ave.',	'San Jose',	'CA',	 95181,	'408-200-8268',	30.00,	'2018-01-27',	'Master Chef', NULL);
INSERT INTO Staff
Values(31,	'Chef',	'Fredo',	'McGray',	'Male',	'2578 First St.', 	'San Jose',	'CA',	 95182,	'408-371-9168',	25.00,	'2018-01-27',	'Second Chef',	NULL);
INSERT INTO Staff
Values(32,	'Chef',	'Sonny',	'Mcfee',	'Male',	'2599 Parson Ct.', 	'San Jose',	'CA',	 95181,	'408-923-7543',	22.00,	'2018-05-06',	'Line Chef',	NULL);
INSERT INTO Staff
Values(33,	'Chef',	'Lida', 	'Pooja',	'Female',	'2588 San Fernando.', 	'San Jose',	'CA',	 95182,	'408-861-3705',	22.00,	'2018-07-20',	'Line Chef',	NULL);
INSERT INTO Staff
Values(40,	'Manager',	'Mathias',	'Silva', 'Male',	'2585 Cruz Ave.', 	'San Jose',	'CA',	95136,	'408-266-5756',	35.00,	'2018-01-27',	NULL,  350.00);
INSERT INTO Staff
Values(41,	'Manager',	'Valeria',	'Patterson',	'Female',	'2581 San Fernando.',	'San Jose',	'CA',	 95182,	'408-868-9015',	35.00,	'2018-01-27',	NULL,	275.00);

INSERT INTO Shifts
Values(1, 'Mon-Th', 'Noon');
INSERT INTO Shifts
Values(2, 'Mon-Th', 'Night');
INSERT INTO Shifts
Values(3, 'Fr-Sun', 'Noon');
INSERT INTO Shifts
Values(4, 'Fr-Sun', 'Night');
INSERT INTO Shifts
Values(5, 'Mon-Sun', 'All-Day');
INSERT INTO Shifts
Values(6, 'Mon-Th', 'All-Day');
INSERT INTO Shifts
Values(7, 'Fr-Sun', 'All-Day');

LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Shift_Assignment.txt' INTO TABLE Shift_Assignment;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Supervises.txt' INTO TABLE Supervises;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Handles.txt' INTO TABLE Handles;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Cust_Orders.txt' INTO TABLE Cust_Orders;
LOAD DATA INFILE 'C:/ProgramData/MySQL/MySQL Server 5.7/Uploads/Order_Line.txt' INTO TABLE OrderLine;


