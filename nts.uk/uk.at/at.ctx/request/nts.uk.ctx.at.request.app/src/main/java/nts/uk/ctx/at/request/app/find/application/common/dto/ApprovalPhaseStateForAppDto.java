package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFormImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApprovalPhaseStateForAppDto {
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private Integer approvalFormValue;
	
	private List<ApprovalFrameForAppDto> listApprovalFrame;
	
	public static ApprovalPhaseStateForAppDto fromApprovalPhaseStateImport(ApprovalPhaseStateImport_New approvalPhaseStateImport){
		return new ApprovalPhaseStateForAppDto(
				approvalPhaseStateImport.getPhaseOrder(), 
				approvalPhaseStateImport.getApprovalAtr().value,
				approvalPhaseStateImport.getApprovalAtr().name,
				approvalPhaseStateImport.getApprovalForm().value,
				approvalPhaseStateImport.getListApprovalFrame().stream().map(x -> ApprovalFrameForAppDto.fromApprovalFrameImport(x)).collect(Collectors.toList()));
	}
	
	public ApprovalPhaseStateImport_New toDomain() {
		return new ApprovalPhaseStateImport_New(
				phaseOrder, 
				EnumAdaptor.valueOf(approvalAtrValue, ApprovalBehaviorAtrImport_New.class),
				EnumAdaptor.valueOf(approvalFormValue, ApprovalFormImport.class), 
				listApprovalFrame.stream().map(x -> x.toDomain()).collect(Collectors.toList()));
	}
}
