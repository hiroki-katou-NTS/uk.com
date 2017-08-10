package command.person.info.item;

import lombok.Value;

@Value
public class AddItemCommand {
	private String perInfoCtgId;
	private String itemCode;
	private String itemParentCode;
	private String itemName;
	private int itemType;
	private SingleItemCommand singleItem;
	
}
