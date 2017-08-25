package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author hoatt
 *
 */
public interface WorkAppApprovalRootRepository {

	/**
	 * get all Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	List<PersonApprovalRoot> getAllPsApprovalRoot(String companyId, String employeeId);
	/**
	 * get Approval Branch
	 * @param companyId
	 * @param branchId
	 * @param number
	 * @return
	 */
	Optional<ApprovalBranch> getApprovalBranch(String companyId, String branchId, int number);
	/**
	 * get All Approval Phase by Code
	 * @param companyId
	 * @param branchId
	 * @return
	 */
	List<ApprovalPhase> getAllApprovalPhasebyCode(String companyId, String branchId);
	/**
	 * get All Company Approval Root
	 * @param companyId
	 * @return
	 */
	List<CompanyApprovalRoot> getAllComApprovalRoot(String companyId);
	/**
	 * get All Approver By Code
	 * @param companyId
	 * @param approvalPhaseId
	 * @return
	 */
	List<Approver> getAllApproverByCode(String companyId, String approvalPhaseId);
	/**
	 * delete Company Approval Root
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 */
	void deleteComApprovalRoot(String companyId, String approvalId, String historyId);
	/**
	 * delete All Approval Phase By Branch Id
	 * @param companyId
	 * @param branchId
	 */
	void deleteAllAppPhaseByBranchId(String companyId, String branchId);
	/**
	 * delete All Approver By Approval Phase Id
	 * @param companyId
	 * @param approvalPhaseId
	 */
	void deleteAllApproverByAppPhId(String companyId, String approvalPhaseId);
	/**
	 * delete Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @param historyId
	 */
	void deletePsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId);
	/**
	 * get All Workplace Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @return
	 */
	List<WorkplaceApprovalRoot> getAllWpApprovalRoot(String companyId, String workplaceId);
	/**
	 * delete Person Approval Root
	 * @param companyId
	 * @param workplaceId
	 * @param historyId
	 */
	void deleteWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId);
	/**
	 * add Company Approval Root
	 * @param comAppRoot
	 */
	void addComApprovalRoot(CompanyApprovalRoot comAppRoot);
	/**
	 * add Person Approval Root
	 * @param psAppRoot
	 */
	void addPsApprovalRoot(PersonApprovalRoot psAppRoot);
	/**
	 * add Workplace Approval Root
	 * @param wpAppRoot
	 */
	void addWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot);
	/**
	 * add All Approval Phase
	 * @param lstAppPhase
	 */
	void addAllApprovalPhase(List<ApprovalPhase> lstAppPhase);
	/**
	 * add All Approver
	 * @param lstApprover
	 */
	void addAllApprover(List<Approver> lstApprover);
	/**
	 * update Company Approval Root
	 * @param comAppRoot
	 */
	void updateComApprovalRoot(CompanyApprovalRoot comAppRoot);
	/**
	 * update Person Approval Root
	 * @param psAppRoot
	 */
	void updatePsApprovalRoot(PersonApprovalRoot psAppRoot);
	/**
	 * update Workplace Approval Root
	 * @param wpAppRoot
	 */
	void updateWpApprovalRoot(WorkplaceApprovalRoot wpAppRoot);
	/**
	 * get Company Approval Root By End date
	 * @param companyId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<CompanyApprovalRoot> getComApprovalRootByEdate(String companyId, GeneralDate endDate, Integer applicationType);
	/**
	 * get Workplace Approval Root By End date
	 * @param companyId
	 * @param workplaceId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<WorkplaceApprovalRoot> getWpApprovalRootByEdate(String companyId, String workplaceId, GeneralDate endDate, Integer applicationType);
	/**
	 * get Person Approval Root By End date
	 * @param companyId
	 * @param employeeId
	 * @param endDate
	 * @param applicationType
	 * @return
	 */
	List<PersonApprovalRoot> getPsApprovalRootByEdate(String companyId, String employeeId, GeneralDate endDate, Integer applicationType);
	/**
	 * get ComApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param historyId
	 * @return
	 */
	Optional<CompanyApprovalRoot> getComApprovalRoot(String companyId, String approvalId, String historyId);
	/**
	 * get WpApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param workplaceId
	 * @param historyId
	 * @return
	 */
	Optional<WorkplaceApprovalRoot> getWpApprovalRoot(String companyId, String approvalId, String workplaceId, String historyId);
	/**
	 * get PsApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param employeeId
	 * @param historyId
	 * @return
	 */
	Optional<PersonApprovalRoot> getPsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId);
}
