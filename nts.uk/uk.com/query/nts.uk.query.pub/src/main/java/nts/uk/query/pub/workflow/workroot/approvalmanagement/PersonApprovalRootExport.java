package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonApprovalRootExport {

	/** 会社ID */
	private String companyId;

	/** 承認ID */
	private String approvalId;

	/** 社員ID */
	private String employeeId;

	/** 承認ルート */
	public ApprovalRootExport apprRoot;

	/** 運用モード */
	private int operationMode;
}
