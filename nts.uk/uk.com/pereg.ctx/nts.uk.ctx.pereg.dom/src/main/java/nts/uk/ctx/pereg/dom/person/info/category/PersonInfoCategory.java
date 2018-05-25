package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;

@Getter
public class PersonInfoCategory extends AggregateRoot {
	
	/**
	 * 個人情報カテゴリID
	 */
	private String personInfoCategoryId;
	
	/**
	 * カテゴリコード
	 */
	private CategoryCode categoryCode;
	
	/**
	 * カテゴリ名
	 */
	private CategoryName categoryName;
	
	/**
	 * 会社ID
	 */
	private String companyId;
	
	/**
	 * 既定区分
	 */
	private IsFixed isFixed;
	
	/**
	 * 個人・社員区分
	 */
	private PersonEmployeeType personEmployeeType;
	
	/**
	 * 種類
	 */
	private CategoryType categoryType;
	
	/**
	 * 廃止区分
	 */
	private IsAbolition isAbolition;
	
	/**
	 * 個人情報カテゴリ親ID
	 */
	private CategoryCode categoryParentCode;
	
	private boolean alreadyCopy;
	
	/**
	 * 初期値マスタ対象区分
	 */
	private InitValMasterObjCls initValMasterCls;
	
	/**
	 * 項目追加対象区分
	 */
	private AddItemObjCls addItemCls;

