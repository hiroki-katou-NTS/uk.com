package nts.uk.ctx.at.request.app.find.application.common.dto.approvesendmail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalFrameOutput;
@Value
@AllArgsConstructor
public class ApproverFrameSendDto {
	
	private Integer frameOrder;
	
	private List<ApproverStateSendDto> listApprover;
	
	public static ApproverFrameSendDto fromApprovalFrame(ApprovalFrameOutput approvalFrame){
		return new ApproverFrameSendDto(
				approvalFrame.getFrameOrder(), 
				approvalFrame.getListApprover().stream()
					.map(x -> new ApproverStateSendDto(
							x.getApproverID(), 
							x.getApprovalAtr().value,
							x.getApprovalAtr().name, 
							x.getAgentID(),
							x.getApproverName(),
							x.getRepresenterID(),
							x.getRepresenterName(),
							x.getApprovalReason(),
							x.getSMail(),
							x.getSMailAgent()))
					.collect(Collectors.toList())
				);
	}
}
