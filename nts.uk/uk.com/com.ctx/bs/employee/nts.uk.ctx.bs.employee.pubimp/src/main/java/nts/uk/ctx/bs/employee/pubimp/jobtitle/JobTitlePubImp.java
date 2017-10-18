/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.pubimp.jobtitle;
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistory;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistoryRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitleRepository;
import nts.uk.ctx.bs.employee.pub.jobtitle.JobTitleExport;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;

/**
 * The Class JobTitlePubImp.
 */
@Stateless
public class JobTitlePubImp implements SyJobTitlePub {

	/** The first item index. */
	private final int FIRST_ITEM_INDEX = 0;

	/** The job title repository. */
	@Inject
	private JobTitleRepository jobTitleRepository;

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

		List<JobTitle> jobTitleDtos = this.jobTitleRepository.findByJobIds(jobIds);

		// Return
		return jobTitleDtos.stream().map(item -> JobTitleExport.builder()
				.companyId(item.getCompanyId().v()).positionId(item.getPositionId().v())
				.positionCode(item.getPositionCode().v()).positionName(item.getPositionName().v())
				.sequenceCode(item.getSequenceCode().v()).startDate(item.getPeriod().start())
				.endDate(item.getPeriod().end()).build()).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.employee.EmployeePub#findJobTitleBySid(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<JobTitleExport> findBySid(String employeeId, GeneralDate baseDate) {
		// Query
		Optional<AffJobTitleHistory> optHistory = this.jobTitleHistoryRepository
				.findBySid(employeeId, baseDate);

		// Check exist
		if (!optHistory.isPresent()) {
			return Optional.empty();
		}

		// Get infos
		List<JobTitle> jobTitleImports = this.jobTitleRepository
				.findByJobIds(Arrays.asList(optHistory.get().getJobTitleId().v()));

		// Check exist
		if (CollectionUtil.isEmpty(jobTitleImports)) {
			return Optional.empty();
		}

		// Return
		JobTitle item = jobTitleImports.get(FIRST_ITEM_INDEX);
		return Optional.of(JobTitleExport.builder().companyId(item.getCompanyId().v())
				.positionId(item.getPositionId().v()).positionCode(item.getPositionCode().v())
				.positionName(item.getPositionName().v()).sequenceCode(item.getSequenceCode().v())
				.startDate(item.getPeriod().start()).endDate(item.getPeriod().end())
				.build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.bs.employee.pub.employee.jobtitle.SyJobTitlePub#
	 * findJobTitleByPositionId(java.lang.String, java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<JobTitleExport> findByJobId(String companyId, String positionId,
			GeneralDate baseDate) {
		// Query
		List<JobTitle> jobTitleImports = this.jobTitleRepository.findByJobIds(companyId,
				Arrays.asList(positionId), baseDate);

		// Check exist
		if (CollectionUtil.isEmpty(jobTitleImports)) {
			return Optional.empty();
		}

		// Return
		JobTitle item = jobTitleImports.get(FIRST_ITEM_INDEX);
		return Optional.of(JobTitleExport.builder().companyId(item.getCompanyId().v())
				.positionId(item.getPositionId().v()).positionCode(item.getPositionCode().v())
				.positionName(item.getPositionName().v()).sequenceCode(item.getSequenceCode().v())
				.startDate(item.getPeriod().start()).endDate(item.getPeriod().end())
				.build());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub#findByBaseDate(java.
	 * lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<JobTitleExport> findAll(String companyId, GeneralDate baseDate) {
		// Query
		List<JobTitle> jobTitleDtos = this.jobTitleRepository.findAll(companyId, baseDate);

		// Return
		return jobTitleDtos.stream().map(item -> JobTitleExport.builder()
				.companyId(item.getCompanyId().v()).positionId(item.getPositionId().v())
				.positionCode(item.getPositionCode().v()).positionName(item.getPositionName().v())
				.sequenceCode(item.getSequenceCode().v()).startDate(item.getPeriod().start())
				.endDate(item.getPeriod().end()).build()).collect(Collectors.toList());
	}

}
