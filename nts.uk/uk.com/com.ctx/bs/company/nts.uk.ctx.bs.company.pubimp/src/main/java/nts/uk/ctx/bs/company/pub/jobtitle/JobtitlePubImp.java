/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.pub.jobtitle;
/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleRepository;

/**
 * The Class JobtitlePubImp.
 */
@Stateless
public class JobtitlePubImp implements JobtitlePub {

	/** The job title repository. */
	@Inject
	private JobTitleRepository jobTitleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.jobtitle.JobtitlePub#findAll(java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public List<JobtitleExport> findAll(String companyId, GeneralDate referenceDate) {
		return jobTitleRepository.findAll(companyId, referenceDate).stream()
				.map(item -> new JobtitleExport(item.getCompanyId().v(), item.getPositionId().v(),
						item.getPositionCode().v(), item.getPositionName().v(),
						item.getSequenceCode().v(), item.getPeriod().getStartDate(),
						item.getPeriod().getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.jobtitle.JobtitlePub#findByJobIds(java.util.
	 * List)
	 */
	@Override
	public List<JobtitleExport> findByJobIds(List<String> jobIds) {
		return jobTitleRepository.findByJobIds(jobIds).stream()
				.map(item -> new JobtitleExport(item.getCompanyId().v(), item.getPositionId().v(),
						item.getPositionCode().v(), item.getPositionName().v(),
						item.getSequenceCode().v(), item.getPeriod().getStartDate(),
						item.getPeriod().getEndDate()))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.company.pub.jobtitle.JobtitlePub#findByJobIds(java.lang.
	 * String, java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<JobtitleExport> findByJobIds(String companyId, List<String> jobIds,
			GeneralDate baseDate) {
		return jobTitleRepository.findByJobIds(companyId, jobIds, baseDate).stream()
				.map(item -> new JobtitleExport(item.getCompanyId().v(), item.getPositionId().v(),
						item.getPositionCode().v(), item.getPositionName().v(),
						item.getSequenceCode().v(), item.getPeriod().getStartDate(),
						item.getPeriod().getEndDate()))
				.collect(Collectors.toList());
	}

}
