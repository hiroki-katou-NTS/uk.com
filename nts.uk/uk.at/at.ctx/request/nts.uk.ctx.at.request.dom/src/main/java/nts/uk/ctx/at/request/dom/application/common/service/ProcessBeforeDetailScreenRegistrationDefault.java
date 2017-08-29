package nts.uk.ctx.at.request.dom.application.common.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

@Stateless
public class ProcessBeforeDetailScreenRegistrationDefault implements ProcessBeforeDetailScreenRegistrationService {
	
	@Inject
	private OtherCommonAlgorithmService otherCommonAlgorithmService;
	
	public void processBeforeDetailScreenRegistration(String companyID, String employeeID, GeneralDate appDate, int employeeRouteAtr, String targetApp, int postAtr ){

		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		// selectedWorkTypeConflictCheck();
		
		// アルゴリズム「確定チェック」を実施する(thực hiện xử lý 「確定チェック」)
		otherCommonAlgorithmService.employeePeriodCurrentMonthCalculate(companyID, employeeID, appDate);
		
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
		*/
	}
}
