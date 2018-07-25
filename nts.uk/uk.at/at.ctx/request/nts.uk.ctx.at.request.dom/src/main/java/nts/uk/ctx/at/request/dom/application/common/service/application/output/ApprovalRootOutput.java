package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
@Getter
public class ApprovalRootOutput {

	private List<ApprovalPhaseStateOutput> listApprovalPhaseState;

	public static ApprovalRootOutput fromApprovalRootImportToOutput(ApprovalRootContentImport_New approvalRoot) {
		return new ApprovalRootOutput(
				approvalRoot.getApprovalRootState().getListApprovalPhaseState().stream().map(x -> {
					return ApprovalPhaseStateOutput.fromRootStateImportToOutput(x);
				}).collect(Collectors.toList()));
	}
}
