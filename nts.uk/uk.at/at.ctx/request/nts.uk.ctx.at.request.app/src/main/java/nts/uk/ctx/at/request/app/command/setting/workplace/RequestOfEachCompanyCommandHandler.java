//package nts.uk.ctx.at.request.app.command.setting.workplace;
//
///*import java.util.ArrayList;
//import java.util.List;*/
//import java.util.Optional;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.arc.layer.app.command.CommandHandler;
//import nts.arc.layer.app.command.CommandHandlerContext;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompany;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
//import nts.uk.shr.com.context.AppContexts;
//
///**
// * update request of each company
// * @author yennth
// */
//@Stateless
//public class RequestOfEachCompanyCommandHandler extends CommandHandler<RequestOfEachCompanyCommand>{
//	@Inject
//	private RequestOfEachCompanyRepository requestRep;
//
//	@Override
//	protected void handle(CommandHandlerContext<RequestOfEachCompanyCommand> context) {
//		String companyId = AppContexts.user().companyId();
//		Optional<RequestOfEachCompany> requestCom = requestRep.getRequestByCompany(companyId);
//		RequestOfEachCompany requestOfEachCompany = context.getCommand().toDomain(companyId);
//		if(requestCom.isPresent()){
//			requestRep.updateRequestOfEachCompany(requestOfEachCompany);
//		}else{
//			requestRep.insertRequestOfEachCompany(requestOfEachCompany);
//		}
//	}
//	
//}
