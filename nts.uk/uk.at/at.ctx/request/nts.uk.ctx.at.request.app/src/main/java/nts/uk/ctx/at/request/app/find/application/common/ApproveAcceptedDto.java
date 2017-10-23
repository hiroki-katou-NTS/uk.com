package nts.uk.ctx.at.request.app.find.application.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.appapprovalphase.ApprovalAtr;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approvalframe.ConfirmAtr;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.ApproveAccepted;
import nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted.Reason;

@Data
@AllArgsConstructor
public class ApproveAcceptedDto {
	private String companyID;
	private String appAcceptedID ;
	private String approverSID ;
	private int approvalATR;
	private String nameApprovalATR;
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
				null,
				domain.getConfirmATR().value, 
				domain.getApprovalDate() ==null?null : domain.getApprovalDate().toString(), 
				domain.getReason().v(), 
				domain.getRepresenterSID());
	}
	public static ApproveAccepted toEntity(ApproveAcceptedDto entity) {
		return new ApproveAccepted(
				entity.getCompanyID(), 
				entity.getAppAcceptedID(), 
				entity.getApproverSID(), 
				EnumAdaptor.valueOf(entity.getApprovalATR(), ApprovalAtr.class), 
				EnumAdaptor.valueOf(entity.getConfirmATR(), ConfirmAtr.class), 
				entity.getApprovalDate() ==null?null:GeneralDate.fromString(entity.getApprovalDate(), "yyyy/MM/dd"), 
				new Reason(entity.getReason()), 
				entity.getRepresenterSID());
	}
}
