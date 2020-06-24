package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType_Old;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;

/**
 * 承認ルートから基準日として扱う日の判定
 *
 */
public interface RootBaseDateGet {
	public ApprovalRootPattern getBaseDateFromRoot(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType_Old appType, GeneralDate appDate, String appID, Boolean isCreate);
}
