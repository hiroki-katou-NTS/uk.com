package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ApprovalFrameForRemandDto {
	public int phaseOrder;
	public String approvalReason;
	public List<DetailApproverDto> listApprover; 
}
