package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.DetailApprovalFrameOutput;

@Value
@AllArgsConstructor
public class ApprovalFrameForRemandDto {
	public int phaseOrder;
	public String approvalReason;
	public List<DetailApproverDto> listApprover; 
	
	public static ApprovalFrameForRemandDto fromDomain(DetailApprovalFrameOutput frameOutput){
		return new ApprovalFrameForRemandDto(frameOutput.phaseOrder, frameOutput.approvalReason, 
				frameOutput.listApprover.stream().
					map(x -> {
							return new DetailApproverDto(x.getApproverID(), x.getApproverName(), 
									x.getRepresenterID(), x.getRepresenterName(), x.getJobtitle(),
									x.jobtitleAgent);
					}).collect(Collectors.toList()));
	}
}
