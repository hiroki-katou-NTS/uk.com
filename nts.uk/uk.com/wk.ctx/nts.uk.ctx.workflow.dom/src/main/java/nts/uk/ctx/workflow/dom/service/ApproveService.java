package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.service.output.ApprovalRepresenterInforOutput;

/**
 * 承認する
 * @author Doan Duy Hung
 *
 */
public interface ApproveService {
	
	/**
	 * 承認する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return 承認フェーズ枠番
	 */
	public Integer doApprove(String companyID, String rootStateID, String employeeID, 
			Boolean isCreate, ApplicationType appType, GeneralDate appDate, String memo, Integer rootType);
	
	/**
	 * 1.指定する承認フェーズの承認が完了したか
	 * @param approvalPhaseState ドメインモデル「承認フェーズインスタンス」
	 * @return 承認完了フラグ(true, false)
　				true：指定する承認フェーズの承認が完了
　				false：指定する承認フェーズの承認がまだ未完了
	 */
	public Boolean isApproveApprovalPhaseStateComplete(String companyID, ApprovalPhaseState approvalPhaseState);
	
	/**
	 * 2.承認全体が完了したか
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @return
	 */
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, 
			Boolean isCreate, ApplicationType appType, GeneralDate appDate, Integer rootType);
	
	/**
	 * 3.指定する承認フェーズに未承認の承認者一覧を取得する
	 * @param approvalPhaseState ドメインモデル「承認フェーズインスタンス」
	 * @return 未承認の承認者一覧
	 */
	public List<String> getUnapproveApproverFromPhase(ApprovalPhaseState approvalPhaseState); 
	
	/**
	 * 4.次の承認の番の承認者を取得する(メール通知用)
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param approvalPhaseStateNumber ドメインモデル「承認フェーズインスタンス」・順序
	 * @return
	 */
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID, Integer approvalPhaseStateNumber, 
			Boolean isCreate, String employeeID, ApplicationType appType, GeneralDate appDate, Integer rootType);
	
	/**
	 * 1.送信先の判断処理
	 * @param listApprovalRepresenterInforOutput
	 * @return
	 */
	public List<String> judgmentDestination(List<ApprovalRepresenterInforOutput> listApprovalRepresenterInforOutput);
	
}
