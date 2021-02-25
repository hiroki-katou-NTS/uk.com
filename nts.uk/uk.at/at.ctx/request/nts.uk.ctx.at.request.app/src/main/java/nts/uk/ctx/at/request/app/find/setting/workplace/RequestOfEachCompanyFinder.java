//package nts.uk.ctx.at.request.app.find.setting.workplace;
//
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompany;
//import nts.uk.ctx.at.request.dom.setting.workplace.RequestOfEachCompanyRepository;
//import nts.uk.shr.com.context.AppContexts;
///**
// * 
// * @author yennth
// *
// */
//@Stateless
//public class RequestOfEachCompanyFinder {
//	@Inject
//	private RequestOfEachCompanyRepository requestRep;
//	/**
//	 * find Request Of Each Company  
//	 * @return
//	 */
//	public RequestOfEachCompanyDto findRequest(){
//		String companyId = AppContexts.user().companyId(); 
//		Optional<RequestOfEachCompany> requestCom = this.requestRep.getRequestByCompany(companyId);
//		if(requestCom.isPresent()){
//			return new RequestOfEachCompanyDto(
//					requestCom.get().getCompanyID(), 
//					requestCom.get().getSelectionFlg().value,
//					requestCom.get().getListApprovalFunctionSetting().stream().map(x -> ApprovalFunctionSettingDto.convertToDto(x)).collect(Collectors.toList()));
//		}
//		return null;
//	}
//}
