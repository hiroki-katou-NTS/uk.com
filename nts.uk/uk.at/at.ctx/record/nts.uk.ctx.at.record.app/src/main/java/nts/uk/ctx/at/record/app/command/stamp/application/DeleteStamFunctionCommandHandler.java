package nts.uk.ctx.at.record.app.command.stamp.application;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stamp.application.StampResultDisplayRepository;

@Stateless
public class DeleteStamFunctionCommandHandler extends CommandHandler<DeleteStamFunctionCommand>{
	
	@Inject
	private StampResultDisplayRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteStamFunctionCommand> context) {
		repo.delete();
	}
}
