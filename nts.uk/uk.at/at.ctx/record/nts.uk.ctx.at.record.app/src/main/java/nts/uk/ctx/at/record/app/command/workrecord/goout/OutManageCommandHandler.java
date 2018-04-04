package nts.uk.ctx.at.record.app.command.workrecord.goout;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;

/**
 * The Class OutManageCommandHandler.
 *
 * @author hoangdd
 */
@Stateless
public class OutManageCommandHandler extends CommandHandler<OutManageCommand>{

	/** The repository. */
	@Inject
	private OutManageRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutManageCommand> context) {

		OutManageCommand command = context.getCommand();
		OutManage domain = new OutManage(command);
		if (repository.findByID(command.getCompanyID()).isPresent()) {
			repository.update(domain);
		} else {
			repository.add(domain);
		}
	}

}

