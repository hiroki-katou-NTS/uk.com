package find.person.info.item;

import lombok.Value;

@Value
public class PerInfoItemDefMapDto {
	private String id;
	private String perInfoCtgId;
	private String itemName;
	private boolean alreadyItemDefCopy;
}
