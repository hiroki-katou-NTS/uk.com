package command.person.info.item;

import lombok.Value;

@Value
public class UpdateItemChangeCommand {

	private String id;
	private String itemName;
	private int isAbolition;
	private int isRequired;

}
