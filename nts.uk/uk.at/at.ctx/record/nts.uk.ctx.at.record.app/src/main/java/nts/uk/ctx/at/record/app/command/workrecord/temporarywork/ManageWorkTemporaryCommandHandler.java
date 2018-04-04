/**
 * 
 */
package nts.uk.ctx.at.record.app.command.workrecord.temporarywork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporary;
import nts.uk.ctx.at.record.dom.workrecord.temporarywork.ManageWorkTemporaryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ManageWorkTemporaryCommandHandler.
 *
 * @author hoangdd
 */
@Stateless
public class ManageWorkTemporaryCommandHandler extends CommandHandler<ManageWorkTemporaryCommand>{

	/** The repository. */
	@Inject
	private ManageWorkTemporaryRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ManageWorkTemporaryCommand> context) {
		String companyId = AppContexts.user().companyId();
		
		ManageWorkTemporaryCommand command = context.getCommand();
		
		Optional<ManageWorkTemporary> optManageWorkTemporary = repository.findByCID(companyId);
		
		ManageWorkTemporary manageWorkTemporary = new ManageWorkTemporary(command);
		
		if (optManageWorkTemporary.isPresent()) {
			this.repository.update(manageWorkTemporary);
		} else {
			this.repository.add(manageWorkTemporary);
		}
	}
}

