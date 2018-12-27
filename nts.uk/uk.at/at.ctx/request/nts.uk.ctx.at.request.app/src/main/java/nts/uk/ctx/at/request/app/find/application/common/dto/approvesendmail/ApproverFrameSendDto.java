package nts.uk.ctx.at.request.app.find.application.common.dto.approvesendmail;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalFrameOutput;
@Value
@AllArgsConstructor
public class ApproverFrameSendDto {
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private List<ApproverStateSendDto> listApprover;
	
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approvalReason;
	
	public static ApproverFrameSendDto fromApprovalFrame(ApprovalFrameOutput approvalFrame){
		return new ApproverFrameSendDto(
				approvalFrame.getPhaseOrder(), 
				approvalFrame.getFrameOrder(), 
				approvalFrame.getApprovalAtr().value,
				approvalFrame.getApprovalAtr().name, 
				approvalFrame.getListApprover().stream()
					.map(x -> new ApproverStateSendDto(
							x.getApproverID(), 
							x.getApproverName(),
							x.getRepresenterID(),
							x.getRepresenterName(),
							x.getSMail(),
							x.getSMailAgent()))
					.collect(Collectors.toList()), 
				approvalFrame.getApproverID(),
				approvalFrame.getApproverName(),
				approvalFrame.getRepresenterID(),
				approvalFrame.getRepresenterName(),
				approvalFrame.getApprovalReason());
	}
}
