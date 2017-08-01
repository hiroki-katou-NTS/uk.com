package nts.uk.ctx.bs.person.dom.person.info.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class PersonInfoCategory extends AggregateRoot {
	private String personInfoCategoryId;
	private String companyId;
	private CategoryCode categoryCode;
	private CategoryCode categoryParentCode;
	private CategoryName categoryName;
	private PersonEmployeeType personEmployeeType;
	private IsUsed isUsed;
	private CategoryType categoryType;
	private IsFixed isFixed;

	public static PersonInfoCategory createFromJavaType(String personInfoCategoryId, String companyId,
			String categoryCode, String categoryParentCode, String categoryName, int personEmployeeType, int isUsed,
			int categoryType, int isFixed) {
		return new PersonInfoCategory(personInfoCategoryId, companyId, new CategoryCode(categoryCode),
				new CategoryCode(categoryParentCode), new CategoryName(categoryName),
				EnumAdaptor.valueOf(personEmployeeType, PersonEmployeeType.class),
				EnumAdaptor.valueOf(isUsed, IsUsed.class), EnumAdaptor.valueOf(categoryType, CategoryType.class),
				EnumAdaptor.valueOf(isFixed, IsFixed.class));
	}

}
