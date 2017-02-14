package nts.uk.ctx.basic.dom.organization.department;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DepartmentDomainService {

	@Inject
	private DepartmentRepository departmentRepository;

	public void add(Department department) {
		if (department.getHierarchyCode().toString().trim().length() / 3 > 10) {
			// throw error
		}
		if (departmentRepository.isDuplicateDepartmentCode(department.getCompanyCode(),
				department.getDepartmentCode())) {
			// throw error
		}
		departmentRepository.add(department);
	}

	public void update(Department department) {
		departmentRepository.update(department);
	}

	public void remove(String companyCode, DepartmentCode departmentCode, String historyId) {
		departmentRepository.remove(companyCode, departmentCode, historyId);
	}

}