	private PersonInfoCategory(String companyId, String categoryCode, String categoryName, int categoryType) {
		super();
		this.personInfoCategoryId = IdentifierUtil.randomUniqueId();
		this.companyId = companyId;
		this.categoryCode = new CategoryCode(categoryCode);
		this.categoryParentCode = null;
		this.categoryName = new CategoryName(categoryName);
		this.personEmployeeType = PersonEmployeeType.EMPLOYEE;
		this.isAbolition = IsAbolition.NOT_ABOLITION;
		this.categoryType = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		this.isFixed = IsFixed.NOT_FIXED;
		this.initValMasterCls = InitValMasterObjCls.INIT;
		this.addItemCls = AddItemObjCls.ENABLE;
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
	
	// đối ứng cho việc thêm 2 trường mới initObj, addObj
	private PersonInfoCategory(String personInfoCategoryId, String companyId, String categoryCode,
			String categoryParentCode, String categoryName, int personEmployeeType, int isAbolition, int categoryType,
			int isFixed, int addObj, int initObj) {
		super();
		this.personInfoCategoryId = personInfoCategoryId;
		this.categoryCode = new CategoryCode(categoryCode);
		this.categoryParentCode = new CategoryCode(categoryParentCode);
		this.categoryName = new CategoryName(categoryName);
		this.personEmployeeType = EnumAdaptor.valueOf(personEmployeeType, PersonEmployeeType.class);
		this.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		this.categoryType = EnumAdaptor.valueOf(categoryType, CategoryType.class);
		this.isFixed = EnumAdaptor.valueOf(isFixed, IsFixed.class);
		this.addItemCls = EnumAdaptor.valueOf(addObj, AddItemObjCls.class);
		this.initValMasterCls = EnumAdaptor.valueOf(initObj, InitValMasterObjCls.class);
	}

	private PersonInfoCategory(String personInfoCategoryId, String companyId, int categoryType) {
		super();
		this.personInfoCategoryId = personInfoCategoryId;
		this.companyId = companyId;
		this.categoryType = EnumAdaptor.valueOf(categoryType, CategoryType.class);
	}
	
	private PersonInfoCategory(){
		super();
	}

	public static PersonInfoCategory createFromJavaType(String companyId, String categoryCode, String categoryName,
			int categoryType) {
		return new PersonInfoCategory(companyId, categoryCode, categoryName, categoryType);
	}

	public static PersonInfoCategory createFromEntity(String personInfoCategoryId, String companyId,
			String categoryCode, String categoryParentCode, String categoryName, int personEmployeeType,
			int isAbolition, int categoryType, int isFixed,
			int addObj, int initObj) {
		return new PersonInfoCategory(personInfoCategoryId, companyId, categoryCode, categoryParentCode, categoryName,
				personEmployeeType, isAbolition, categoryType, isFixed, addObj, initObj);
	}
	
	public static PersonInfoCategory createFromEntity(String personInfoCategoryId, String companyId,
			String categoryCode, String categoryParentCode, String categoryName, int personEmployeeType,
			int isAbolition, int categoryType, int isFixed) {
		return new PersonInfoCategory(personInfoCategoryId, companyId, categoryCode, categoryParentCode, categoryName,
				personEmployeeType, isAbolition, categoryType, isFixed);
	}

	public static PersonInfoCategory createFromJavaTypeUpdate(String personInfoCategoryId, String companyId,
			int categoryType) {
		return new PersonInfoCategory(personInfoCategoryId, companyId, categoryType);
	}
	
	public static PersonInfoCategory createDomainWithAbolition(String ctgId, String ctgCd, String name){
		PersonInfoCategory p = new PersonInfoCategory();
		p.personInfoCategoryId = ctgId;
		p.categoryName = new CategoryName(name);
		p.categoryCode = new CategoryCode(ctgCd);
		return p;
	}
	public void setDomainNameAndAbolition(CategoryName name, int isAbolition){
		this.isAbolition = EnumAdaptor.valueOf(isAbolition, IsAbolition.class);
		this.categoryName = name;
	}
	
	public static List<PersonInfoCategory> getAllPerInfoCategoryWithCondition(List<PersonInfoCategory> lstObj){
		return lstObj.stream().filter(obj -> 
			obj.getPersonEmployeeType() == PersonEmployeeType.EMPLOYEE
			&& obj.getIsAbolition() == IsAbolition.NOT_ABOLITION
			&& (obj.getCategoryType() != CategoryType.MULTIINFO
			|| obj.getCategoryType() != CategoryType.DUPLICATEHISTORY)
		).collect(Collectors.toList());
	}

	public void setCategoryName(String name) {
		this.categoryName = new CategoryName(name);
	}
	
	public void setCategoryType(int categoryType) {
		this.categoryType = EnumAdaptor.valueOf(categoryType, CategoryType.class);
	}

	public PersonInfoCategory(String personInfoCategoryId, CategoryCode categoryCode, CategoryName categoryName,
			String companyId, IsFixed isFixed, PersonEmployeeType personEmployeeType, CategoryType categoryType,
			IsAbolition isAbolition, CategoryCode categoryParentCode) {
		super();
		this.personInfoCategoryId = personInfoCategoryId;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
		this.companyId = companyId;
		this.isFixed = isFixed;
		this.personEmployeeType = personEmployeeType;
		this.categoryType = categoryType;
		this.isAbolition = isAbolition;
		this.categoryParentCode = categoryParentCode;
		this.initValMasterCls = InitValMasterObjCls.INIT;
		this.addItemCls = AddItemObjCls.ENABLE;
	}
	
	public boolean isFixed() {
		return this.isFixed == IsFixed.FIXED;
	}
	
	public boolean isSingleCategory() {
		return categoryType == CategoryType.SINGLEINFO;
	}
	
	public boolean isMultiCategory() {
		return categoryType == CategoryType.MULTIINFO;
	}
	
	public boolean isHistoryCategory() {
		return categoryType == CategoryType.CONTINUOUSHISTORY 
				|| categoryType == CategoryType.NODUPLICATEHISTORY
				|| categoryType == CategoryType.DUPLICATEHISTORY
				|| categoryType == CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE;
	}
	
	public boolean isEmployeeType() {
		return personEmployeeType == PersonEmployeeType.EMPLOYEE;
	}
	
	public boolean isPersonType() {
		return personEmployeeType == PersonEmployeeType.PERSON;
	}
	
}
