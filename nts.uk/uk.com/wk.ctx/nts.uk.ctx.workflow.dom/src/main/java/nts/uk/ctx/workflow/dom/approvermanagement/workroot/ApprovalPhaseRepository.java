package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

/**
 * 承認フェーズRepository
 */
public interface ApprovalPhaseRepository {

	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	List<ApprovalPhase> getAllApprovalPhasebyCode(String approvalId);
	/**
	 * get Approval Phase by Code
	 * @param companyId
	 * @param approvalId
	 * @param phaseOrder
	 * @return
	 */
	Optional<ApprovalPhase> getApprovalPhase(String approvalId, int phaseOrder);
	/**
	 * get All Approval Phase by Code include approvers
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	List<ApprovalPhase> getAllIncludeApprovers(String approvalId);
	
	/**
	 * add All Approval Phase
	 * @param lstAppPhase
	 */
	void addAllApprovalPhase(List<ApprovalPhase> lstAppPhase);
	/**
	 * add Approval Phase
	 * @param appPhase
	 */
	void addApprovalPhase(ApprovalPhase appPhase);
	/**
	 * update Approval Phase
	 * @param appPhase
	 */
	void updateApprovalPhase(ApprovalPhase appPhase);
	/**
	 * delete All Approval Phase By approvalId
	 * @param companyId
	 * @param approvalId
	 */
	void deleteAllAppPhaseByApprovalId(String approvalId);
	/**
	 * delete Approval Phase By phaseOrder
	 * @param companyId
	 * @param approvalId
	 * @param phaseOrder
	 */
	void deleteAppPhaseByAppPhId(String approvalId, int phaseOrder);
	/**
	 * Get approval first phase by approvalId
	 * @param companyId
	 * @param approvalId
	 * @return
	 */
	Optional<ApprovalPhase> getApprovalFirstPhase(String approvalId);
	
	/**
	 * [1] 承認IDListから承認フェーズ取得する
	 * @param cid 会社ID	
	 * @param approvalIds 承認IDList
	 * return 承認フェーズ	
	 */
	public List<ApprovalPhase> getFromApprovalIds(String cid, List<String> approvalIds);
	
	/**
	 * [2]承認IDListの承認フェーズを削除する
	 * @param approvalIds 承認IDList
	 */
	public void deleteByApprovalIds(List<String> approvalIds);
	
	/**
	 * [3]InsertAll(List<承認フェーズ>）　※WWFMT_APPROVAL_PHASE　&　WWFMT_APPROVER
	 * @param approvalPhases List<承認フェーズ>
	 */
	public void insertAll(List<ApprovalPhase> approvalPhases);
}
