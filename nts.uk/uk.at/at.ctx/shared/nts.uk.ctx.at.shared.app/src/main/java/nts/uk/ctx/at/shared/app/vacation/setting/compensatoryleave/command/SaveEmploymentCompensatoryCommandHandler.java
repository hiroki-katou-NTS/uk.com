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

/**
 * The Class SaveEmploymentCompensatoryCommandHandler.
 */
@Stateless
public class SaveEmploymentCompensatoryCommandHandler extends CommandHandler<SaveEmploymentCompensatoryCommand> {

	/** The compens leave em set repository. */
	@Inject
	CompensLeaveEmSetRepository compensLeaveEmSetRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmploymentCompensatoryCommand> context) {
		SaveEmploymentCompensatoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		CompensatoryLeaveEmSetting domain = command.toDomain(companyId);

		CompensatoryLeaveEmSetting findItem = compensLeaveEmSetRepository.find(companyId,domain.getEmploymentCode().v());
		if (findItem != null) {
			compensLeaveEmSetRepository.update(domain);
		} else {
			compensLeaveEmSetRepository.insert(domain);
		}
	}

}
