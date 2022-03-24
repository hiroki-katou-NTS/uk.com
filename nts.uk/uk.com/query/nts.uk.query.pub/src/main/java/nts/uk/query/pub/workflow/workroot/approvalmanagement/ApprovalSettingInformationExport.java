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
	
	public Integer getEmploymentRootAtr() {
		return this.personApprovalRoot.getApprRoot().getEmploymentRootAtr();
	}
	
	public Integer getApplicationType() {
		return this.personApprovalRoot.getApprRoot().getApplicationType();
	}
	
	public Integer getConfirmationRootType() {
		return this.personApprovalRoot.getApprRoot().getConfirmationRootType();
	}
}
