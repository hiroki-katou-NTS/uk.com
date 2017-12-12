package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
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
	
	private Integer rootType;
	
	private Integer phaseOrder;
	
	private ApprovalAtr approvalAtr;
	
	private ApprovalForm approvalForm;
	
	private List<ApprovalFrame> listApprovalFrame;
	
}
