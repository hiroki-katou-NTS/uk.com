package nts.uk.ctx.pereg.app.command.process.checkdata;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.app.find.person.info.item.PersonInfoItemDefDto;
import nts.uk.ctx.pereg.dom.person.info.category.AddItemObjCls;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryCode;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryName;
import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.ctx.pereg.dom.person.info.category.InitValMasterObjCls;
import nts.uk.ctx.pereg.dom.person.info.category.IsAbolition;
import nts.uk.ctx.pereg.dom.person.info.category.IsFixed;
import nts.uk.ctx.pereg.dom.person.info.category.PersonEmployeeType;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Setter
@Getter
public class PersonInfoCategoryDto {

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
	
	private List<PersonInfoItemDefinition> listItemDf;
	
	


	public PersonInfoCategoryDto(String personInfoCategoryId, CategoryCode categoryCode, CategoryName categoryName,
			String companyId, IsFixed isFixed, PersonEmployeeType personEmployeeType, CategoryType categoryType,
			IsAbolition isAbolition, CategoryCode categoryParentCode, boolean alreadyCopy,
			InitValMasterObjCls initValMasterCls, AddItemObjCls addItemCls, NotUseAtr salaryUseAtr,
			NotUseAtr personnelUseAtr, NotUseAtr employmentUseAtr, boolean canAbolition,
			List<PersonInfoItemDefinition> listItemDf) {
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
		this.alreadyCopy = alreadyCopy;
		this.initValMasterCls = initValMasterCls;
		this.addItemCls = addItemCls;
		this.salaryUseAtr = salaryUseAtr;
		this.personnelUseAtr = personnelUseAtr;
		this.employmentUseAtr = employmentUseAtr;
		this.canAbolition = canAbolition;
		this.listItemDf = listItemDf;
	}




	public PersonInfoCategoryDto(PersonInfoCategory domain) {
		super();
		this.personInfoCategoryId = domain.getPersonInfoCategoryId();
		this.categoryCode = domain.getCategoryCode();
		this.categoryName = domain.getCategoryName();
		this.companyId = domain.getCompanyId();
		this.isFixed = domain.getIsFixed();
		this.personEmployeeType = domain.getPersonEmployeeType();
		this.categoryType = domain.getCategoryType();
		this.isAbolition = domain.getIsAbolition();
		this.categoryParentCode = null;
		this.initValMasterCls = domain.getInitValMasterCls();
		this.addItemCls = domain.getAddItemCls();
		this.employmentUseAtr = domain.getEmploymentUseAtr();
		this.salaryUseAtr = domain.getSalaryUseAtr();
		this.personnelUseAtr = domain.getPersonnelUseAtr();
		this.canAbolition = domain.isCanAbolition();
		this.listItemDf = new ArrayList<>();
	}
}
