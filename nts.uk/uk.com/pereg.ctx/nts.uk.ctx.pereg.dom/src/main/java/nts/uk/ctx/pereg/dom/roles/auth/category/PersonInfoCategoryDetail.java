package nts.uk.ctx.pereg.dom.roles.auth.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonInfoCategoryDetail {
	private String categoryId;

	private String categoryCode;
	
	private String categoryName;
	
	private int categoryType;
	
	private int allowPersonRef;
	
	private int allowOtherRef;
	
	private int personEmployeeType;
	
	private boolean isSetting;	
}
