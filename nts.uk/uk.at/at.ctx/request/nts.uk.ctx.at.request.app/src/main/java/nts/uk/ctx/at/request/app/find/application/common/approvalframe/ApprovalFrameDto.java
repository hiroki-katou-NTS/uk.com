package nts.uk.ctx.at.request.app.find.application.common.approvalframe;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalFrame;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;

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
	private String approverSID ;
	
	/** 承認区分 */
	private ApprovalAtr approvalATR;
	
	/** 確定区分 */
	private ConfirmAtr confirmATR;
	
	public static ApprovalFrameDto fromDomain (ApprovalFrame domain){
		
		return new ApprovalFrameDto (
				domain.getCompanyID(),
				domain.getPhaseID(),
				domain.getDispOrder(),
				domain.getApproverSID(),
				domain.getApprovalATR(),
				domain.getConfirmATR());
	}
}
