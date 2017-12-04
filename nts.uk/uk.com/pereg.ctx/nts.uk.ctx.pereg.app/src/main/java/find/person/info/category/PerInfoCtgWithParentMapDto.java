package find.person.info.category;

import lombok.Value;

@Value
public class PerInfoCtgWithParentMapDto {
	private String id;
	private String categoryCode;
	private String categoryName;
	private int personEmployeeType;
	private int isAbolition;
	private int categoryType;
	private int isFixed;
	private int isParent;
}

