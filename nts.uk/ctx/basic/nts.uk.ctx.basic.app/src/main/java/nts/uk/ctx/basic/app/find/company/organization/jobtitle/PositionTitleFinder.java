/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.jobtitle;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.app.find.company.organization.jobtitle.dto.JobTitleDto;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitle;
import nts.uk.ctx.basic.dom.company.organization.jobtitle.JobTitleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JobTitleFinder.
 */
@Stateless
public class PositionTitleFinder {

	/** The repository. */
	@Inject
	private JobTitleRepository repository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<JobTitleDto> findAll() {
		String companyId = AppContexts.user().companyId();

		List<JobTitle> jobs = repository.findAll(companyId, GeneralDate.today());

		return jobs.stream().map(job -> {
			JobTitleDto memento = new JobTitleDto();
			job.saveToMemento(memento);
			return memento;
		}).collect(Collectors.toList());
	}
}
