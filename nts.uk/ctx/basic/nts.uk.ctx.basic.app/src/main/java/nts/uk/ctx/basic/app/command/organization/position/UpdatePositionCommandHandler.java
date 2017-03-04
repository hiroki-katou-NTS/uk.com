package nts.uk.ctx.basic.app.command.organization.position;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;


@Stateless
public class UpdatePositionCommandHandler extends CommandHandler<UpdatePositionCommand>{

	@Inject
	private PositionRepository positionRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdatePositionCommand> context) {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	protected void handle(CommandHandlerContext<UpdatePositionCommand> context) {
//		UpdatePositionCommand update = context.getCommand();
//		JobTitle position = update.toDomain();
//		position.validate();
//		this.positionRepo.update(position);
//		
//	}

	
}
