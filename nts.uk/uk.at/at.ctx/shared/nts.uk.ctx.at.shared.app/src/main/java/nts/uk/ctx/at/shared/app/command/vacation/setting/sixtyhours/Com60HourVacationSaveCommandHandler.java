/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.sixtyhours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SixtyHourExtra;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationDomainEvent;
import nts.uk.ctx.at.shared.dom.vacation.setting.sixtyhours.Com60HourVacationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ComSubstVacationSaveCommandHandler.
 */
@Stateless
public class Com60HourVacationSaveCommandHandler
		extends CommandHandler<Com60HourVacationSaveCommand> {

	/** The repository. */
	@Inject
	private Com60HourVacationRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Com60HourVacationSaveCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyId = loginUserContext.companyId();
		Optional<Com60HourVacation> optCom60HourVacation = this.repository.findById(companyId);
		
		// Get Command
		Com60HourVacationSaveCommand command = context.getCommand();

		// Check is managed, keep old values when is not managed
		if (optCom60HourVacation.isPresent()) {
			Com60HourVacation setting = optCom60HourVacation.get();
			if (command.getIsManage() == ManageDistinct.NO.value) {
				command.setSixtyHourExtra(setting.getSixtyHourExtra().value);
				command.setDigestiveUnit(setting.getTimeVacationDigestUnit().getDigestUnit().value);
			}
		} else {
			if (command.getIsManage() == ManageDistinct.NO.value) {
				command.setSixtyHourExtra(SixtyHourExtra.ALLWAYS.value);
				command.setDigestiveUnit(TimeDigestiveUnit.OneMinute.value);
			}
		}
		
		Com60HourVacation com60HourVacation = command.toDomain(companyId);

		// Check exist
		if (optCom60HourVacation.isPresent()) {
			// Update into db
			this.repository.update(com60HourVacation);
		} else {
			// Insert into db
			this.repository.insert(com60HourVacation);
		}

		//get isManageByTime from DB
		int isManageDB = optCom60HourVacation.isPresent() ? optCom60HourVacation.get().getTimeVacationDigestUnit().getManage().value : -1;
		//check managementCategory change
		boolean isManage = command.getIsManage() != isManageDB;
		if (isManage) {
			boolean manage = command.getIsManage() == ManageDistinct.YES.value;
			val com60HourVacationEvent = new Com60HourVacationDomainEvent(manage);
			com60HourVacationEvent.toBePublished();
		}
	}

}
