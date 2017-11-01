package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.bs.employee.app.find.person.category.EmpPerCtgInfoDto;
import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.EmpCtgDomainServices;
import nts.uk.ctx.bs.employee.dom.employeeinfo.service.ParamForGetItemDef;
import nts.uk.ctx.bs.employee.dom.person.ParamForGetPerItem;
import nts.uk.ctx.bs.person.dom.person.info.category.CategoryType;
import nts.uk.ctx.bs.person.dom.person.info.category.PerInfoCategoryRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;
import nts.uk.ctx.bs.person.dom.person.info.item.PersonInfoItemDefinition;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeCategoryFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PerInfoCategoryRepositoty perInfoCtgRepositoty;

	@Inject
	private PerInfoItemDefRepositoty pernfoItemDefRep;

	@Inject
	private EmpCtgDomainServices empCtgDomainServices;

	public List<PersonInfoCtgFullDto> getAllPerInfoCtg(String companyId, String employeeIdSelected) {
		String empIdCurrentLogin = AppContexts.user().employeeId();
		// get roleIdOfLogin from app context;
		// String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";

		// get list ctg
		List<PersonInfoCtgFullDto> listCategory = employeeRepository.getAllPerInfoCtg(companyId).stream().map(p -> {
			return new PersonInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
					p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
					p.getCategoryType().value, p.getIsFixed().value, null);
		}).collect(Collectors.toList());

		if (employeeIdSelected == empIdCurrentLogin) {
			// get List Ctg theo quyền

		} else {
			// get List Ctg theo quyền

		}

		for (int i = 0; i < listCategory.size(); i++) {

		}
		return listCategory;
	};

	public PersonInfoCtgFullDto getCtgAndItemByCtgId(String empSelected, String ctgId) {
		String ctgIDTest = "0f15c5e4-09b8-4a58-8ccf-7cc7d781a886";
		String contractCode = AppContexts.user().contractCode();
		String companyId = AppContexts.user().companyId();
		String loginEmpId = AppContexts.user().employeeId();
		String roleId = AppContexts.user().roles().forPersonnel();
		val perCtgInfo = perInfoCtgRepositoty.getPerInfoCategory(ctgIDTest, AppContexts.user().contractCode()).get();

		// check Type của Category
		if (perCtgInfo.getCategoryType() == CategoryType.SINGLEINFO) {
			return PersonInfoCtgFullDto.createObjectFromDomain(perCtgInfo);
		} else {
			List<PersonInfoItemDefinition> lstPerInfoItemDef = empCtgDomainServices
					.getPerItemDef(new ParamForGetItemDef(perCtgInfo, roleId == null ? "" : roleId, companyId,
							contractCode, loginEmpId.equals(empSelected)));
		}

		val lstPerItemDef = pernfoItemDefRep.getPerInfoItemByCtgId(ctgId, AppContexts.user().companyId(),
				AppContexts.user().contractCode());
		return PersonInfoCtgFullDto.createObjectFromDomain(perCtgInfo, lstPerItemDef);

	}
}
