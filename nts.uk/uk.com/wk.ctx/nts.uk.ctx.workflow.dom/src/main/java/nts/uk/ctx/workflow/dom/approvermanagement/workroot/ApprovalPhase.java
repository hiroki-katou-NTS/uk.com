package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.Arrays;
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
	/**承認ID*/
	private String approvalId;
	/**順序*/
	private int phaseOrder;
	/**承認形態*/
	private ApprovalForm approvalForm;
	/**閲覧フェーズ*/
	private int browsingPhase;
	/**承認者指定区分*/
	private ApprovalAtr approvalAtr;
	/**承認者*/
	private List<Approver>  approvers;
	
	public static ApprovalPhase createSimpleFromJavaType(
			String approvalId,
			int phaseOrder,
			int approvalForm,
			int browsingPhase,
			int approvalAtr,
			List<Approver>  approvers){
		return new ApprovalPhase(
				approvalId,
				phaseOrder,
				EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
				browsingPhase,
				EnumAdaptor.valueOf(approvalAtr, ApprovalAtr.class),
				approvers);
	}
	
	/**
	 * [C-1] 一承認者で作成する
	 * @param approvalId 承認ID
	 * @param phaseOrder フェーズの順序
	 * @param approverId 承認者ID
	 * @return
	 */
	public static ApprovalPhase createSimpleFromJavaType(
			String approvalId,
			int phaseOrder,
			String approverId) {
		List<Approver> approvers = Arrays.asList(Approver.createSimpleFromJavaType(approverId));
		return new ApprovalPhase(approvalId,
				phaseOrder,
				ApprovalForm.EVERYONE_APPROVED,
				0,
				ApprovalAtr.PERSON,
				approvers);
	}
	
	public void addApproverList(List<Approver> approvers) {
		this.approvers = approvers;
	}

	public boolean containsConfirmer() {
		return this.approvers.stream().anyMatch(a -> a.isConfirmer());
	}
}
