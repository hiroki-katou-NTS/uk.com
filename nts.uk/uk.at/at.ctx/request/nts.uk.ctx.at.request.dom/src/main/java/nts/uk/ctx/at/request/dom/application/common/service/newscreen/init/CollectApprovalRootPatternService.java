package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.ApprovalRootPattern;

/**
 * 1-4.新規画面起動時の承認ルート取得パターン
 * @author Doan Duy Hung
 *
 */
public interface CollectApprovalRootPatternService {
	
	public ApprovalRootPattern getApprovalRootPatternService(String companyID, String employeeID, 
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate, String appID, Boolean isCreate);
	
	/**
	 * 1-4.新規画面起動時の承認ルート取得パターン
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param rootAtr 就業ルート区分
	 * @param appType 申請種類
	 * @param appDate 基準日
	 * @return
	 */
	public ApprovalRootContentImport_New getApprovalRootPatternNew(String companyID, String employeeID, 
			EmploymentRootAtr rootAtr, ApplicationType appType, GeneralDate appDate);
}
