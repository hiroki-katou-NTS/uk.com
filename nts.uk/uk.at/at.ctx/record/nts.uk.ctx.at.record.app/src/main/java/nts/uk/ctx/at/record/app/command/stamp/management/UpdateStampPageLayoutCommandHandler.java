package nts.uk.ctx.at.record.app.command.stamp.management;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.management.StampSetPerRepository;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class UpdateStampPageLayoutCommandHandler extends CommandHandler<StampPageLayoutCommand>{
	@Inject
	private StampSetPerRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<StampPageLayoutCommand> context) {
		StampPageLayoutCommand command = context.getCommand();
		this.repo.updatePage(command.toDomain());
	}
}
