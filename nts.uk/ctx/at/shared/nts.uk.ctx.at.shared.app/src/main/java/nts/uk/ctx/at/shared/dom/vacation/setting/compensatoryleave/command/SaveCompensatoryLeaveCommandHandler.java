/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveCompensatoryLeaveCommandHandler extends CommandHandler<SaveCompensatoryLeaveCommand> {

	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveCompensatoryLeaveCommand> context) {
		SaveCompensatoryLeaveCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveComSetting clcs = command.toDomain(companyId);
		CompensatoryLeaveComSetting findClsc = compensLeaveComSetRepository.find(companyId);
		if (findClsc == null) {
			compensLeaveComSetRepository.insert(clcs);
		} else {
			compensLeaveComSetRepository.update(clcs);
		}
	}

}
