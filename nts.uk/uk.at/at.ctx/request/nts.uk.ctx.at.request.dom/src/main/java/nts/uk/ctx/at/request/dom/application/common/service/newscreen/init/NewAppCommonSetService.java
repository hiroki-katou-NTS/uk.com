package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.AppCommonSetOutput;

/**
 * 1-1.新規画面起動前申請共通設定を取得する
 * @author Doan Duy Hung
 *
 */
public interface NewAppCommonSetService {
	
	public AppCommonSetOutput getNewAppCommonSet(String companyID, String employeeID, EmploymentRootAtr rootAtr, ApplicationType_Old appType, GeneralDate appDate);
	
}
