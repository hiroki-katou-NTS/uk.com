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
//@Stateless
//public class UpdateHistoryCommandHandler extends CommandHandler<UpdateHistoryCommand>{
//
//	@Inject
//	private PositionRepository positionRepo;
//	
//	@Override
//	protected void handle(CommandHandlerContext<UpdateHistoryCommand> context) {
//		UpdateHistoryCommand update = context.getCommand();
//		JobHistory jobHist = update.toDomain();
//		//position.validate();
//		this.positionRepo.updateHistory(jobHist);
//		
//	}
//
//	
//}