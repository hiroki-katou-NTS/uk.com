/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.ac.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.company.pub.jobtitle.JobtitlePub;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.dto.AcJobTitleDto;

/**
 * The Class JobtitleAdapterImpl.
 */
@Stateless
public abstract class JobtitleAdapterImpl implements SyJobTitleAdapter {

	/** The jobtitle pub. */
	@Inject
	private JobtitlePub jobtitlePub;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.jobtitle.JobTitleAdapter#findAll(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<AcJobTitleDto> findAll(String companyId, GeneralDate referenceDate) {
		return jobtitlePub.findAll(companyId, referenceDate).stream()
				.map(item -> new AcJobTitleDto(item.getCompanyId(), item.getPositionId(),
						item.getPositionCode(), item.getPositionName(), item.getSequenceCode(),
						item.getStartDate(), item.getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.jobtitle.JobTitleAdapter#findByJobIds(
	 * java.util.List)
	 */
	@Override
	public List<AcJobTitleDto> findByJobIds(List<String> jobIds) {
		return jobtitlePub.findByJobIds(jobIds).stream()
				.map(item -> new AcJobTitleDto(item.getCompanyId(), item.getPositionId(),
						item.getPositionCode(), item.getPositionName(), item.getSequenceCode(),
						item.getStartDate(), item.getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.access.jobtitle.SyJobTitleAdapter#findByJobIds
	 * (java.lang.String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<AcJobTitleDto> findByJobIds(String companyId, List<String> jobIds,
			GeneralDate baseDate) {
		return jobtitlePub.findByJobIds(companyId, jobIds, baseDate).stream()
				.map(item -> new AcJobTitleDto(item.getCompanyId(), item.getPositionId(),
						item.getPositionCode(), item.getPositionName(), item.getSequenceCode(),
						item.getStartDate(), item.getEndDate()))
				.collect(Collectors.toList());
	}

}
