/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ac.executionlog;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.ScEmploymentStatusAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmploymentStatusDto;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentExport;
import nts.uk.ctx.bs.employee.pub.employment.statusemployee.StatusOfEmploymentPub;

/**
 * The Class ScEmploymentStatusAdapterImpl.
 */
@Stateless
public class ScEmploymentStatusAdapterImpl implements ScEmploymentStatusAdapter {

	/** The employment status pub. */
	@Inject
	private StatusOfEmploymentPub employmentStatusPub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.adapter.ScEmploymentStatusAdapter#
	 * getStatusEmployment(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public EmploymentStatusDto getStatusEmployment(String employeeId, GeneralDate baseDate) {
		return this.toDto(this.employmentStatusPub.getStatusOfEmployment(employeeId, baseDate));
	}

	/**
	 * To dto.
	 *
	 * @param export the export
	 * @return the employment status dto
	 */
	private EmploymentStatusDto toDto(StatusOfEmploymentExport export) {
		EmploymentStatusDto dto = new EmploymentStatusDto();
		dto.setEmployeeId(export.getEmployeeId());
		dto.setLeaveHolidayType(export.getTempAbsenceFrNo());
		dto.setRefereneDate(export.getRefereneDate());
		dto.setStatusOfEmployment(export.getStatusOfEmployment());
		return dto;
	}

}
