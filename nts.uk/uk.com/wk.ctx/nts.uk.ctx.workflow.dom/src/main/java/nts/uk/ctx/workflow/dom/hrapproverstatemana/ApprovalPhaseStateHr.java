package nts.uk.ctx.workflow.dom.hrapproverstatemana;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
/**
 * 人事承認フェーズインスタンス
 * @author hoatt
 *
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApprovalPhaseStateHr extends DomainObject {
	/**承認フェーズNo*/
	private int phaseOrder;
	/**承認区分*/
	private ApprovalBehaviorAtr approvalAtr;
	/**承認形態*/
	private ApprovalForm approvalForm;
	/**承認枠*/
	private List<ApprovalFrameHr> lstApprovalFrame;
	
	public static ApprovalPhaseStateHr convert(int phaseOrder, int approvalAtr, int approvalForm, List<ApprovalFrameHr> lstApprovalFrame){
		return new  ApprovalPhaseStateHr (phaseOrder,
				EnumAdaptor.valueOf(approvalAtr, ApprovalBehaviorAtr.class),
				EnumAdaptor.valueOf(approvalForm, ApprovalForm.class),
				lstApprovalFrame);
	}
}
