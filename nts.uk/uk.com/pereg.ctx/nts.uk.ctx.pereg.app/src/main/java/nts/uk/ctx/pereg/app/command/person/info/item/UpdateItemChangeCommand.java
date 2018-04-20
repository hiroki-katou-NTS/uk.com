package nts.uk.ctx.pereg.app.command.person.info.item;

import lombok.Value;

@Value
public class UpdateItemChangeCommand {
	
	private String id;
	
	private String itemName;
	
	private int isAbolition;
	
	private int isRequired;
	
	private Integer dataType;
	
	private String selectionItemId;
	
	private int personEmployeeType;
	
}