/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.employee.jobtitle;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.SyJobTitleAdapter;
import nts.uk.ctx.bs.employee.dom.access.jobtitle.dto.JobTitleImport;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.employee.jobtitle.SyJobTitlePub;

/**
 * The Class SyJobTitlePubImp.
 */
@Stateless
public class SyJobTitlePubImp implements SyJobTitlePub {

	/** The first item index. */
	private final int FIRST_ITEM_INDEX = 0;

	/** The job title adapter. */
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
	public List<JobTitleExport> findJobTitleBySid(String employeeId) {
		// Query
		List<AffJobTitleHistory> affJobTitleHistories = this.jobTitleHistoryRepository
				.findBySid(employeeId);

		List<String> jobIds = affJobTitleHistories.stream().map(item -> item.getJobTitleId().v())
				.collect(Collectors.toList());

		List<JobTitleImport> jobTitleDtos = this.jobTitleAdapter.findByJobIds(jobIds);

		// Return
		return jobTitleDtos.stream()
				.map(item -> JobTitleExport.builder().companyId(item.getCompanyId())
						.positionId(item.getPositionId()).positionCode(item.getPositionCode())
						.positionName(item.getPositionName()).sequenceCode(item.getSequenceCode())
						.startDate(item.getStartDate()).endDate(item.getEndDate()).build())
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<JobTitleExport> findJobTitleBySid(String employeeId, GeneralDate baseDate) {
		// Query
		Optional<AffJobTitleHistory> optHistory = this.jobTitleHistoryRepository
				.findBySid(employeeId, baseDate);

		// Check exist
		if (!optHistory.isPresent()) {
			return Optional.empty();
		}

		// Get infos
		List<JobTitleImport> jobTitleImports = this.jobTitleAdapter
				.findByJobIds(Arrays.asList(optHistory.get().getJobTitleId().v()));

		// Check exist
		if (CollectionUtil.isEmpty(jobTitleImports)) {
			return Optional.empty();
		}

		// Return
		JobTitleImport item = jobTitleImports.get(FIRST_ITEM_INDEX);
		return Optional.of(JobTitleExport.builder().companyId(item.getCompanyId())
				.positionId(item.getPositionId()).positionCode(item.getPositionCode())
				.positionName(item.getPositionName()).sequenceCode(item.getSequenceCode())
				.startDate(item.getStartDate()).endDate(item.getEndDate()).build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.jobtitle.SyJobTitlePub#
	 * findJobTitleByPositionId(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<JobTitleExport> findJobTitleByPositionId(String companyId, String positionId,
			GeneralDate baseDate) {
		// Query
		List<JobTitleImport> jobTitleImports = this.jobTitleAdapter.findByJobIds(companyId,
				Arrays.asList(positionId), baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(jobTitleImports)) {
			return Optional.empty();
		}

		// Return
		JobTitleImport item = jobTitleImports.get(FIRST_ITEM_INDEX);
		return Optional.of(JobTitleExport.builder().companyId(item.getCompanyId())
				.positionId(item.getPositionId()).positionCode(item.getPositionCode())
				.positionName(item.getPositionName()).sequenceCode(item.getSequenceCode())
				.startDate(item.getStartDate()).endDate(item.getEndDate()).build());
	}

}
