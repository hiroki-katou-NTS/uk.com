/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.query.model.employee.EmployeeReferenceRange;
import nts.uk.query.model.employee.EmployeeRoleImported;
import nts.uk.query.model.employee.EmployeeRoleRepository;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegulationInfoEmployeeFinder.
 */
@Stateless
public class RegulationInfoEmployeeFinder {

	/** The repo. */
	@Inject
	private RegulationInfoEmployeeRepository repo;
	
	@Inject
	private EmployeeRoleRepository roleRepo;

	/**
	 * Find.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> find(EmployeeSearchQueryDto queryDto) {
		return this.repo.find(AppContexts.user().companyId(), queryDto.toQueryModel()).stream()
				.map(model -> RegulationInfoEmployeeDto.builder().employeeCode(model.getEmployeeCode())
						.employeeId(model.getEmployeeID()).employeeName(model.getName().orElse("")).build())
				.collect(Collectors.toList());
	}
	
	// 検索条件の職場一覧を参照範囲に基いて変更する
	private List<String> changeWorkplaceListByRole(String roleId, EmployeeReferenceRange referenceRange) {
		if(roleId == null) {
			throw new RuntimeException("Invalid Role");
		}
		
		// Find Role by roleId;
		EmployeeRoleImported role = this.roleRepo.findRoleById();
		
		// Check Role.
		if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.ONLY_MYSELF) {
			throw new RuntimeException("Unable to search");
		}
		
		// 
		if (referenceRange == EmployeeReferenceRange.DEPARTMENT_AND_CHILD
				|| referenceRange == EmployeeReferenceRange.DEPARTMENT_ONLY) {
			
			// check role.
			//if (role.ge)
		}
		
		return new ArrayList<String>();
	}
}
