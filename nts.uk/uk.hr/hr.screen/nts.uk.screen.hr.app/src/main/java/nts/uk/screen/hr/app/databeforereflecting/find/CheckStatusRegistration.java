package nts.uk.screen.hr.app.databeforereflecting.find;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.retiredemployeeinfo.service.RetirementInformationService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckStatusRegistration {
	
	@Inject
	private RetirementInformationService retirementInfoService;
	
	// Path : UKDesign.UniversalK.人事.JCM_異動・発令.JCM007_退職者の登録.A：退職者の登録.アルゴリズム.登録状況チェック
	// アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "CHeck tình trạng đăng ký ")
	public Boolean CheckStatusRegistration(String sid){
		
		
		// アルゴリズム[退職登録済みチェック]を実行する(thực hiện thuật toán[check đã đăng ký nghỉ việc])
		
		
		
		
		
		
		
		String cid = AppContexts.user().companyId();
		Integer workId = 1;
		List<String> listSid =  Arrays.asList(sid);
		Optional<Boolean> includReflected = Optional.of(true);
		Optional<String> sortByColumnName = Optional.empty();
		Optional<String> orderType = Optional.empty();

		// [個人情報の取得]("Get personal information")
		// todo 
		
		// アルゴリズム[退職者情報の取得]を実行する (Thực hiện thuật toán "Get Retired information")
		List<RetirementInformation> listRetirementInfo = retirementInfoService.getRetirementInfo(cid, workId, listSid,
				includReflected, sortByColumnName, orderType);
		
		if (!listRetirementInfo.isEmpty()) {
			throw new BusinessException("MsgJ_JCM007_5"); // MsgJ_JCM007_5
		}
		
		return true;
	}
}
