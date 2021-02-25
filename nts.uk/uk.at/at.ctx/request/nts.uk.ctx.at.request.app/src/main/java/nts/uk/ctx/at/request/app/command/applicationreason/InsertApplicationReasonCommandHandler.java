//package nts.uk.ctx.at.request.app.command.applicationreason;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.arc.layer.app.command.CommandHandlerWithResult;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
//import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
//import nts.uk.shr.com.context.AppContexts;
//
///**
// * insert a item application reason
// * @author yennth
// *
// */
//@Stateless
//@Transactional
//public class InsertApplicationReasonCommandHandler extends CommandHandlerWithResult<ApplicationReasonCommand, String>{
//	@Inject
//	private ApplicationReasonRepository reasonRep;
//	
//	@Override
//	protected String handle(CommandHandlerContext<ApplicationReasonCommand> context) {
//		ApplicationReasonCommand insert = context.getCommand();
//		String companyId = AppContexts.user().companyId();
//		ApplicationReason appReason = ApplicationReason.createNew(companyId, insert.getAppType(), insert.getDispOrder(), insert.getReasonTemp(), insert.getDefaultFlg());
//		reasonRep.insertReason(appReason);
//		return companyId + "-" +  insert.getDispOrder() + "-" + appReason.getReasonID();
//	}
//
//}
