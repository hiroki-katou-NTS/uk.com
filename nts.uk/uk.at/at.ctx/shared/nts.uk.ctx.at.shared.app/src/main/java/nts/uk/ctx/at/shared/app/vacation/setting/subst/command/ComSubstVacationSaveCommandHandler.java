/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.subst.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ComSubstVacationSaveCommandHandler.
 */
@Stateless
public class ComSubstVacationSaveCommandHandler
		extends CommandHandler<ComSubstVacationSaveCommand> {

	/** The repository. */
	@Inject
	private ComSubstVacationRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ComSubstVacationSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Get Command
		ComSubstVacationSaveCommand command = context.getCommand();

		// Update VacationAcquisitionRule
		Optional<ComSubstVacation> optComSubstVacation = this.repository.findById(companyId);

		// Check is managed, keep old values when is not managed
		if (optComSubstVacation.isPresent() && command.getIsManage() == ManageDistinct.NO.value) {
			SubstVacationSetting setting = optComSubstVacation.get().getSetting();
			command.setAllowPrepaidLeave(setting.getAllowPrepaidLeave().value);
			command.setExpirationDate(setting.getExpirationDate().value);
		}

		// Convert data
		ComSubstVacation comSubstVacation = command.toDomain(companyId);

		// Check exist
		if (optComSubstVacation.isPresent()) {
			// Update into db
			this.repository.update(comSubstVacation);
		} else {
			// Insert into db
			this.repository.insert(comSubstVacation);
		}
	}
}
