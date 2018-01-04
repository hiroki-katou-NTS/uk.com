package nts.uk.ctx.workflow.dom.service;

import java.util.List;

import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalPhaseState;
import nts.uk.ctx.workflow.dom.service.output.ApprovalStatusOutput;
import nts.uk.ctx.workflow.dom.service.output.ApproverPersonOutput;

/**
 * 承認ステータスの判断
 * @author Doan Duy Hung
 *
 */
public interface JudgmentApprovalStatusService {
	
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
	 * 2.承認ステータスの判断
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @return ステータス：（否認、承認済、未承認、差し戻し）
	 */
	public ApprovalBehaviorAtr determineApprovalStatus(String companyID, String rootStateID);
	
	/**
	 * 3.指定した社員が承認できるかの判断
	 * @param companyID 会社ID
	 * @param rootStateID インスタンスID
	 * @param employeeID 社員ID
	 * @return
	 */
	public ApproverPersonOutput judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID);
	
	/**
	 * 1.承認フェーズ毎の承認者を取得する
	 * lấy người xác nhận trong Phase
	 * @param approvalPhaseState Approval Phase
	 * @return list Approver
	 */
	public List<String> getApproverFromPhase(ApprovalPhaseState approvalPhaseState);
	
	/**
	 * 1.承認状況の判断
	 * @param approvalPhaseState
	 * @param employeeID
	 */
	public ApprovalStatusOutput judmentApprovalStatus(String companyID, ApprovalPhaseState approvalPhaseState, String employeeID);
	
	/**
	 * 2.指定した社員が指定した承認者リストの代行承認者かの判断
	 * @param companyID
	 * @param employeeID
	 * @param listApprover
	 * @return
	 */
	public Boolean judgmentAgentListByEmployee(String companyID, String employeeID, List<String> listApprover);
	
	
}
