package nts.uk.ctx.at.shared.app.command.entranceexit;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExit;
import nts.uk.ctx.at.shared.dom.entranceexit.ManageEntryExitRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hoangdd
 *
 */
@Stateless
public class ManageEntryExitCommandHandler extends CommandHandler<ManageEntryExitCommand>{

	@Inject
	private ManageEntryExitRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ManageEntryExitCommand> context) {
		String companyID = AppContexts.user().companyId(); 
		ManageEntryExitCommand command = context.getCommand();
		 
		Optional<ManageEntryExit> optDomain = repository.findByID(companyID);
		ManageEntryExit domain = new ManageEntryExit(command);
		if (!optDomain.isPresent()) {
			this.repository.add(domain);
		} else {
			this.repository.update(domain);
		}
	}

}

