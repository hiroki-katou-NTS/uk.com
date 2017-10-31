package find.person.info.category;

import lombok.Value;

@Value
public class PerInfoCtgFullDto {
	private String id;
	private String categoryCode;
	private String categoryName;
	private int personEmployeeType;
	private int isAbolition;
	private int categoryType;
	private int isFixed;

}
