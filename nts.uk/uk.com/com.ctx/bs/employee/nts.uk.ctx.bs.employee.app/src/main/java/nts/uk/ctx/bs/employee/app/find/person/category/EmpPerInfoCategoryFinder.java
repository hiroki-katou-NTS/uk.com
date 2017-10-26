package nts.uk.ctx.bs.employee.app.find.person.category;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.employee.dom.person.PerInfoCtgDomainService;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmpPerInfoCategoryFinder {

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;
	
	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;
	
	@Inject
	private PerInfoCtgDomainService perInfoCtgDomainService;
	/**
	 * get person ctg infor and list of item children
	 * */
	public EmpPerCtgInfoDto getCtgAndItemByCtgId(String ctgId){
		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgId, AppContexts.user().contractCode()).get();
		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(), AppContexts.user().contractCode());
		return EmpPerCtgInfoDto.createObjectFromDomain(perCtgInfo, lstPerItemDef);
	}
	/**
	 * person ctg infor and list of item children by parent
	 * */
	public EmpPerCtgInfoDto getCtgAndItemByParent(String employeeId, String ctgId, String parentInfoId){
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonalInfo();
		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgId, contractCode).get();
		if(perCtgInfo.getCategoryType() == CategoryType.SINGLEINFO)
			return EmpPerCtgInfoDto.createObjectFromDomain(perCtgInfo);
		else{
			List<PersonInfoItemDefinition> lstPersonInfoItemDefinition = perInfoCtgDomainService.getPerItemDef(
					new ParamForGetPerItem(perCtgInfo, parentInfoId, roleId == null ? "" : roleId, companyId, contractCode, loginEmpId.equals(employeeId)));
		};
		return null;
	}
	
}
