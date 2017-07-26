/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.budget.external;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExtBudgetDataPreviewDto;
import nts.uk.ctx.at.schedule.app.find.budget.external.actualresult.ExternalBudgetLogDto;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ExternalBudgetFinder {

	@Inject
	private ExternalBudgetRepository externalBudgetRepo;
	
	/** The ext budget log repo. */
	@Inject
    private ExternalBudgetLogRepository extBudgetLogRepo;

	/*
	 * get All List iTem of external Budget
	 */
	public List<ExternalBudgetDto> findAll() {
		String companyId = AppContexts.user().companyId();
		List<ExternalBudgetDto> lstBudget = this.externalBudgetRepo.findAll(companyId).stream()
				.map(item -> ExternalBudgetDto.fromDomain(item))
				.collect(Collectors.toList());
		return lstBudget;
	}
	
	/**
	 * Find data preview.
	 *
	 * @param file the file
	 * @return the ext budget data preview dto
	 */
	public ExtBudgetDataPreviewDto findDataPreview(File file) {
	    return null;
	}
	
	/**
	 * Find all external budget log.
	 *
	 * @return the list
	 */
	public List<ExternalBudgetLogDto> findAllExternalBudgetLog() {
	    String companyId = AppContexts.user().companyId();
	    return null;
	}
}
