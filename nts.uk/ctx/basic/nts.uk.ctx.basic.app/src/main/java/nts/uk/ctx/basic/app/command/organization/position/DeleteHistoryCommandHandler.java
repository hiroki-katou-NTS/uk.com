//package nts.uk.ctx.basic.app.command.position.hoatt;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.HistoryId;
//import nts.uk.ctx.basic.dom.organization.position.hoatt.PositionRepository;
//import nts.uk.shr.com.context.AppContexts;
//
//	@Stateless
//	public class DeleteHistoryCommandHandler extends CommandHandler<DeleteHistoryCommand>{
//
//		@Inject
//		private PositionRepository positionRepo;
//		
//		@Override
//		protected void handle(CommandHandlerContext<DeleteHistoryCommand> context) {
//
//			String companyCode = AppContexts.user().companyCode();
//			positionRepo.deleteHist(companyCode, 
//					new HistoryId(context.getCommand().getHistoryId()));
//		}
//}
