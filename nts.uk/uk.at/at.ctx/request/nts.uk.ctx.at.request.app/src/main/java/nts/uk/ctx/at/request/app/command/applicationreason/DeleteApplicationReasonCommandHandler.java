//package nts.uk.ctx.at.request.app.command.applicationreason;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
//import nts.uk.shr.com.context.AppContexts;
//
///**
// * 
// * @author yennth
// *
// */
//@Stateless
//@Transactional
//public class DeleteApplicationReasonCommandHandler  extends CommandHandler<DeleteApplicationReasonCommand>{
//	@Inject
//	private ApplicationReasonRepository reasonRep;
//
//	@Override
//	protected void handle(CommandHandlerContext<DeleteApplicationReasonCommand> context) {
//		DeleteApplicationReasonCommand delete = context.getCommand();
//		String companyId = AppContexts.user().companyId();
//		reasonRep.deleteReason(companyId, delete.getAppType(), delete.getReasonID());
//	}
//	
//	
//}
