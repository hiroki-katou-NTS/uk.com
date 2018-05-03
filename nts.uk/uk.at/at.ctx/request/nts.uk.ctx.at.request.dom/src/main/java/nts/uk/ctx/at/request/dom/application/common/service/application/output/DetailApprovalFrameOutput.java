package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class DetailApprovalFrameOutput {
	public int phaseOrder;
	public String approvalReason;
	public List<DetailApproverOutput> listApprover; 
}
