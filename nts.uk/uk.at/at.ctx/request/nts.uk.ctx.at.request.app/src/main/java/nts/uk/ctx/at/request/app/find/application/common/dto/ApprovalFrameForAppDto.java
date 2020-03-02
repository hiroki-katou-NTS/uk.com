package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
@Value
@AllArgsConstructor
public class ApprovalFrameForAppDto {
	
	private Integer frameOrder;
	
	private List<ApproverStateForAppDto> listApprover;
	
	public static ApprovalFrameForAppDto fromApprovalFrameImport(ApprovalFrameImport_New approvalFrameImport){
		return new ApprovalFrameForAppDto(
				approvalFrameImport.getFrameOrder(), 
				approvalFrameImport.getListApprover().stream()
					.map(x -> new ApproverStateForAppDto(
							x.getApproverID(), 
							x.getApprovalAtr().value,
							x.getApprovalAtr().name, 
							x.getAgentID(),
							x.getApproverName(),
							x.getRepresenterID(),
							x.getRepresenterName(),
							x.getApprovalDate() == null ? null : x.getApprovalDate().toString("yyyy/MM/dd"),
							x.getApprovalReason(),
							x.getApproverEmail(),
							x.getRepresenterEmail()))
					.collect(Collectors.toList()));
	}
}
