package nts.uk.ctx.at.request.app.find.application.common.dto.approvesendmail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalPhaseStateOutput;
@Value
@AllArgsConstructor
public class ApproverPhaseStateSendDto {
	private Integer phaseOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApproverFrameSendDto> listApprovalFrame;
	
	public static ApproverPhaseStateSendDto fromApprovalPhaseState(ApprovalPhaseStateOutput approvalPhaseState){
		return new ApproverPhaseStateSendDto(
				approvalPhaseState.getPhaseOrder(), 
				approvalPhaseState.getApprovalAtr().value,
				approvalPhaseState.getApprovalAtr().name,
				approvalPhaseState.getListApprovalFrame().stream().map(x -> ApproverFrameSendDto.fromApprovalFrame(x)).collect(Collectors.toList()));
	}
}
