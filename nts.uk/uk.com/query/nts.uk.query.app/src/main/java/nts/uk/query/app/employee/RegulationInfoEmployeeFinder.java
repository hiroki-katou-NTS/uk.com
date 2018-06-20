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
import nts.uk.query.model.employee.EmployeeAuthAdapter;
import nts.uk.query.model.employee.EmployeeReferenceRange;
import nts.uk.query.model.employee.EmployeeRoleImported;
import nts.uk.query.model.employee.EmployeeRoleRepository;
import nts.uk.query.model.employee.RegulationInfoEmployee;
import nts.uk.query.model.employee.RegulationInfoEmployeeRepository;
import nts.uk.query.model.employee.RoleWorkPlaceAdapter;
import nts.uk.query.model.employee.history.EmployeeHistoryRepository;
import nts.uk.query.model.employee.mgndata.EmpDataMngInfoAdapter;
import nts.uk.query.model.employement.history.EmploymentHistoryAdapter;
import nts.uk.query.model.person.QueryPersonAdapter;
import nts.uk.query.model.workrule.closure.QueryClosureEmpAdapter;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class RegulationInfoEmployeeFinder.
 */
@Stateless
public class RegulationInfoEmployeeFinder {

	/** The repo. */
	@Inject
	private RegulationInfoEmployeeRepository repo;
	
	/** The role repo. */
	@Inject
	private EmployeeRoleRepository roleRepo;
	
	/** The work place adapter. */
	@Inject
	private RoleWorkPlaceAdapter workPlaceAdapter;
	
	/** The emp auth adapter. */
	@Inject
	private EmployeeAuthAdapter empAuthAdapter;

	/** The emp data mng info adapter. */
	@Inject
	private EmpDataMngInfoAdapter empDataMngInfoAdapter;

	/** The person adapter. */
	@Inject
	private QueryPersonAdapter personAdapter;

	/** The emp his repo. */
	@Inject
	private EmployeeHistoryRepository empHisRepo;

	/** The closure emp adapter. */
	@Inject
	private QueryClosureEmpAdapter closureEmpAdapter;

	@Inject
	private EmploymentHistoryAdapter empHisAdapter;

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
			RegulationInfoEmployee loginEmployee = this.repo.findBySid(companyId, loginEmployeeId,
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
				.map(model -> this.toDto(model))
				.collect(Collectors.toList());
	}

	/**
	 * Find by employee code.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> findByEmployeeCode(SearchEmployeeQuery query) {
		List<String> sIds = searchByEmployeeCode(query.getCode(), query.getSystemType());

		// filter by closure id
		sIds = this.filterByClosure(query, sIds);
		
		return getEmployeeInfo(sIds, query.getReferenceDate());
	}

	/**
	 * Find by employee name.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> findByEmployeeName(SearchEmployeeQuery query) {
		List<String> sIds = searchByEmployeeName(query.getName(), query.getSystemType());
		
		// filter by closure id
		sIds = this.filterByClosure(query, sIds);
		
		return getEmployeeInfo(sIds, query.getReferenceDate());
	}

	/**
	 * Find by employee entry date.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> findByEmployeeEntryDate(SearchEmployeeQuery query) {
		List<String> sIds = searchByEntryDate(query.getDatePeriod(), query.getSystemType());
		
		// filter by closure id
		sIds = this.filterByClosure(query, sIds);
		
		return getEmployeeInfo(sIds, query.getReferenceDate());
	}

	/**
	 * Find by employee retirement date.
	 *
	 * @param query the query
	 * @return the list
	 */
	public List<RegulationInfoEmployeeDto> findByEmployeeRetirementDate(SearchEmployeeQuery query) {
		List<String> sIds = searchByRetirementDate(query.getDatePeriod(), query.getSystemType());
		
		// filter by closure id
		sIds = this.filterByClosure(query, sIds);
		
		return getEmployeeInfo(sIds, query.getReferenceDate());
	}

	// A：社員情報取得処理
	public List<RegulationInfoEmployeeDto> getEmployeeInfo(List<String> sIds, GeneralDate referenceDate) {
		return this.repo.findInfoBySIds(sIds, referenceDate).stream().map(item -> this.toDto(item))
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
		if (queryDto.getReferenceRange() == null) {
			return false;
		}

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
			isSearchOnlyMe = true;
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

	/**
	 * Search by employee code.
	 *
	 * @param sCd the s cd
	 * @param systemType the system type
	 * @return the list
	 */
	public List<String> searchByEmployeeCode(String sCd, Integer systemType) {
		List<String> sIds = this.empDataMngInfoAdapter.findNotDeletedBySCode(AppContexts.user().companyId(),
				sCd);

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

	/**
	 * Search by employee name.
	 *
	 * @param sName the s name
	 * @param systemType the system type
	 * @return the list
	 */
	public List<String> searchByEmployeeName(String sName, Integer systemType) {
		List<String> pIds = this.personAdapter.findPersonIdsByName(sName);

		List<String> sIds = this.empDataMngInfoAdapter.findByListPersonId(AppContexts.user().companyId(), pIds);

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

	/**
	 * Search by entry date.
	 *
	 * @param period the period
	 * @param systemType the system type
	 * @return the list
	 */
	public List<String> searchByEntryDate(DatePeriod period, Integer systemType) {
		List<String> sIds = this.empHisRepo.findEmployeeByEntryDate(AppContexts.user().companyId(), period);

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

	/**
	 * Search by retirement date.
	 *
	 * @param period the period
	 * @param systemType the system type
	 * @return the list
	 */
	public List<String> searchByRetirementDate(DatePeriod period, Integer systemType) {
		List<String> sIds = this.empHisRepo.findEmployeeByRetirementDate(AppContexts.user().companyId(), period);

		return this.empAuthAdapter.narrowEmpListByReferenceRange(sIds, systemType);
	}

	/**
	 * Filter by closure.
	 *
	 * @param query the query
	 * @param sIds the s ids
	 * @return the list
	 */
	private List<String> filterByClosure(SearchEmployeeQuery query, List<String> sIds) {
		if (query.isUseClosure() && !query.isAllClosure()) {
			List<String> empCds = this.closureEmpAdapter.findListEmpCdByClosureId(query.getClosureId());
			List<String> filteredSids = this.empHisAdapter.findSIdsByEmpCdsAndPeriod(empCds, query.getReferenceDatePeriod());

			// filter list employee ids
			return sIds.stream().filter(filteredSids::contains).collect(Collectors.toList());
		}
		return sIds;
	}

	/**
	 * To dto.
	 *
	 * @param model the model
	 * @return the regulation info employee dto
	 */
	private RegulationInfoEmployeeDto toDto(RegulationInfoEmployee model) {
		return RegulationInfoEmployeeDto.builder()
				.employeeCode(model.getEmployeeCode())
				.employeeId(model.getEmployeeID())
				.employeeName(model.getName().orElse(""))
				.workplaceId(model.getWorkplaceId().orElse(""))
				.workplaceCode(model.getWorkplaceCode().orElse(""))
				.workplaceName(model.getWorkplaceName().orElse(""))
				.build();
	}
}
