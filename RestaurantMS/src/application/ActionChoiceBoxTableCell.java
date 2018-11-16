package application;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class ActionChoiceBoxTableCell<S> extends TableCell<S, ChoiceBox<Integer>> {

	private final ChoiceBox<Integer> choiceBox;
	
	public ActionChoiceBoxTableCell(){
		this.getStyleClass().add("action-choice-box-table-cell");
		
		this.choiceBox = new ChoiceBox<>();
		choiceBox.getItems().addAll(1,2,3,4,5,6);
		//choiceBox.getSelectionModel().select(orderLine.getQuantity());
        this.choiceBox.setMaxWidth(Double.MAX_VALUE);
	}
	
	public int getCurrentChoiceBoxSelection(){
		return choiceBox.getValue();
	}
	
	public void setChoiceBoxSelection(int n){
		choiceBox.getSelectionModel().select(n);
	}
	
	public static <S> Callback<TableColumn<S, ChoiceBox<Integer>>, TableCell<S, ChoiceBox<Integer>>> forTableColumn() {
        return param -> new ActionChoiceBoxTableCell<>();
    }
	
    @Override
    public void updateItem(ChoiceBox<Integer> item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {                
            setGraphic(choiceBox);
        }
    }
	  
	  
	
}
