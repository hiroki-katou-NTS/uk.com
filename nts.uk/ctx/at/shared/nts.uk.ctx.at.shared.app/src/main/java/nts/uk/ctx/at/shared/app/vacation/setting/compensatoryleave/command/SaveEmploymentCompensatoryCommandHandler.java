/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.compensatoryleave.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class SaveEmploymentCompensatoryCommandHandler extends CommandHandler<SaveEmploymentCompensatoryCommand> {

	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;

	@Override
	protected void handle(CommandHandlerContext<SaveEmploymentCompensatoryCommand> context) {
		SaveEmploymentCompensatoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveEmSetting domain = command.toDomain(companyId);

		CompensatoryLeaveEmSetting findItem = compensLeaveEmSetRepository.find(companyId);
		if (findItem != null) {
			compensLeaveEmSetRepository.update(domain);
		} else {
			compensLeaveEmSetRepository.insert(domain);
		}
	}

}
