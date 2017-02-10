//package nts.uk.ctx.pr.report.app.wageledger.command;
//
//import javax.ejb.Stateless;
//import javax.enterprise.context.spi.Context;
//import javax.inject.Inject;
//
//import lombok.val;
//import nts.arc.error.BusinessException;
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
//import nts.uk.shr.com.context.AppContexts;
//
//@Stateless
//public class AggregateItemSaveCommandHandler extends CommandHandler<AggregateItemSaveCommand>{
//	
//	@Inject
//	private WLAggregateItemRepository repository;
//
//	@Override
//	protected void handle(CommandHandlerContext<AggregateItemSaveCommand> context) {
//		val companyCode = AppContexts.user().companyCode();
//		val command = context.getCommand();
//		
//		// Check duplicate code.
//		if (this.repository.isExist(command.code)) {
//			throw new BusinessException("ER011");
//		}
//		
//		// 
//	}
//	
//}
