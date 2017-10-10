package nts.uk.ctx.at.request.app.find.application.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.Reason;

@Data
@AllArgsConstructor
public class ApproveAcceptedDto {
	private String companyID;
	private String appAcceptedID ;
	private String approverSID ;
	private int approvalATR;
	private int confirmATR;
	private String approvalDate;
	private String reason;
	private String representerSID;
	
	public static ApproveAcceptedDto fromDomain(ApproveAccepted domain) {
		return new ApproveAcceptedDto(
				domain.getCompanyID(), 
				domain.getAppAcceptedID(), 
				domain.getApproverSID(), 
				domain.getApprovalATR().value, 
				domain.getConfirmATR().value, 
				domain.getApprovalDate().toString(), 
				domain.getReason().v(), 
				domain.getRepresenterSID());
	}
}
