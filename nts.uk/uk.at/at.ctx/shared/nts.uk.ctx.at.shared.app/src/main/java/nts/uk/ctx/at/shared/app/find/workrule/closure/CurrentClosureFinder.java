/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.workrule.closure.dto.CurrentClosureDto;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class CurrentClosureFinder.
 */
@Stateless
public class CurrentClosureFinder {

	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

	/** The closure service. */
	@Inject 
	private ClosureService closureService;
	
	/**
	 * Find closure name and period.
	 *
	 * @return the list
	 */
	public List<CurrentClosureDto> findCurrentClosure() {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		List<Closure> listClosure = this.closureRepository.findAllActive(companyId, UseClassification.UseClass_Use);
		List<CurrentClosureDto> listCurrentClosureDto = new ArrayList<CurrentClosureDto>();

		for (Closure closure : listClosure) {
			Optional<ClosureHistory> opClosureHistory = this.closureRepository.findByClosureIdAndCurrentMonth(companyId,
					closure.getClosureId().value, closure.getClosureMonth().getProcessingYm().v());

			DatePeriod currentPeriod = this.closureService.getClosurePeriod(closure.getClosureId().value,
					closure.getClosureMonth().getProcessingYm());

			if (opClosureHistory.isPresent()) {
				CurrentClosureDto currentClosureDto = CurrentClosureDto.builder()
						.closureId(opClosureHistory.get().getClosureId().value)
						.startDate(currentPeriod.start())
						.endDate(currentPeriod.end())
						.closureName(opClosureHistory.get().getClosureName().v())
						.processingDate(closure.getClosureMonth().getProcessingYm().v())
						.build();
				listCurrentClosureDto.add(currentClosureDto);
			}
		}

		return listCurrentClosureDto;
	}

}
