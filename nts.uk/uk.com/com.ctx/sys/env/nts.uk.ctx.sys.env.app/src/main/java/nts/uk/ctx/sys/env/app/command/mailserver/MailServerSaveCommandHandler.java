/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.app.command.mailserver;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.mailserver.MailServer;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class MailServerSaveCommandHandler.
 */
@Stateless
public class MailServerSaveCommandHandler extends CommandHandler<MailServerSaveCommand> {

	/** The repository. */
	@Inject
	private MailServerRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MailServerSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();

		// Get command
		MailServerSaveCommand command = context.getCommand();

		MailServer mailServer = new MailServer(command);

		// Find Mail server setting
		Optional<MailServer> mailSetting = this.repository.findBy(companyId);

		// Update
		if (mailSetting.isPresent()) {
			this.repository.update(mailServer);
			return;
		}

		// Create
		this.repository.add(mailServer);
	}

}
