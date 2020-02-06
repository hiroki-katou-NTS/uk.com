/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.app.command.setting.vacation.history;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.app.command.vacation.history.VacationHistoryCommand;
import nts.uk.ctx.at.request.dom.vacation.history.OptionalMaxDay;
import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
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

		if (command.getIsCreated()) {

			// check conditional
			if (this.vacationHistoryRepository.findByWorkTypeCode(companyId, command.getWorkTypeCode()).size() >= 20) {
				throw new BusinessException("Msg_976");
			}

			if (command.getVacationHistory().getStartDate()
					.after(command.getVacationHistory().getEndDate())) {
				throw new BusinessException("Msg_917");
			}

			DatePeriod period = new DatePeriod(command.getVacationHistory().getStartDate(),
					command.getVacationHistory().getEndDate());
			Integer count = this.vacationHistoryRepository.countByDatePeriod(companyId, command.getWorkTypeCode(),
					period, command.getVacationHistory().getHistoryId());

			if (count.intValue() > 0) {
				throw new BusinessException("Msg_106");
			}

			if (command.getVacationHistory().getStartDate().year() != command.getVacationHistory().getEndDate().year()) {
				throw new BusinessException("Msg_967");
			}

			// Add
			this.addVacationHistory(companyId, command);
		} else {
			// Update
			// this.updateJobTitle(companyId, command);
		}
	}

	/**
	 * Adds the vacation history.
	 *
	 * @param companyId the company id
	 * @param command the command
	 */
	private void addVacationHistory(String companyId, VacationHistoryCommand command) {
		//To Domain
		PlanVacationHistory history = new PlanVacationHistory(companyId, command.getWorkTypeCode(), new OptionalMaxDay(command.getMaxDay()),
				command.getVacationHistory().getStartDate(), command.getVacationHistory().getEndDate());

		//insert
		this.vacationHistoryRepository.add(history);
	}

	// private void updateJobTitle(String companyId, SaveJobTitleCommand
	// command) {
	//
	// // Get old JobTitleCode
	// Optional<JobTitleCode> opJobTitleCode =
	// this.jobTitleInfoRepository.findJobTitleCode(companyId,
	// command.getJobTitleInfo().getJobTitleId());
	// if (!opJobTitleCode.isPresent()) {
	// return;
	// }
	//
	// // JobTitleCode is not changable
	// command.getJobTitleInfo().setJobTitleCode(opJobTitleCode.get().v());
	// this.jobTitleInfoRepository.update(command.toDomain(companyId));
	// }
}
