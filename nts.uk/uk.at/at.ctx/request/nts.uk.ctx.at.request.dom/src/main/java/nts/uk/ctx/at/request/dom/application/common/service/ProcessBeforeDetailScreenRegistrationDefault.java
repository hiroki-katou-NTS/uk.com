package nts.uk.ctx.at.request.dom.application.common.service;

import javax.ejb.Stateless;

@Stateless
public class ProcessBeforeDetailScreenRegistrationDefault {
	
	public void processBeforeDetailScreenRegistration(String companyID, String employeeID, String appDate, int employeeRouteAtr, String targetApp, int postAtr ){
		/*
		// 選択した勤務種類の矛盾チェック(check sự mâu thuẫn của worktype đã chọn)
		selectedWorkTypeConflictCheck();
		
		// アルゴリズム「確定チェック」を実施する(thực hiện xử lý 「確定チェック」)
		OtherCommonAlgorithm.employeePeriodCurrentMonthCalculate();
		
		アルゴリズム「排他チェック」を実行する(thực hiện xử lý 「排他チェック」)
		exclusiveCheck();
		 */
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
