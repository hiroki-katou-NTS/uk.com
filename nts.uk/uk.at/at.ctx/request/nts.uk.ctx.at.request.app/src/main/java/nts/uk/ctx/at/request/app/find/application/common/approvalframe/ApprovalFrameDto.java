package nts.uk.ctx.at.request.app.find.application.common.approvalframe;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalATR;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmATR;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.Reason;

/**
 * 
 * @author hieult
 *
 */
@Value
public class ApprovalFrameDto {
	/** 会社ID */
	private String companyID;
	/** フェーズID */
	private String phaseID ;
	
	/** 順序 */
	private int dispOrder ;
	
	/** 承認者 */
	private String authorizerSID ;
	
	/** 代行者 */
	private String substituteSID ; 
	
	/** 承認区分 */
	private ApprovalATR approvalATR;

	/** 日付 */
	private String approvalDate;
	
	/** 理由 */
	private Reason reason;
	
	/** 確定区分 */
	private ConfirmATR confirmATR;
	
	public static ApprovalFrameDto fromDomain (ApprovalFrame domain){
		
		return new ApprovalFrameDto (
				domain.getCompanyID(),
				domain.getPhaseID(),
				domain.getDispOrder(),
				domain.getAuthorizerSID(),
				domain.getSubstituteSID(),
				domain.getApprovalATR(),
				domain.getApprovalDate(),
				domain.getReason(),
				domain.getConfirmATR());
	}
}
