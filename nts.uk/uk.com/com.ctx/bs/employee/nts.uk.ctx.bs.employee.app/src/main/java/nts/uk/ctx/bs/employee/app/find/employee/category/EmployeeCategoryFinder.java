package nts.uk.ctx.bs.employee.app.find.employee.category;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.person.dom.person.role.auth.category.PersonInfoCategoryAuthRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeCategoryFinder {

	/** The employee repository. */
	@Inject
	private EmployeeRepository employeeRepository;

	@Inject
	private PersonInfoCategoryAuthRepository perInfoCtgAuthRepo;

	public List<PersonInfoCtgFullDto> getAllPerInfoCtg(String companyId, String employeeIdSelected) {
		String empIdCurrentLogin = AppContexts.user().employeeId();
		// get roleIdOfLogin from app context;
		// String roleIdOfLogin = AppContexts.user().roles().forPersonalInfo();
		String roleIdOfLogin = "99900000-0000-0000-0000-000000000001";

		// get list ctg
		List<PersonInfoCtgFullDto> listCategory = employeeRepository.getAllPerInfoCtg(companyId).stream().map(p -> {
			return new PersonInfoCtgFullDto(p.getPersonInfoCategoryId(), p.getCategoryCode().v(),
					p.getCategoryName().v(), p.getPersonEmployeeType().value, p.getIsAbolition().value,
					p.getCategoryType().value, p.getIsFixed().value);
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
}
