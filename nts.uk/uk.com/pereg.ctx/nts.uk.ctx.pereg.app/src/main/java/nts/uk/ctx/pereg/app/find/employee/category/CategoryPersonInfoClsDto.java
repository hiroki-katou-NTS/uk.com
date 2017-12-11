package nts.uk.ctx.pereg.app.find.employee.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPersonInfoClsDto {
	private String personInfoCategoryID;
	private String categoryCode;
	private String categoryName;
	private int personEmployeeType;
	private int isAbolition;
	private int categoryType;
	private int isFixed;

	private CategoryPersonInfoClsDto(PersonInfoCategory personInfoCategory) {
		this.personInfoCategoryID = personInfoCategory.getPersonInfoCategoryId();
		this.categoryCode = personInfoCategory.getCategoryCode().v();
		this.categoryName = personInfoCategory.getCategoryName().v();
		this.personEmployeeType = personInfoCategory.getPersonEmployeeType().value;
		this.isAbolition = personInfoCategory.getIsAbolition().value;
		this.categoryType = personInfoCategory.getCategoryType().value;
		this.isFixed = personInfoCategory.getIsFixed().value;
	}
	
	public static CategoryPersonInfoClsDto createObjectFromDomain(PersonInfoCategory personInfoCategory) {
		return new CategoryPersonInfoClsDto(personInfoCategory);
	}

}
