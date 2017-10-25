package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;

@Stateless
public class DetailBeforeUpdateImpl implements DetailBeforeUpdate {
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithmService;
	
	@Inject
	private NewBeforeRegister newBeforeRegister;
	
	public void processBeforeDetailScreenRegistration(String companyID, String employeeID, GeneralDate appDate, int employeeRouteAtr, String appID, PrePostAtr postAtr ){

		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		// selectedWorkTypeConflictCheck();
		
		// アルゴリズム「確定チェック」を実施する(thực hiện xử lý 「確定チェック」)
		newBeforeRegister.confirmationCheck(companyID, employeeID, appDate);
		
		exclusiveCheck();
	}
	
	public void exclusiveCheck(){
		/*
		// 排他制御を行う  ????
		result = exclusiveCheck();
		if(result.hasError){
			throw new BusinessException(Msg_197);
			reloadScreen();
		}
		
		// xử lý đã được bao gồm khi update
		*/
	}
}
