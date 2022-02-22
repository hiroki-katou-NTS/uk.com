package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApprovalSettingInformationExport {

	/** 承認フェーズ */
	private List<ApprovalPhaseExport> approvalPhases;

	/** 承認ルート */
	private PersonApprovalRootExport personApprovalRoot;
}
