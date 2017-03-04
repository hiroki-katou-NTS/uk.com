//package nts.uk.ctx.basic.app.command.position.hoatt;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.JobHistory;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.PositionRepository;
//
//
//@Stateless
//public class AddHistoryCommandHandler extends CommandHandler<AddHistoryCommand> {
//
//	@Inject
//	private PositionRepository positionRepo;
//		
////	@Override
////	protected void handle(CommandHandlerContext<AddHistoryCommand> context) {
////		AddHistoryCommand create = context.getCommand();		 	 
////		JobHistory position = create.toDomain();		 
////		position.validate();
////		this.positionRepo.addHistory(position);
////	}
//}
