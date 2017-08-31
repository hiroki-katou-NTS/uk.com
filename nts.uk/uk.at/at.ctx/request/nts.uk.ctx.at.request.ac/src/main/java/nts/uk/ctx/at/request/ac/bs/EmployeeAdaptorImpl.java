/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.ac.bs;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employee.employment.SyEmploymentPub;
import nts.uk.ctx.bs.employee.pub.employee.workplace.SyWorkplacePub;

/**
 * The Class EmployeeAdaptorImpl.
 */
@Stateless
public class EmployeeAdaptorImpl implements EmployeeAdaptor {

	/** The employee pub. */
	@Inject
	private SyEmployeePub employeePub;

	/** The employment pub. */
	@Inject
	private SyEmploymentPub employmentPub;

	/** The workplace pub. */
	@Inject
	private SyWorkplacePub workplacePub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor#
	 * getEmploymentCode(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	public String getEmploymentCode(String companyId, String employeeId, GeneralDate baseDate) {
		return this.employmentPub.getEmploymentCode(companyId, employeeId, baseDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor#
	 * findWpkIdsBySid(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public List<String> findWpkIdsBySid(String companyId, String sid, GeneralDate baseDate) {
		return this.workplacePub.findWpkIdsBySid(companyId, sid, baseDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeAdaptor#
	 * getWorkplaceId(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public String getWorkplaceId(String companyId, String employeeId, GeneralDate baseDate) {
		return this.workplacePub.getWorkplaceId(companyId, employeeId, baseDate);
	}
}
