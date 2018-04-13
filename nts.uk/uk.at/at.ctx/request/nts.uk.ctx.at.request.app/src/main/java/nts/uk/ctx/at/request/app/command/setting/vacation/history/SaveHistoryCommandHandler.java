/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.app.command.setting.vacation.history;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.settting.worktype.history.OptionalMaxDay;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class SaveHistoryCommandHandler.
 */
@Stateless
@Transactional
public class SaveHistoryCommandHandler extends CommandHandler<VacationHistoryCommand> {

	/** The vacation history repository. */
	@Inject
	private VacationHistoryRepository vacationHistoryRepository;

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

		// check conditional
		if (command.getVacationHistory().getStartDate().after(command.getVacationHistory().getEndDate())) {
			throw new BusinessException("Msg_917");
		}

		DatePeriod period = new DatePeriod(command.getVacationHistory().getStartDate(),
				command.getVacationHistory().getEndDate());
		Integer count = this.vacationHistoryRepository.countByDatePeriod(companyId, command.getWorkTypeCode(), period,
				command.getVacationHistory().getHistoryId());

		if (count.intValue() > 0) {
			throw new BusinessException("Msg_106");
		}

		if (command.getVacationHistory().getStartDate().year() != command.getVacationHistory().getEndDate().year()) {
			throw new BusinessException("Msg_967");
		}

		// check isNewMode
		if (command.getIsCreated()) {
			if (this.vacationHistoryRepository.findByWorkTypeCode(companyId, command.getWorkTypeCode()).size() >= 20) {
				throw new BusinessException("Msg_976");
			}

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

		this.vacationHistoryRepository.update(history);
	}
}
