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

import nts.arc.time.GeneralDate;
import nts.uk.query.model.employee.EmployeeReferenceRange;
import nts.uk.query.model.employee.EmployeeRoleImported;
import nts.uk.query.model.employee.EmployeeRoleRepository;
import nts.uk.query.model.employee.EmployeeSearchQuery;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.model.employee.RoleWorkPlaceAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

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

	/**
	 * Find.
	 *
	 * @param queryDto the query dto
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> find(EmployeeSearchQueryDto queryDto) {
		
		//Algorithm: 検索条件の職場一覧を参照範囲に基いて変更する
		List<String> lstWorkplaceChange = this.changeWorkplaceListByRole(queryDto);
		
		//check param FilterByWorkPlace
		if (!queryDto.getFilterByWorkplace()) {
        	queryDto.setFilterByWorkplace(true);
        }
		//Change WorkplaceCodes
		queryDto.setWorkplaceCodes(lstWorkplaceChange);
		
		return this.repo.find(AppContexts.user().companyId(), queryDto.toQueryModel()).stream()
				.map(model -> RegulationInfoEmployeeDto.builder()
						.employeeCode(model.getEmployeeCode())
						.employeeId(model.getEmployeeID())
						.employeeName(model.getName().orElse(""))
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
	private List<String> changeWorkplaceListByRole(EmployeeSearchQueryDto queryDto) {
		//get RoleId
		String roleId = this.workPlaceAdapter.findRoleIdBySystemType(queryDto.getSystemType());
		
		//check RoleId
		if(roleId == null) {
			throw new RuntimeException("Invalid Role");
		}
		
		// Find Role by roleId;
		EmployeeRoleImported role = this.roleRepo.findRoleById(roleId);
		
		// Check Role.
		if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.ONLY_MYSELF) {
			throw new RuntimeException("Unable to search");
		}
		
		List<String> listWorkplaceFinal = new ArrayList<>();
		
		//check param referenceRange
		switch (EmployeeReferenceRange.valueOf(queryDto.getReferenceRange())) {
			case ONLY_MYSELF:
	            break;
	        case ALL_EMPLOYEE:
	            if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.ALL_EMPLOYEE) {
	            	break;
	            } else {
	            	//Get list String Workplace
	            	listWorkplaceFinal = this.changeListWorkplaces(queryDto.toQueryModel());
	            }
	            break;
	        case DEPARTMENT_ONLY:
	        	//Get list String Workplace
	        	listWorkplaceFinal = this.changeListWorkplaces(queryDto.toQueryModel());
	            break;
	        case DEPARTMENT_AND_CHILD:
	            if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
	            	//Get list String Workplace
	            	listWorkplaceFinal = this.changeListWorkplaces(queryDto.toQueryModel());
	                break;
	            } else {
	            	//Get list String Workplace
	            	queryDto.setReferenceRange(EmployeeReferenceRange.DEPARTMENT_ONLY.value);
	            	listWorkplaceFinal = this.changeListWorkplaces(queryDto.toQueryModel());
	            }
	            break;
	        default: break;
		}
		
		return listWorkplaceFinal;
	}
	
	/**
	 * Change list workplaces.
	 *
	 * @param queryParam the query param
	 * @return the list
	 */
	//change list Workplace [指定条件から参照可能な職場リストを取得する]
	private List<String> changeListWorkplaces(EmployeeSearchQuery queryParam) {
		
		List<String> listWorkplaceFinal = new ArrayList<>();
		
		//get List Workplace
		GeneralDate date = GeneralDate.localDate(queryParam.getBaseDate().toLocalDate());
		List<String> wkplist = this.workPlaceAdapter.getWorkPlaceIdByEmployeeReferenceRange(date, queryParam.getReferenceRange());
        
		//check param filterByWorkplace
        if (queryParam.getFilterByWorkplace()) {
            // merge list workplaces
        	listWorkplaceFinal = queryParam.getWorkplaceCodes().stream()
                    .filter(wkplist::contains)
                    .collect(Collectors.toList());
        } else {
        	//Set list Workplace
        	listWorkplaceFinal = wkplist;
        }
        return listWorkplaceFinal;
    }
}
