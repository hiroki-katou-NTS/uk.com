package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
/**
 * 
 * @author hoatt
 *
 */
public interface PersonApprovalRootRepository {

	/**
	 * get all Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @return
	 */
	List<PersonApprovalRoot> getAllPsApprovalRoot(String companyId, String employeeId);
	/**
	 * delete Person Approval Root
	 * @param companyId
	 * @param employeeId
	 * @param historyId
	 */
	void deletePsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId);
	/**
	 * add Person Approval Root
	 * @param psAppRoot
	 */
	void addPsApprovalRoot(PersonApprovalRoot psAppRoot);
	/**
	 * update Person Approval Root
	 * @param psAppRoot
	 */
	void updatePsApprovalRoot(PersonApprovalRoot psAppRoot);
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
	 * get PsApprovalRoot
	 * @param companyId
	 * @param approvalId
	 * @param employeeId
	 * @param historyId
	 * @return
	 */
	Optional<PersonApprovalRoot> getPsApprovalRoot(String companyId, String approvalId, String employeeId, String historyId);
}
