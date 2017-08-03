package nts.uk.ctx.bs.person.dom.person.info.category;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;

@Getter
public class PersonInfoCategory extends AggregateRoot {
	private String personInfoCategoryId;
	private String companyId;
	private CategoryCode categoryCode;
	private CategoryCode categoryParentCode;
	private CategoryName categoryName;
	private PersonEmployeeType personEmployeeType;
	private IsAbolition isAbolition;
	private CategoryType categoryType;
	private IsFixed isFixed;

	private PersonInfoCategory(String companyId, String categoryCode, String categoryParentCode, String categoryName,
			int personEmployeeType, int isAbolition, int categoryType, int isFixed) {
		super();
		this.personInfoCategoryId = IdentifierUtil.randomUniqueId();
		this.companyId = companyId;
		this.categoryCode = new CategoryCode(categoryCode);
		this.categoryParentCode = new CategoryCode(categoryParentCode);
		this.categoryName = new CategoryName(categoryName);
		this.personEmployeeType = EnumAdaptor.valueOf(personEmployeeType, PersonEmployeeType.class);
		this.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		this.categoryType = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		this.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
	}

	private PersonInfoCategory(String personInfoCategoryId, String companyId, String categoryCode,
			String categoryParentCode, String categoryName, int personEmployeeType, int isAbolition, int categoryType,
			int isFixed) {
		super();
		this.personInfoCategoryId = personInfoCategoryId;
		this.categoryCode = new CategoryCode(categoryCode);
		this.categoryParentCode = new CategoryCode(categoryParentCode);
		this.categoryName = new CategoryName(categoryName);
		this.personEmployeeType = EnumAdaptor.valueOf(personEmployeeType, PersonEmployeeType.class);
		this.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		this.categoryType = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		this.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
	}

	public static PersonInfoCategory createFromJavaType(String companyId, String categoryCode,
			String categoryParentCode, String categoryName, int personEmployeeType, int isAbolition, int categoryType,
			int isFixed) {
		return new PersonInfoCategory(companyId, categoryCode, categoryParentCode, categoryName, personEmployeeType,
				isAbolition, categoryType, isFixed);
	}

	public static PersonInfoCategory createFromEntity(String personInfoCategoryId, String companyId,
			String categoryCode, String categoryParentCode, String categoryName, int personEmployeeType,
			int isAbolition, int categoryType, int isFixed) {
		return new PersonInfoCategory(personInfoCategoryId, companyId, categoryCode, categoryParentCode, categoryName, personEmployeeType, isAbolition, categoryType, isFixed);
	}
}
