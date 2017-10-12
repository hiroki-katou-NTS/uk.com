package find.person.info.item;

import lombok.Value;

@Value
public class PerInfoCopyItemDto {
	private String id;
	private String itemName;
	private ItemTypeStateDto itemTypeState;
	private int isRequired;

	

}
