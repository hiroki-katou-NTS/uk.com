package nts.uk.ctx.at.request.app.find.application.common;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalForm;

/**
 * 
 * @author hieult
 *
 */

/**
 * 10-2.詳細画面解除後の処理
 */
@Value
public class AfterDetailScreenProcessDto {
	
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
	private ApprovalAtr approvalATR;

	public static AfterDetailScreenProcessDto fromDomain(AppApprovalPhase domain){
		return new AfterDetailScreenProcessDto (
				domain.getCompanyID(),
				domain.getAppID(),
				domain.getPhaseID(),
				domain.getApprovalForm(),
				domain.getDispOrder(),
				domain.getApprovalATR());
	}
}
