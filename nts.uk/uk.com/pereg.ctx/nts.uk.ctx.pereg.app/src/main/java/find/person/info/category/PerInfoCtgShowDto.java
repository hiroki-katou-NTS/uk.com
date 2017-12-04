package find.person.info.category;

import lombok.Value;

@Value
public class PerInfoCtgShowDto {
	private String id;
	private String categoryName;
	private int categoryType;
	private int isAbolition;
	private String categoryParentCode;
}
