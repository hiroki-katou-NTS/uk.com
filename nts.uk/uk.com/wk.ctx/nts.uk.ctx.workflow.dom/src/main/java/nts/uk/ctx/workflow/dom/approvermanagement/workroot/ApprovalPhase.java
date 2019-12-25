package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 承認フェーズ
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApprovalPhase extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**分岐ID*/
	private String approvalId;
	/**順序*/
	private int phaseOrder;
	/**承認形態*/
	private ApprovalForm approvalForm;
	/**閲覧フェーズ*/
	private int browsingPhase;
	
	/**承認者*/
	private List<Approver>  approvers;
	
	public static ApprovalPhase createSimpleFromJavaType(String companyId,
			String approvalId,
			int phaseOrder,
			int approvalForm,
			int browsingPhase,
			List<Approver>  approvers){
		return new ApprovalPhase(companyId,
				approvalId,
				phaseOrder,
				EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
				browsingPhase,
				approvers);
	}
//	public void updateBranchId(String branchId){
//		this.branchId = branchId;
//	}
//	public void updateAppPhaseId(String approvalPhaseId){
//		this.approvalPhaseId = approvalPhaseId;
//	}
	
	public void addApproverList(List<Approver> approvers) {
		this.approvers = approvers;
	}

	public boolean containsConfirmer() {
		return this.approvers.stream().anyMatch(a -> a.isConfirmer());
	}
}
