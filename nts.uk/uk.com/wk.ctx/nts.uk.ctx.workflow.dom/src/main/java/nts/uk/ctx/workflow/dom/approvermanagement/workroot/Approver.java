package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;

@Getter
@AllArgsConstructor
public class Approver {
	/*会社ID*/
	private String companyId;
	/*承認フェーズID*/
	private int approvalPhaseId;
	/*職位ID*/
	private String jobTitleId;
	/*社員ID*/
	private String employeeId;
	/*順序*/
	private int orderNumber;
	/*区分*/
	private ApprovalAtr approvalAtr;
	/*確定者*/
	private int confirmPerson;
	
	public static Approver createSimpleFromJavaType(String companyId,
			int approvalPhaseId,
			String jobTitleId,
			String employeeId,
			int orderNumber,
			int approvalAtr,
			int confirmPerson){
		return new Approver(companyId,
				approvalPhaseId,
				jobTitleId,
				employeeId,
				orderNumber,
				EnumAdaptor.valueOf(approvalAtr, ApprovalAtr.class),
				confirmPerson);
	}
}
