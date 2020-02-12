package nts.uk.ctx.hr.shared.app.databeforereflecting.find;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.RetirementInformation;
import nts.uk.ctx.hr.shared.dom.databeforereflecting.service.RetirementInformationService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CheckStatusRegistration {
	
	@Inject
	private RetirementInformationService retirementInfoService;
	

	// アルゴリズム[登録状況チェック]を実行する(Thực hiện thuật toán "CHeck tình trạng đăng ký ")
	public Boolean CheckStatusRegistration(String sid){
		
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
