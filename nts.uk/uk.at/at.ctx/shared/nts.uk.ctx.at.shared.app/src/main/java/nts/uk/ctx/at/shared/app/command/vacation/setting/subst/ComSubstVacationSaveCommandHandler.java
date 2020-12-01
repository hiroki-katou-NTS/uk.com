/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.subst;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ExpirationTime;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.SubstVacationSettingDomainEvent;
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
		if (optComSubstVacation.isPresent()) {
			SubstVacationSetting setting = optComSubstVacation.get().getSetting();
			if (command.getManageDistinct() == ManageDistinct.NO.value) {
				command.setExpirationDate(setting.getExpirationDate().value);
				command.setAllowPrepaidLeave(setting.getAllowPrepaidLeave().value);
			}
		} else {
			if (command.getManageDistinct() == ManageDistinct.NO.value) {
				command.setAllowPrepaidLeave(ApplyPermission.ALLOW.value);
				command.setExpirationDate(ExpirationTime.THIS_MONTH.value);
			}
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
		
		//get isManageByTime from DB
		//int isManageDB = optComSubstVacation.isPresent() ? optComSubstVacation.get().getSetting().getIsManage().value : -1;
		int isManageDB = optComSubstVacation.isPresent() ? optComSubstVacation.get().getSetting().getManageDeadline().value: -1;
		//check managementCategory change
		boolean isManage = command.getManageDeadline()!= isManageDB;
		if (isManage) {
			boolean manage = command.getManageDeadline() == ManageDistinct.YES.value;
			val substVacationSettingEvent = new SubstVacationSettingDomainEvent(manage);
			substVacationSettingEvent.toBePublished();
		}
	}
}
