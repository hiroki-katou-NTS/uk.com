package nts.uk.ctx.pereg.app.command.person.info.item;

import lombok.Value;

@Value
public class UpdateItemCommand {
	private String perInfoItemDefId;
	private String perInfoCtgId;
	private String itemName;
	private SingleItemCommand singleItem;
	private int personEmployeeType;
}
