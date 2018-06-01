package nts.uk.ctx.workflow.dom.service;

import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;

/**
 * 解除する
 * @author Doan Duy Hung
 *
 */
public interface ReleaseService {
	
	/**
	 * 解除する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 */
	public Boolean doRelease(String companyID, String rootStateID, String employeeID, Integer rootType);
	
	/**
	 * 1.解除できるかチェックする
	 * @param approvalPhaseState ドメインモデル「承認フェーズインスタンス」
	 * @param employeeID 社員ID
	 * @return
	 */
	public Boolean canReleaseCheck(ApprovalPhaseState approvalPhaseState, String employeeID);
	
}
