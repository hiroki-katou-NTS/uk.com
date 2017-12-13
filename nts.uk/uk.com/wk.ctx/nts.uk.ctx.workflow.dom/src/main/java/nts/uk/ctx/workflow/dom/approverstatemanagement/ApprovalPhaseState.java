package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * 承認フェーズインスタンス
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalPhaseState {
	
	private String companyID;
	
	private String rootStateID;
	
	private RootType rootType;
	
	private Integer phaseOrder;
	
	@Setter
	private ApprovalBehaviorAtr approvalAtr;
	
	private ApprovalForm approvalForm;
	
	private List<ApprovalFrame> listApprovalFrame;
	
}
