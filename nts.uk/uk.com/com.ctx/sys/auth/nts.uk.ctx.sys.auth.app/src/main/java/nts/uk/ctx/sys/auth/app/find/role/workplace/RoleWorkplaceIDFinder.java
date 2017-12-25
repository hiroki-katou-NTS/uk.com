/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.app.find.role.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffWorkplaceHistImport;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.role.RoleRepository;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

/**
 * The Class RoleWorkplaceIDFinder.
 */
@Stateless
public class RoleWorkplaceIDFinder {

	/** The role repository. */
	@Inject
	private RoleRepository roleRepository;

	/** The workplace adapter. */
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	/** The workplace manager repository. */
	@Inject
	private WorkplaceManagerRepository workplaceManagerRepository;

	/**
	 * Find list wokplace id.
	 *
	 * @param systemType the system type
	 * @return the list
	 */
	public WorkplaceIdDto findListWokplaceId(Integer systemType) {


		String roleId = this.findRoleId(systemType);

		Optional<Role> opRole = roleRepository.findByRoleId(roleId);
		GeneralDate referenceDate = GeneralDate.today();
		List<String> listWkpId = new ArrayList<>();
		WorkplaceIdDto workplaceIdDto = new WorkplaceIdDto();

		if (opRole.isPresent()) {
			if (opRole.get().getEmployeeReferenceRange() == EmployeeReferenceRange.ALL_EMPLOYEE) {
				listWkpId = workplaceAdapter.findListWkpIdByBaseDate(referenceDate);
				workplaceIdDto.setListWorkplaceIds(listWkpId);
				workplaceIdDto.setIsAllEmp(true);
			} else {
				listWkpId = this.findListWkpIdByOtherCase(referenceDate, opRole.get());
				workplaceIdDto.setListWorkplaceIds(listWkpId);
				workplaceIdDto.setIsAllEmp(false);
			}
		}
		return workplaceIdDto;

	}
	
	/**
	 * Find wkp id by agorithm.
	 *
	 * @param referenceDate the reference date
	 * @param role the role
	 * @return the list
	 */
	public List<String> findListWkpIdByOtherCase(GeneralDate referenceDate, Role role) {
		List<String> listWkpId = new ArrayList<>();

		String workplaceId = "";
		String employeeId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		
		// get workplace manager 
		List<WorkplaceManager> listWkpManager = workplaceManagerRepository.findListWkpManagerByEmpIdAndBaseDate(employeeId, referenceDate);
		
		// add wkpId to listWkpId
		listWkpId = listWkpManager.stream().map(m -> m.getWorkplaceId()).collect(Collectors.toList());
				
		// requestList #30 get aff workplace history
		Optional<AffWorkplaceHistImport> opAffWorkplaceHistImport = workplaceAdapter.findWkpByBaseDateAndEmployeeId(referenceDate, employeeId);	
		
		// add wkpId to listWkpId		
		workplaceId = opAffWorkplaceHistImport.get().getWorkplaceId();
		if(!workplaceId.isEmpty()){
			listWkpId.add(workplaceId);
		}		
		
		// action RequestList #154
		if (role.getEmployeeReferenceRange() == EmployeeReferenceRange.DEPARTMENT_AND_CHILD) {
			List<String> list = workplaceAdapter.findListWorkplaceIdByCidAndWkpIdAndBaseDate(companyId, workplaceId, referenceDate);
			listWkpId.addAll(list);			
		}
	
		return listWkpId.stream().distinct().collect(Collectors.toList());
	}
		

	/**
	 * Find role id.
	 *
	 * @param systemType the system type
	 * @return the string
	 */
	public String findRoleId(Integer systemType) {
		LoginUserRoles loginUserRoles = AppContexts.user().roles();

		// Mock data
		switch (SystemType.valueOf(systemType)) {
		case PERSONAL_INFORMATION:
			// return loginUserRoles.forPersonalInfo();
			// EmployeeReferenceRange = 0
			return "085d32db-7809-4769-8cd2-c61d69fd1b51";

		case EMPLOYMENT:
			// return loginUserRoles.forAttendance();
			// EmployeeReferenceRange = 2
			return "32c67880-ff0a-4969-9056-9b49135d37d5";

		case SALARY:
			// return loginUserRoles.forPayroll();
			// EmployeeReferenceRange = 1
			return "55b0c0fc-e2e3-4afd-8853-fbc491725ec5";

		case HUMAN_RESOURCES:
			// return loginUserRoles.forPersonnel();
			// EmployeeReferenceRange = 1
			return "004";

		case ADMINISTRATOR:
			// return loginUserRoles.forCompanyAdmin();
			// EmployeeReferenceRange = 0
			return "f73d4feb-4f2f-41e8-96a2-44681cdd24eb";

		default:
			break;
		}

		return null;
	}

	/**
	 * The Enum SystemType.
	 */
	public enum SystemType {

		/** The personal information. */
		// システム管理者
		PERSONAL_INFORMATION(1),

		/** The employment. */
		// 就業
		EMPLOYMENT(2),

		/** The salary. */
		// 給与
		SALARY(3),

		/** The human resources. */
		// 人事
		HUMAN_RESOURCES(4),

		/** The administrator. */
		// 管理者
		ADMINISTRATOR(5);

		/** The value. */
		public final int value;

		/** The Constant values. */
		private final static SystemType[] values = SystemType.values();

		/**
		 * Instantiates a new system type.
		 *
		 * @param value the value
		 */
		private SystemType(int value) {
			this.value = value;
		}

		/**
		 * Value of.
		 *
		 * @param value the value
		 * @return the system type
		 */
		public static SystemType valueOf(Integer value) {
			// Invalid object.
			if (value == null) {
				return null;
			}

			// Find value.
			for (SystemType val : SystemType.values) {
				if (val.value == value) {
					return val;
				}
			}
			// Not found.
			return null;
		}
	}

}
