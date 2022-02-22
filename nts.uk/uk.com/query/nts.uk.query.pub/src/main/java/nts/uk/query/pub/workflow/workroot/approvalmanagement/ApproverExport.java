package nts.uk.query.pub.workflow.workroot.approvalmanagement;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproverExport {

	/**承認者Gコード*/
	private String jobGCD;
	/**社員ID*/
	private String employeeId;
	/**順序*/
	private int approverOrder;
	/**確定者*/
	private int confirmPerson;
	/**特定職場ID*/
	private String specWkpId;
}
