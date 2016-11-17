package nts.uk.ctx.pr.proto.app.layout.command;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
/**
 * UpdateLayoutCommandHandler
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class UpdateLayoutCommandHandler extends CommandHandler<UpdateLayoutCommand> {

	@Override
	protected void handle(CommandHandlerContext<UpdateLayoutCommand> handlerContext) {
		
		
	}

	
}
