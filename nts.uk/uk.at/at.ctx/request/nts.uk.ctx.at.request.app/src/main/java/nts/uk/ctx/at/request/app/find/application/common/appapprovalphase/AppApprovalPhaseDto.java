package nts.uk.ctx.at.request.app.find.application.common.appapprovalphase;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalATR;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;
@Value
public class AppApprovalPhaseDto {
	/** 会社ID */
	private String companyID;

	/** 申請ID */
	private String appID;

	/** フェーズID */
	private String phaseID;

	/** 承認形態 */
	private ApprovalForm approvalForm;

	/** 順序 */
	private int dispOrder;

	/** 承認区分 */
	private ApprovalATR approvalATR;
	
	public static AppApprovalPhaseDto fromDomain (AppApprovalPhase domain){
		return new AppApprovalPhaseDto(
				domain.getCompanyID(),
				domain.getAppID(),
				domain.getPhaseID(),
				domain.getApprovalForm(),
				domain.getDispOrder(),
				domain.getApprovalATR());
	}
}
