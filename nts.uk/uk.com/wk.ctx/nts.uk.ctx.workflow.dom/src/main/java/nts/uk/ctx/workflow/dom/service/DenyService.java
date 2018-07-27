package nts.uk.ctx.workflow.dom.service;

import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;

/**
 * 否認する
 * @author Doan Duy Hung
 *
 */
public interface DenyService {
	
	/**
	 * 否認する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return 否認を実行したかフラグ(true, false)
				true：否認を実行した
				false：否認を実行しなかった
	 */
	public Boolean doDeny(String companyID, String rootStateID, String employeeID, String memo, Integer rootType);
	
	/**
	 * 1.否認できるかチェックする
	 * @param approvalRootState ドメインモデル「承認ルートインスタンス」
	 * @param order 開始順序
	 * @param employeeID 社員ID
	 * @return
	 */
	public Boolean canDenyCheck(ApprovalRootState approvalRootState, Integer order, String employeeID);
	
}
