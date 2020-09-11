/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.company;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class EmploymentSaveCommandHandler.
 */
@Stateless
@Transactional
public class CompanyWtSettingSaveCommandHandler extends CommandHandler<CompanyWtSettingSaveCommand> {

	/** The repository. */
//	@Inject
//	private CompanyWtSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanyWtSettingSaveCommand> context) {
		// Get Command
//		CompanyWtSettingSaveCommand command = context.getCommand();
//
//		CompanyWtSetting companySetting = new CompanyWtSetting(command);
//
//		Optional<CompanyWtSetting> optCompanySetting = this.repository.find(AppContexts.user().companyId(),
//				command.getYear().v());
//		// Update
//		if (optCompanySetting.isPresent()) {
//			this.repository.update(companySetting);
//			return;
//		}
//		// Create
//		this.repository.create(companySetting);
	}

}
