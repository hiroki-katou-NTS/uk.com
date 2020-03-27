package nts.uk.ctx.at.record.app.command.stamp.management;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class DeleteStampSettingCommandHandler extends CommandHandler<DeleteStampSettingCommand>{

	@Inject
	private StampSetPerRepository repository;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteStampSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		// get command
		DeleteStampSettingCommand command = context.getCommand();
		// delete process
		repository.delete(companyId,command.getPageNo());
	
	}

}
