/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.ac.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.EmployeeApproveDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.person.PersonInforExportAdapter;

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
	@Inject
	private PersonInforExportAdapter psInfor;
	@Inject
	private EmployeeInfoPub emInfor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.
	 * EmployeeApproveAdapter#findByWpkIds(java.lang.String, java.util.List,
	 * nts.arc.time.GeneralDate)
	 */
	public List<EmployeeApproveDto> findByWpkIds(String companyId, List<String> workplaceIds,
			GeneralDate baseDate) {
		List<EmployeeApproveDto> empDto = employeePub.findByWpkIds(companyId, workplaceIds, baseDate)
				.stream().map(x -> new EmployeeApproveDto(x.getCompanyId(),
				x.getPId(), 
				x.getSId(), 
				x.getSCd(),
				psInfor.personName(x.getSId()),
				"", 
				"",
				x.getSMail(),
				x.getRetirementDate(),
				x.getJoinDate())).collect(Collectors.toList());
		
		return empDto;
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

	@Override
	public List<String> findWpkIdsBySid(String companyId, String employeeId, GeneralDate date) {		
		return workplacePub.findWpkIdsBySid(companyId, employeeId, date);
	}

	@Override
	public List<EmployeeApproveDto> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate) {
		List<EmployeeApproveDto> employeesInfor = emInfor.getEmployeesAtWorkByBaseDate(companyId, baseDate)
				.stream()
				.map(x -> new EmployeeApproveDto(x.getCompanyId(), 
						x.getPersonId(), 
						x.getEmployeeId(), 
						x.getEmployeeCode(), 
						"",
						"", 
						"", 
						"", 
						baseDate,
						baseDate))
				.collect(Collectors.toList());
		return employeesInfor;
	}
}
