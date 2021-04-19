package nts.uk.ctx.at.request.app.find.application.common.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApprovalRootOutput;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalRootDto {
	private List<ApprovalPhaseStateForAppDto> listApprovalPhaseStateDto;
	
	public static ApprovalRootDto fromDomain(ApprovalRootOutput approvalRootOutput) {
		return new ApprovalRootDto(approvalRootOutput.getListApprovalPhaseState()
				.stream().map(x -> ApprovalPhaseStateForAppDto.fromApprovalPhaseStateImport(x)).collect(Collectors.toList()));
	}
}
