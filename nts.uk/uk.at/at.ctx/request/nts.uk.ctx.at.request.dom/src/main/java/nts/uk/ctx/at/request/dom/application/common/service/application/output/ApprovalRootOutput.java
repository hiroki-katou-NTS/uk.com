package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootContentImport_New;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
@Getter
public class ApprovalRootOutput {

	private List<ApprovalPhaseStateImport_New> listApprovalPhaseState;

	public static ApprovalRootOutput fromApprovalRootImportToOutput(ApprovalRootContentImport_New approvalRoot) {
		return new ApprovalRootOutput(
				approvalRoot.getApprovalRootState().getListApprovalPhaseState());
	}
}
