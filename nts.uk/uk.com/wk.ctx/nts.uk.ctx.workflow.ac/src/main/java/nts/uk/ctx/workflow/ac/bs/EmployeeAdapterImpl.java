/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.workflow.ac.bs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.pub.employee.ConcurrentEmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.EmployeeExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.workflow.dom.adapter.bs.EmployeeAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.PersonAdapter;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.ConcurrentEmployeeImport;
import nts.uk.ctx.workflow.dom.adapter.bs.dto.EmployeeImport;

/**
 * The Class EmployeeApproveAdapterImpl.
 */
@Stateless
public class EmployeeAdapterImpl implements EmployeeAdapter {

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
	private PersonAdapter psInfor;
	@Inject
	private EmployeeInfoPub emInfor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.workflow.dom.approvermanagement.workroot.employee.
	 * EmployeeApproveAdapter#findByWpkIds(java.lang.String, java.util.List,
	 * nts.arc.time.GeneralDate)
	 */
	public List<EmployeeImport> findByWpkIds(String companyId, List<String> workplaceIds,
			GeneralDate baseDate) {
		List<EmployeeImport> empDto = employeePub.findByWpkIds(companyId, workplaceIds, baseDate)
				.stream().map(x -> new EmployeeImport(x.getCompanyId(),
				x.getPId(), 
				x.getSId(), 
				x.getSCd(),
				psInfor.getPersonInfo(x.getSId()).getEmployeeName(),
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
	public List<EmployeeImport> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate) {
		List<EmployeeImport> employeesInfor = emInfor.getEmployeesAtWorkByBaseDate(companyId, baseDate)
				.stream()
				.map(x -> new EmployeeImport(x.getCompanyId(), 
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
	
	/**
	 * 
	 * @param sID
	 * @return
	 */
	@Override
	public String getEmployeeName(String sID) {
		return this.psInfor.getPersonInfo(sID).getEmployeeName();
	}

	@Override
	public List<ConcurrentEmployeeImport> getConcurrentEmployee(String companyId, String jobId, GeneralDate baseDate) {
		List<ConcurrentEmployeeExport> export = employeePub.getConcurrentEmployee(companyId, jobId, baseDate);
		if (CollectionUtil.isEmpty(export)) {
			Collections.emptyList();
		}
		
		return export.stream().map(x -> new ConcurrentEmployeeImport(
				x.getEmployeeId(), 
				x.getEmployeeCd(), 
				x.getPersonName(), 
				x.getJobId(), 
				x.getJobCls().value)).collect(Collectors.toList());
	}
}
