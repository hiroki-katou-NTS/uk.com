package nts.uk.ctx.at.request.app.find.application.common;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;

@Value
public class ApproveAcceptedDto {
	/** 会社ID */
	private String companyID;
	/** フェーズID */
	private String phaseID ;
	
	/** 順序 */
	private int dispOrder ;
	
	/** 承認者 */
	private String approverSID ;
	
	public static ApproveAcceptedDto fromDomain(ApproveAccepted domain) {
		return new ApproveAcceptedDto(
				domain.getCompanyID(),
				domain.getPhaseID(),
				domain.getDispOrder(),
				domain.getApproverSID()
				);
	}
}
