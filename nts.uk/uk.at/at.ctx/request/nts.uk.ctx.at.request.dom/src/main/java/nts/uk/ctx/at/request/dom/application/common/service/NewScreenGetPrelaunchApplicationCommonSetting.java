package nts.uk.ctx.at.request.dom.application.common.service;

public class NewScreenGetPrelaunchApplicationCommonSetting {

	public void newScreenGetPrelaunchApplicationCommonSetting(String companyID, String employeeID, int employmentRouteAtr, String targetApp, String appDate){
		/*
		String baseDate;
		obj = ApplicationApprovalSetting.find(companyID);
		if(obj.baseDateFlg==APP_DATE) {
			if(appDate.isPresent) {
				baseDate = systemDate;
			} else {
				baseDate = appDate;
			}
		} else {
			baseDate = systemDate;
		}
		// 申請本人の所属職場を含める上位職場を取得する ( Acquire the upper workplace to include the workplace of the applicant himself / herself )
		List<String> workPlaceIDs = WorkPlace.find(companyID, employeeID, baseDate);
		loopResult = [];
		for(item in workPlaceIDs) {
			findItem = ApplicationApprovalSettingByWorkPlace.find(companyID, item.workPlaceID);
			if(findItem.isPresent) {
				loopResult.add(findItem);
				break;
			}
		} 
		if(loopResult.size == 0) {
			ApplicationApprovalSettingByCompany.find(companyID);
		}
		// アルゴリズム「社員所属雇用履歴を取得」を実行する ( Execute the algorithm "Acquire employee affiliation employment history" )
		employeeCD = EmployeeAffiliationEmploymentHistory.find(employeeID, baseDate);
		if(!employeeCD.isPresent) {
			throw new BusinessException(Msg_426);
		}
		ApplicationCommonSetting obj1 = ApplicationApprovalSettingByEmployment.find(companyID, employeeCD);
		return obj1;
		*/
	}
	
}
