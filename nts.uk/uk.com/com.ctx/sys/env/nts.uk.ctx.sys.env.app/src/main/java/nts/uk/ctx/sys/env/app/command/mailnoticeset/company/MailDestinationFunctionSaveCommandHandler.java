/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailnoticeset.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunction;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.MailDestinationFunctionRepository;
import nts.uk.ctx.sys.env.dom.mailnoticeset.employee.UserInfoItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailDestinationFunctionSaveCommandHandler.
 */
@Stateless
@Transactional
public class MailDestinationFunctionSaveCommandHandler extends CommandHandler<MailDestinationFunctionSaveCommand> {

	/** The repo. */
	@Inject
	private MailDestinationFunctionRepository repo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MailDestinationFunctionSaveCommand> context) {

		String companyId = AppContexts.user().companyId();
		// Get new domain
		MailDestinationFunctionSaveCommand command = context.getCommand();
		MailDestinationFunction newDomain = command.toDomain();
		newDomain.setCompanyId(companyId);
		UserInfoItem settingItem = newDomain.getSettingItem();

		// Remove old domain then add new domain
		this.repo.remove(companyId, settingItem);
		this.repo.add(newDomain);
	}

}
