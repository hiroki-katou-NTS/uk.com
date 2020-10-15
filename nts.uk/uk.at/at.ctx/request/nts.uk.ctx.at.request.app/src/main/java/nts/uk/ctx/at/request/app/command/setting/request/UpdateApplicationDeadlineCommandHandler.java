//package nts.uk.ctx.at.request.app.command.setting.request;
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//import javax.transaction.Transactional;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadline;
//import nts.uk.ctx.at.request.dom.setting.request.application.ApplicationDeadlineRepository;
//import nts.uk.shr.com.context.AppContexts;
//
//@Stateless
//@Transactional
//public class UpdateApplicationDeadlineCommandHandler extends CommandHandler<List<ApplicationDeadlineCommand>>{
//	@Inject
//	private ApplicationDeadlineRepository appRep;
//	/**
//	 * update application deadline
//	 */
//	@Override
//	protected void handle(CommandHandlerContext<List<ApplicationDeadlineCommand>> context) {
//		List<ApplicationDeadlineCommand> data = context.getCommand();
//		String companyId = AppContexts.user().companyId();
//		for(ApplicationDeadlineCommand item : data){
//			Optional<ApplicationDeadline> app = appRep.getDeadlineByClosureId(companyId, item.getClosureId());
//			ApplicationDeadline appDeadline = ApplicationDeadline.createSimpleFromJavaType(companyId, item.getClosureId(),
//																						item.getUserAtr(), item.getDeadline(), 
//																						item.getDeadlineCriteria());
//			appDeadline.validate();
//			if(app.isPresent()){
//				appRep.update(appDeadline);
//			}else{
//				appRep.insert(appDeadline);
//			}
//		}
//	}
//}
