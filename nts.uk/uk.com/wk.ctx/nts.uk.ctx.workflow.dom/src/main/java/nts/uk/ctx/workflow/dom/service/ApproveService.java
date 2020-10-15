package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterInforOutput;

/**
 * 承認する
 * @author Doan Duy Hung
 *
 */
public interface ApproveService {
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.Export.就業.2.承認する(ApproveService).2.承認する(ApproveService)
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @param memo 承認コメン
	 * @return 承認フェーズ枠番
	 */
	public Integer doApprove(String rootStateID, String employeeID, String memo);
	
	/**
	 * 1.指定する承認フェーズの承認が完了したか
	 * @param approvalPhaseState ドメインモデル「承認フェーズインスタンス」
	 * @return 承認完了フラグ(true, false)
　				true：指定する承認フェーズの承認が完了
　				false：指定する承認フェーズの承認がまだ未完了
	 */
	public Boolean isApproveApprovalPhaseStateComplete(String companyID, ApprovalPhaseState approvalPhaseState);
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.Export.OLD＿承認する(ApproveService).2.承認全体が完了したか(isApproveAllComplete).2.承認全体が完了したか.2.承認全体が完了したか
	 * @param rootStateID インスタンスID
	 * @return
	 */
	public Boolean isApproveAllComplete(String rootStateID);
	
	/**
	 * 3.指定する承認フェーズに未承認の承認者一覧を取得する
	 * @param approvalPhaseState ドメインモデル「承認フェーズインスタンス」
	 * @return 未承認の承認者一覧
	 */
	public List<String> getUnapproveApproverFromPhase(ApprovalPhaseState approvalPhaseState); 
	
	/**
	 * refactor 4
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.Export.OLD＿承認する(ApproveService).4.次の承認の番の承認者を取得する(メール通知用)(getNextApprovalPhaseStateMailList).4.次の承認の番の承認者を取得する(メール通知用)
	 * @param rootStateID インスタンスID
	 * @param approvalPhaseStateNumber ドメインモデル「承認フェーズインスタンス」・順序
	 * @return
	 */
	public List<String> getNextApprovalPhaseStateMailList(String rootStateID, Integer approvalPhaseStateNumber);
	
	/**
	 * 1.送信先の判断処理
	 * @param listApprovalRepresenterInforOutput
	 * @return
	 */
	public List<String> judgmentDestination(List<ApprovalRepresenterInforOutput> listApprovalRepresenterInforOutput);
	
}
