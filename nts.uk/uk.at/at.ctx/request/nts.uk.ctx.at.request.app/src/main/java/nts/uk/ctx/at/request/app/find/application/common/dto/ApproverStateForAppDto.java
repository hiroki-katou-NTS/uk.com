package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;

@Value
@AllArgsConstructor
public class ApproverStateForAppDto {
	private String approverID;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approvalDate;
	
	private String approvalReason;
	
	private String approverMail;
	
	private String representerMail;
	
	private Integer approverInListOrder;
	
	public static ApproverStateForAppDto fromDomain(ApproverStateImport_New approverStateImport) {
		return new ApproverStateForAppDto(
				approverStateImport.getApproverID(), 
				approverStateImport.getApprovalAtr().value, 
				approverStateImport.getApprovalAtr().name, 
				approverStateImport.getAgentID(), 
				approverStateImport.getApproverName(), 
				approverStateImport.getRepresenterID(), 
				approverStateImport.getRepresenterName(), 
				approverStateImport.getApprovalDate() == null ? null : approverStateImport.getApprovalDate().toString(), 
				approverStateImport.getApprovalReason(), 
				approverStateImport.getApproverEmail(), 
				approverStateImport.getRepresenterEmail(),
				approverStateImport.getApproverInListOrder());
	}
	
	public ApproverStateImport_New toDomain() {
		return new ApproverStateImport_New(
				approverID, 
				EnumAdaptor.valueOf(approvalAtrValue, ApprovalBehaviorAtrImport_New.class), 
				agentID, 
				approverName, 
				representerID, 
				representerName, 
				approvalDate == null ? null : GeneralDateTime.fromString(approvalDate, "yyyy/MM/dd HH:mm:ss"), 
				approvalReason, 
				approverMail, 
				representerMail,
				approverInListOrder);
	}
}
