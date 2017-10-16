/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.jobtitle_old;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.jobtitle_old.dto.JobTitleDto;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitle;
import nts.uk.ctx.bs.employee.dom.jobtitle_old.JobTitleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JobTitleFinder.
 */
@Stateless
public class JobTitleFinderOld {

	/** The repository. */
	@Inject
	private JobTitleRepository repository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<JobTitleDto> findAll(GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		List<JobTitle> jobs = repository.findAll(companyId, baseDate);

		return jobs.stream().map(job -> {
			JobTitleDto memento = new JobTitleDto();
			job.saveToMemento(memento);
			return memento;
		}).collect(Collectors.toList());
	}
}
