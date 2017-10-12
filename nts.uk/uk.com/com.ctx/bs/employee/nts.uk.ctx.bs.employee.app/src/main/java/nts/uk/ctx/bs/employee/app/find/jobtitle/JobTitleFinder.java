/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.jobtitle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.JobTitleFindDto;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle.JobTitleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JobTitleFinder.
 */
@Stateless
public class JobTitleFinder {
	
	/** The repository. */
	@Inject
	private JobTitleRepository repository;
	
	/**
	 * Find job history by job id.
	 *
	 * @param jobTitleId the job title id
	 * @return the job title find dto
	 */
	public JobTitleFindDto findJobHistoryByJobId(String jobTitleId) {
		String companyId = AppContexts.user().companyId();
		Optional<JobTitle> opJobTitle = this.repository.findByJobTitleId(companyId, jobTitleId);
		
		if (opJobTitle.isPresent()) {
			JobTitleFindDto dto = new JobTitleFindDto();
			opJobTitle.get().saveToMemento(dto);
			return dto;
		}		
		return null;
	}
}
