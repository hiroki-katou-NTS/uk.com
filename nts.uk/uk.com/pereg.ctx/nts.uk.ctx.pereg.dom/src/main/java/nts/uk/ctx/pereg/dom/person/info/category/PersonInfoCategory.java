package nts.uk.ctx.pereg.dom.person.info.category;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.enumcommon.NotUseAtr;
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
	
	/**
	 * 給与利用区分
	 */
	private NotUseAtr salaryUseAtr;
	
	/**
	 * 人事利用区分
	 */
	private NotUseAtr personnelUseAtr;
	
	/**
	 * 就業利用区分
	 */
	private NotUseAtr employmentUseAtr;
	
	/**
	 * 廃止切り替え可能か 
	 **/
	private boolean canAbolition;
	
	


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
		this.employmentUseAtr = NotUseAtr.USE;
		this.salaryUseAtr = NotUseAtr.USE;
		this.personnelUseAtr = NotUseAtr.USE;
		this.canAbolition = true;
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
	
	// đối ứng cho việc thêm 4 trường 	// đối ứng cho việc thêm 2 trường mới salaryAtr, employmentAtr, personelAtr, canAbolish
	private PersonInfoCategory(String personInfoCategoryId, String companyId, String categoryCode,
			String categoryParentCode, String categoryName, int personEmployeeType, int isAbolition, int categoryType,
			int isFixed, int addObj, int initObj, int salaryAtr, int employmentAtr, int personelAtr, int canAbolish) {
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
		this.salaryUseAtr = EnumAdaptor.valueOf(salaryAtr, NotUseAtr.class);
		this.personnelUseAtr = EnumAdaptor.valueOf(personelAtr, NotUseAtr.class);
		this.employmentUseAtr = EnumAdaptor.valueOf(employmentAtr, NotUseAtr.class);
		this.canAbolition = canAbolish == 0 ? false: true;
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
			int isAbolition, int categoryType, int isFixed,
			int addObj, int initObj, int salaryAtr, int employmentAtr, int personnelAtr,  int canAbolish) {
		// salaryAtr, int employmentAtr, int personelAtr, int canAbolish
		return new PersonInfoCategory(personInfoCategoryId, companyId, categoryCode, categoryParentCode, categoryName,
				personEmployeeType, isAbolition, categoryType, isFixed, addObj, initObj, salaryAtr,employmentAtr, personnelAtr, canAbolish);
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
	
	public static PersonInfoCategory createDomainWithAbolition(String ctgId, String ctgCd, String name, int isUsed){
		PersonInfoCategory p = new PersonInfoCategory();
		p.personInfoCategoryId = ctgId;
		p.categoryName = new CategoryName(name);
		p.categoryCode = new CategoryCode(ctgCd);
		p.isAbolition = EnumAdaptor.valueOf(isUsed, IsAbolition.class);
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
		// đối ứng cho gian hàng lần 15,16  - thêm 2 trường initValMasterCls,addItemCls 
		this.initValMasterCls = InitValMasterObjCls.INIT;
		this.addItemCls = AddItemObjCls.ENABLE;
		// 6-8-2018 đối ứng cho gian hàng lần 15,16  - thêm 4 trường mới salaryAtr, employmentAtr, personelAtr, canAbolish
		this.salaryUseAtr = NotUseAtr.USE;
		this.employmentUseAtr = NotUseAtr.USE;
		this.personnelUseAtr  = NotUseAtr.USE;
		this.canAbolition = true;
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
	

	public void setAbolish(IsAbolition isAbolition) {
		this.isAbolition = isAbolition;
	}
		
	public boolean isEmployeeType() {
		return personEmployeeType == PersonEmployeeType.EMPLOYEE;
	}
	
	public boolean isPersonType() {
		return personEmployeeType == PersonEmployeeType.PERSON;
	}
	
	public boolean isSalary() {
		return this.salaryUseAtr == NotUseAtr.USE;
	}
	
	public boolean isEmployment() {
		return employmentUseAtr == NotUseAtr.USE;
	}
	
	public boolean isPersonnel() {
		return personnelUseAtr == NotUseAtr.USE;
	}
	
}
