package nts.uk.ctx.pereg.app.find.person.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PerInfoCtgFullDto {
	private String id;
	private String categoryCode;
	private String ctgParentCode;
	private String categoryName;
	private int personEmployeeType;
	private int isAbolition;
	private int categoryType;
	private int isFixed;
	
	public PerInfoCtgFullDto(String id, String categoryCode, String categoryName, int personEmployeeType, int isAbolition, int categoryType, int isFixed){
		this.id = id;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.personEmployeeType = personEmployeeType;
		this.isAbolition = isAbolition;
		this.categoryType = categoryType;
		this.isFixed = isFixed;
	}
}
