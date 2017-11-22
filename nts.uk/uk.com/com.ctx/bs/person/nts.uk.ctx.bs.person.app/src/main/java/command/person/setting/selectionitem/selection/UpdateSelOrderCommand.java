package command.person.setting.selectionitem.selection;

import lombok.Value;
@Value
public class UpdateSelOrderCommand {

	private String selectionID;
	private String histId;
	private int dispOrder;
	private boolean initSelection; 
}
