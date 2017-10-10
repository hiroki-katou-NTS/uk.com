package nts.uk.ctx.sys.env.app.command.mailserver;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.env.dom.mailserver.MailServer;
import nts.uk.ctx.sys.env.dom.mailserver.MailServerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MailServerSaveCommandHandler extends CommandHandler<MailServerSaveCommand> {
	
	@Inject
	private MailServerRepository repository;
	
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
