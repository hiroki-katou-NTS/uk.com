package nts.uk.ctx.workflow.pub.service;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.pub.agent.AgentPubExport;
import nts.uk.ctx.workflow.pub.service.export.ApprovalRootContentExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverApprovedExport;
import nts.uk.ctx.workflow.pub.service.export.ApproverPersonExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface ApprovalRootStatePub {
	
	public ApprovalRootContentExport getApprovalRoot(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID, Boolean isCreate);
	
	public void insertAppRootType(String companyID, String employeeID, Integer appTypeValue, GeneralDate date, String appID);
	
	/**
	 * 4.次の承認の番の承認者を取得する(メール通知用)
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param approvalPhaseStateNumber ドメインモデル「承認フェーズインスタンス」・順序
	 * @return
	 */
	public List<String> getNextApprovalPhaseStateMailList(String companyID, String rootStateID,
			Integer approvalPhaseStateNumber, Boolean isCreate, String employeeID, Integer appTypeValue, GeneralDate appDate);
	
	/**
	 * 承認する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return 承認フェーズ枠番
	 */
	public Integer doApprove(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate, String memo);
	
	/**
	 * 2.承認全体が完了したか
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @return
	 */
	public Boolean isApproveAllComplete(String companyID, String rootStateID, String employeeID, Boolean isCreate, Integer appTypeValue, GeneralDate appDate);
	
	/**
	 * 一括解除する 
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 */
	public void doReleaseAllAtOnce(String companyID, String rootStateID);
	
	/**
	 * 1.承認を行った承認者を取得する
	 * @param rootStateID
	 * @return
	 */
	public ApproverApprovedExport getApproverApproved(String rootStateID); 
	
	/**
	 * 承認代行情報の取得処理
	 * @param companyID 会社ID
	 * @param listApprover 承認者リスト
　							※[承認者の社員ID]の一覧
	 * @return
	 */
	public AgentPubExport getApprovalAgentInfor(String companyID, List<String> listApprover);
	
	/**
	 * 削除時のメール通知者を取得する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @return
	 */
	public List<String> getMailNotifierList(String companyID, String rootStateID);
	
	public void deleteApprovalRootState(String rootStateID);
	
	/**
	 * 解除する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 */
	public Boolean doRelease(String companyID, String rootStateID, String employeeID);
	
	/**
	 * 否認する
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return 否認を実行したかフラグ(true, false)
				true：否認を実行した
				false：否認を実行しなかった
	 */
	public Boolean doDeny(String companyID, String rootStateID, String employeeID, String memo);
	
	/**
	 * 1.指定した社員が承認者であるかの判断
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return 承認者フラグ(true、false)
	 			true：承認者である
	 			false：承認者でない
	 */
	public Boolean judgmentTargetPersonIsApprover(String companyID, String rootStateID, String employeeID);
	
	/**
	 * 3.指定した社員が承認できるかの判断
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return
	 */
	public ApproverPersonExport judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID);
	
	/**
	 * 差し戻しする(承認者まで)
	 * @param companyID
	 * @param rootStateID
	 * @param order
	 */
	public List<String> doRemandForApprover(String companyID, String rootStateID, Integer order);
	
	/**
	 * 差し戻しする(本人まで)
	 * @param companyID
	 * @param rootStateID
	 */
	public void doRemandForApplicant(String companyID, String rootStateID);
	
}
