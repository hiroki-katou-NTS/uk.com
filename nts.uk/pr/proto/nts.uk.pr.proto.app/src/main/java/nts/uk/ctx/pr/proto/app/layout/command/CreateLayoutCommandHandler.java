package nts.uk.ctx.pr.proto.app.layout.command;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * CreateLayoutCommandHandler
 * @author lamvt
 *
 */
@RequestScoped
@Transactional
public class CreateLayoutCommandHandler extends CommandHandler<CreateLayoutCommand>{

	@Override
	protected void handle(CommandHandlerContext<CreateLayoutCommand> arg0) {
		// TODO Auto-generated method stub
		
	}

}
