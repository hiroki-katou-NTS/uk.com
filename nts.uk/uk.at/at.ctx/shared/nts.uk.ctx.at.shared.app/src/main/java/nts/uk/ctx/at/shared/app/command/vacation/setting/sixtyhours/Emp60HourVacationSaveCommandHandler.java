/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Emp60HourVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.SixtyHourVacationSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ComSubstVacationSaveCommandHandler.
 */
@Stateless
public class Emp60HourVacationSaveCommandHandler extends CommandHandler<Emp60HourVacationSaveCommand> {

	/** The repository. */
	@Inject
	private Emp60HourVacationRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Emp60HourVacationSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();

		// Get Command
		Emp60HourVacationSaveCommand command = context.getCommand();

		// Update VacationAcquisitionRule
		Optional<Emp60HourVacation> optEmp60HVacation = this.repository.findById(companyId,
				command.getContractTypeCode());

		// Check is managed, keep old values when is not managed
		if (optEmp60HVacation.isPresent()) {
			SixtyHourVacationSetting setting = optEmp60HVacation.get().getSetting();
			if (command.getIsManage() == ManageDistinct.NO.value) {
				command.setSixtyHourExtra(setting.getSixtyHourExtra().value);
				command.setDigestiveUnit(setting.getDigestiveUnit().value);
			}
		} else {
			if (command.getIsManage() == ManageDistinct.NO.value) {
				command.setSixtyHourExtra(SixtyHourExtra.ALLWAYS.value);
				command.setDigestiveUnit(TimeDigestiveUnit.OneMinute.value);
			}
		}
		// Convert data
		Emp60HourVacation emp60HVacation = command.toDomain(companyId);

		// Check exist
		if (optEmp60HVacation.isPresent()) {
			// Update into db
			this.repository.update(emp60HVacation);
		} else {
			// Insert into db
			this.repository.insert(emp60HVacation);
		}

	}

}
