package find.person.info.item;

import lombok.Value;

@Value
public class PerInfoItemDefNewLayoutDto {
	private String Id;
	private String perInfoCtgId;
	private String itemCode;
	private String itemName;
	private int isAbolition;
	private int isFixed;
	private int isRequired;
	private int systemRequired;
	private int requireChangable;
	private ItemTypeStateDto itemTypeState;
}
