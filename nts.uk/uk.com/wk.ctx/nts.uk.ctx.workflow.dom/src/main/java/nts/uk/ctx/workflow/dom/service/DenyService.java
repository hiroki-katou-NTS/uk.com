package nts.uk.ctx.workflow.dom.service;

import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;

/**
 * 否認する
 * @author Doan Duy Hung
 *
 */
public interface DenyService {
	
	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.Export.就業.3.否認する(DenyService).3.否認する(DenyService)
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @param memo 承認コメント
	 * @return 否認を実行したかフラグ(true, false)
				true：否認を実行した
				false：否認を実行しなかった
	 */
	public Boolean doDeny(String rootStateID, String employeeID, String memo);
	
	/**
	 * 1.否認できるかチェックする
	 * @param approvalRootState ドメインモデル「承認ルートインスタンス」
	 * @param order 開始順序
	 * @param employeeID 社員ID
	 * @return
	 */
	public Boolean canDenyCheck(ApprovalRootState approvalRootState, Integer order, String employeeID);
	
}
