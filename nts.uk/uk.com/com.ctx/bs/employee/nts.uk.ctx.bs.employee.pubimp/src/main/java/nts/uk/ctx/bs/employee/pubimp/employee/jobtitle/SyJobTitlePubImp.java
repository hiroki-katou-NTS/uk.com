/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee.jobtitle;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.dto.AcJobTitleDto;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.PubJobTitleDto;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.SyJobTitlePub;

/**
 * The Class EmployeePubImp.
 */
@Stateless
public class SyJobTitlePubImp implements SyJobTitlePub {

	/** The employment adapter. */
	@Inject
	private SyJobTitleAdapter jobTitleAdapter;

	/** The job title history repository. */
	@Inject
	private AffJobTitleHistoryRepository jobTitleHistoryRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(String)
	 */
	@Override
	public List<PubJobTitleDto> findJobTitleBySid(String employeeId) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.jobTitleHistoryRepository
				.findBySid(employeeId);

		List<String> jobIds = affJobTitleHistories.stream().map(item -> item.getJobTitleId().v())
				.collect(Collectors.toList());

		List<AcJobTitleDto> jobTitleDtos = this.jobTitleAdapter.findByJobIds(jobIds);

		// Return
		return jobTitleDtos.stream().map(item -> {
			PubJobTitleDto dto = new PubJobTitleDto();
			dto.setCompanyId(item.getCompanyId());
			dto.setPositionId(item.getPositionId());
			dto.setPositionCode(item.getPositionCode());
			dto.setPositionName(item.getPositionName());
			dto.setSequenceCode(item.getSequenceCode());
			dto.setStartDate(item.getStartDate());
			dto.setEndDate(item.getEndDate());
			return dto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<PubJobTitleDto> findJobTitleBySid(String employeeId, GeneralDate baseDate) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.jobTitleHistoryRepository
				.findBySid(employeeId, baseDate);

		List<String> jobIds = affJobTitleHistories.stream().map(item -> item.getJobTitleId().v())
				.collect(Collectors.toList());

		List<AcJobTitleDto> jobTitleDtos = this.jobTitleAdapter.findByJobIds(jobIds);

		// Return
		return jobTitleDtos.stream().map(item -> {
			PubJobTitleDto dto = new PubJobTitleDto();
			dto.setCompanyId(item.getCompanyId());
			dto.setPositionId(item.getPositionId());
			dto.setPositionCode(item.getPositionCode());
			dto.setPositionName(item.getPositionName());
			dto.setSequenceCode(item.getSequenceCode());
			dto.setStartDate(item.getStartDate());
			dto.setEndDate(item.getEndDate());
			return dto;
		}).collect(Collectors.toList());
	}

}
