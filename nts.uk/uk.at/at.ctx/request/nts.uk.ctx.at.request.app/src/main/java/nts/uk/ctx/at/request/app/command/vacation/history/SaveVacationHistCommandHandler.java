/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.app.command.vacation.history;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.vacation.history.OptionalMaxDay;
import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryPolicy;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveHistoryCommandHandler.
 */
@Stateless
@Transactional
public class SaveVacationHistCommandHandler extends CommandHandler<VacationHistoryCommand> {

	/** The vacation history repository. */
	@Inject
	private VacationHistoryRepository vacationHistoryRepository;
	
	@Inject
	private VacationHistoryPolicy vacationPolicy ;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<VacationHistoryCommand> context) {
		VacationHistoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		// check isNewMode
		if (command.getIsCreated()) {
			// Add
			this.addVacationHistory(companyId, command);
		} else {
			// Update
			this.updateVacationHistory(companyId, command);
		}
	}

	/**
	 * Adds the vacation history.
	 *
	 * @param companyId the company id
	 * @param command the command
	 */
	private void addVacationHistory(String companyId, VacationHistoryCommand command) {
		// To Domain
		PlanVacationHistory history = new PlanVacationHistory(companyId, command.getWorkTypeCode(),
				new OptionalMaxDay(command.getMaxDay()), command.getVacationHistory().getStartDate(),
				command.getVacationHistory().getEndDate());
		
		//check validate
		this.vacationPolicy.validate(command.getIsCreated(), history, true);

		// insert
		this.vacationHistoryRepository.add(history);
	}

	/**
	 * Update vacation history.
	 *
	 * @param companyId the company id
	 * @param command the command
	 */
	private void updateVacationHistory(String companyId, VacationHistoryCommand command) {

		// Get old historyId
		List<PlanVacationHistory> hist = this.vacationHistoryRepository.findHistory(companyId,
				command.getVacationHistory().getHistoryId());
		if (hist.isEmpty()) {
			return;
		}
		
		PlanVacationHistory history = new PlanVacationHistory(companyId, command.getWorkTypeCode(),
				new OptionalMaxDay(command.getMaxDay()), command.getVacationHistory().getHistoryId(),
				command.getVacationHistory().getStartDate(), command.getVacationHistory().getEndDate());
		
		boolean isCheck = true;
		
		if (command.getVacationHistory().getStartDate().equals(hist.get(0).span().start())
				&& command.getVacationHistory().getEndDate().equals(hist.get(0).span().end())) {
			isCheck = false;
		}
		
		//check validate
		this.vacationPolicy.validate(command.getIsCreated(), history, isCheck);

		this.vacationHistoryRepository.update(history);
	}
}
