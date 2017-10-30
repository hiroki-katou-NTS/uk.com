package nts.uk.ctx.bs.employee.app.find.employee.category;

import lombok.Value;

@Value
public class PersonInfoCtgFullDto {
	private String id;
	private String categoryCode;
	private String categoryName;
	private int personEmployeeType;
	private int isAbolition;
	private int categoryType;
	private int isFixed;

}
