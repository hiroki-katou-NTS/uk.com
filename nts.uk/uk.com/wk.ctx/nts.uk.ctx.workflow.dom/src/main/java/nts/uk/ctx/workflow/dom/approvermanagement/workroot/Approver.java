package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
/**
 * 承認者
 * @author hoatt
 *
 */
@Getter
@AllArgsConstructor
public class Approver {
	/**会社ID*/
	private String companyId;
	/**承認フェーズID*/
	private String approvalPhaseId;
	/**承認者ID*/
	private String approverId;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**順序*/
	private int orderNumber;
	/**区分*/
	private ApprovalAtr approvalAtr;
	/**確定者*/
	private int confirmPerson;
	
	public static Approver createSimpleFromJavaType(String companyId,
			String approvalPhaseId,
			String approverId,
			String jobTitleId,
			String employeeId,
			int orderNumber,
			int approvalAtr,
			int confirmPerson){
		return new Approver(companyId,
				approvalPhaseId,
				approverId,
				jobTitleId,
				employeeId,
				orderNumber,
				EnumAdaptor.valueOf(approvalAtr, ApprovalAtr.class),
				confirmPerson);
	}
}
