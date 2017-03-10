//package nts.uk.ctx.basic.app.command.position.hoatt;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.JobTitle;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.PositionRepository;
//import nts.uk.shr.com.context.AppContexts;
//
//@Stateless
//public class CreatePositionCommandHandler extends CommandHandler<CreatePositionCommand> {
//
//	@Inject
//	private PositionRepository positionRepo;
//		
//	@Override
//	protected void handle(CommandHandlerContext<CreatePositionCommand> context) {
//		CreatePositionCommand create = context.getCommand();		 	 
//		JobTitle position = create.toDomain();		 
//		position.validate();
//		this.positionRepo.add(position);
//	}
//
//}
