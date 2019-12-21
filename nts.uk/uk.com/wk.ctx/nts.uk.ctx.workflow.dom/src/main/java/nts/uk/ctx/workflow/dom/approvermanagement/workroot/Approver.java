package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
/**
 * 承認者
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class Approver extends DomainObject{
	/**会社ID*/
	private String companyId;
	/**分岐ID*/
	private String approvalId;
	/**承認フェーズID*/
	private int phaseOrder;
	/**承認者順序*/
	private int approverOrder;
	/**職位ID*/
	private String jobTitleId;
	/**社員ID*/
	private String employeeId;
	/**承認者指定区分*/
	private ApprovalAtr approvalAtr;
	/**確定者*/
	private ConfirmPerson confirmPerson;
	/**特定職場ID*/
	private String specWkpId;
	
	public static Approver createSimpleFromJavaType(String companyId, 
			String approvalId,
			int phaseOrder,
			int approverOrder,
			String jobTitleId,
			String employeeId,
			int approvalAtr,
			int confirmPerson,
			String specWkpId){
		return new Approver(companyId,
				approvalId,
				phaseOrder,
				approverOrder,
				jobTitleId,
				employeeId,
				EnumAdaptor.valueOf(approvalAtr, ApprovalAtr.class),
				EnumAdaptor.valueOf(confirmPerson, ConfirmPerson.class),
				specWkpId);
	}
//	public void updateApprovalPhaseId(String approvalPhaseId){
//		this.approvalPhaseId = approvalPhaseId;
//	}
//	public void updateApproverId(String approverId){
//		this.approverId = approverId;
//	}
	
	public boolean isConfirmer() {
		return this.confirmPerson == ConfirmPerson.CONFIRM;
	}
}
