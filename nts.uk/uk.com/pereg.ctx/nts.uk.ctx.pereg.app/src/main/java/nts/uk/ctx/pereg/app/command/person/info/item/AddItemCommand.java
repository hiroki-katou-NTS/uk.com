package nts.uk.ctx.pereg.app.command.person.info.item;

import lombok.Value;

@Value
public class AddItemCommand {
	
	private String perInfoCtgId;
	
	private String itemCode;
	
	private String itemParentCode;
	
	private String itemName;
	
	private SingleItemCommand singleItem;
	
	private int personEmployeeType;
	
}
