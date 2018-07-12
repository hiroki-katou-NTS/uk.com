//package nts.uk.ctx.at.record.app.command.resultsperiod.optionalaggregationperiod;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.gul.text.IdentifierUtil;
//import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInfor;
//import nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.AggrPeriodInforRepository;
//import nts.uk.shr.com.context.AppContexts;
//
//@Stateless
//public class AddErrorInforCommandHandler extends CommandHandler<AddErrorInforCommand>{
//
//	@Inject
//	private AggrPeriodInforRepository repository; 
//	
//	@Override
//	protected void handle(CommandHandlerContext<AddErrorInforCommand> context) {
//		AddErrorInforCommand command = context.getCommand();
//		String memberId = AppContexts.user().employeeId();
////		AggrPeriodInfor infor = command.toDomain(memberId);
////			this.repository.addPeriodInfor(infor);
////		
//	}
//
//}
