/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.ac.employee;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.employment.SyEmploymentPub;
import nts.uk.ctx.bs.employee.pub.employee.workplace.SyWorkplacePub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;

/**
 * The Class EmployeeApproveAdapterImpl.
 */
@Stateless
public class EmployeeApproveAdapterImpl implements EmployeeApproveAdapter {

	/** The employee pub. */
	@Inject
	private SyEmployeePub employeePub;

	/** The workplace pub. */
	@Inject
	private SyWorkplacePub workplacePub;

	/** The employment pub. */
	@Inject
	private SyEmploymentPub employmentPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.
	 * EmployeeApproveAdapter#findByWpkIds(java.lang.String, java.util.List,
	 * nts.arc.time.GeneralDate)
	 */
	public List<EmployeeApproveDto> findByWpkIds(String companyId, List<String> workplaceIds,
			GeneralDate baseDate) {
		List<EmployeeExport> empDto = employeePub.findByWpkIds(companyId, workplaceIds, baseDate);
		List<EmployeeApproveDto> lstEmployees = new ArrayList<>();
		for (EmployeeExport employeeDto : empDto) {
			EmployeeApproveDto appDto = new EmployeeApproveDto();
			appDto.setCompanyId(employeeDto.getCompanyId());
			appDto.setJoinDate(employeeDto.getJoinDate());
			appDto.setPId(employeeDto.getPId());
			appDto.setRetirementDate(employeeDto.getRetirementDate());
			appDto.setSCd(employeeDto.getSCd());
			appDto.setSId(employeeDto.getSId());
			appDto.setSMail(employeeDto.getSMail());
			lstEmployees.add(appDto);
		}
		return lstEmployees;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.
	 * EmployeeApproveAdapter#getWorkplaceId(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	public String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate) {
		return workplacePub.getWorkplaceId(companyId, employeeId, baseDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.
	 * EmployeeApproveAdapter#getEmploymentCode(java.lang.String,
	 * java.lang.String, nts.arc.time.GeneralDate)
	 */
	public String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate) {
		return employmentPub.getEmploymentCode(companyId, employeeId, baseDate);
	}
}
