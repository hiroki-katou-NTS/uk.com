/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.app.employee;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.query.model.employee.EmployeeReferenceRange;
import nts.uk.query.model.employee.EmployeeRoleImported;
import nts.uk.query.model.employee.EmployeeRoleRepository;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.model.employee.RoleWorkPlaceAdapter;
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
	
	@Inject
	private RoleWorkPlaceAdapter workPlaceAdapter;
	
	/** The employee info repository. */
	@Inject
	private RegulationInfoEmployeeRepository employeeInfoRepository;

	/**
	 * Find.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> find(RegulationInfoEmpQueryDto queryDto) {
		
		//Algorithm: 検索条件の職場一覧を参照範囲に基いて変更する
		boolean isSearchOnlyMe = this.changeWorkplaceListByRole(queryDto);
		
		if(isSearchOnlyMe) {
			// Find login employee info.
			String loginEmployeeId = AppContexts.user().employeeId();
			String companyId = AppContexts.user().companyId();
			RegulationInfoEmployee loginEmployee = this.employeeInfoRepository.findBySid(companyId, loginEmployeeId,
					GeneralDateTime.now());
			return Arrays.asList(RegulationInfoEmployeeDto.builder()
					.employeeCode(loginEmployee.getEmployeeCode())
					.employeeId(loginEmployee.getEmployeeID())
					.employeeName(loginEmployee.getName().orElse(""))
					.workplaceId(loginEmployee.getWorkplaceId().orElse(""))
					.workplaceCode(loginEmployee.getWorkplaceCode().orElse(""))
					.workplaceName(loginEmployee.getWorkplaceName().orElse(""))
					.build());
		}
		
		return this.repo.find(AppContexts.user().companyId(), queryDto.toQueryModel()).stream()
				.map(model -> RegulationInfoEmployeeDto.builder()
						.employeeCode(model.getEmployeeCode())
						.employeeId(model.getEmployeeID())
						.employeeName(model.getName().orElse(""))
						.workplaceId(model.getWorkplaceId().orElse(""))
						.workplaceCode(model.getWorkplaceCode().orElse(""))
						.workplaceName(model.getWorkplaceName().orElse(""))
						.build())
				.collect(Collectors.toList());
	}
	
	/**
	 * Change workplace list by role.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 */
	// 検索条件の職場一覧を参照範囲に基いて変更する
	private boolean changeWorkplaceListByRole(RegulationInfoEmpQueryDto queryDto) {
		// get RoleId
		String roleId = this.workPlaceAdapter.findRoleIdBySystemType(queryDto.getSystemType());
		boolean isSearchOnlyMe = false;

		// check RoleId
		if (roleId == null) {
			throw new RuntimeException("Invalid Role");
		}

		// Find Role by roleId;
		EmployeeRoleImported role = this.roleRepo.findRoleById(roleId);

		// Check Role.
		if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.ONLY_MYSELF) {
			isSearchOnlyMe = true;
		}

		// check param referenceRange
		switch (EmployeeReferenceRange.valueOf(queryDto.getReferenceRange())) {
		case ONLY_MYSELF:
			break;
		case ALL_EMPLOYEE:
			if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.ALL_EMPLOYEE) {
				// not change workplaceCodes
				break;
			} else {
				// Get list String Workplace
				this.changeListWorkplaces(queryDto);
			}
			break;
		case DEPARTMENT_ONLY:
			// Get list String Workplace
			this.changeListWorkplaces(queryDto);
			break;
		case DEPARTMENT_AND_CHILD:
			if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
				// Get list String Workplace
				this.changeListWorkplaces(queryDto);
				break;
			} else {
				// Get list String Workplace
				queryDto.setReferenceRange(EmployeeReferenceRange.DEPARTMENT_ONLY.value);
				this.changeListWorkplaces(queryDto);
			}
			break;
		default:
			throw new RuntimeException("Invalid enum value");
		}
		return isSearchOnlyMe;
	}
	
	/**
	 * Change list workplaces.
	 *
	 * @param queryParam the query param
	 * @return the list
	 */
	//change list Workplace [指定条件から参照可能な職場リストを取得する]
	private void changeListWorkplaces(RegulationInfoEmpQueryDto queryParam) {
		// get List Workplace
		GeneralDate date = GeneralDate.fromString(queryParam.getBaseDate(), "yyyy-MM-dd");
		List<String> wkplist = this.workPlaceAdapter.getWorkPlaceIdByEmployeeReferenceRange(date,
				queryParam.getReferenceRange());

		// check param filterByWorkplace
		if (queryParam.getFilterByWorkplace()) {
			// merge list workplaces
			queryParam.setWorkplaceCodes(queryParam.getWorkplaceCodes().stream().filter(wkplist::contains)
					.collect(Collectors.toList()));
		} else {
			// Set list Workplace
			queryParam.setWorkplaceCodes(wkplist);
			queryParam.setFilterByWorkplace(true);
		}

	}
}
