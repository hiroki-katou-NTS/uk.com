package nts.uk.ctx.at.request.dom.application.common.approvalframe;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.common.approveaccepted.ApproveAccepted;

/**
 * 
 * @author hieult
 *
 */

public interface ApprovalFrameRepository {
		
	/**
	 * get all approver by phase ID
	 * @param companyID
	 * @param phaseID
	 * @return
	 */
	List<ApprovalFrame> getAllApproverByPhaseID(String companyID , String phaseID);
	/**
	 * Find by Code
	 * 
	 * @param companyID
	 * @param phaseID
	 * @param dispOrder
	 * @return
	 */
	Optional<ApprovalFrame> findByCode(String companyID, String phaseID, String dispOrder);

	/**
	 * Find list Frame by Phase ID
	 * 
	 * @param companyID
	 * @param phaseID
	 * @param dispOrder
	 * @return
	 */
	Optional<ApprovalFrame> findByCode(String companyID , String phaseID , int dispOrder,String approverSID);

	List<ApprovalFrame> findByPhaseID(String companyID, String phaseID);
	/**
	 * Create ApprovalFrame
	 * 
	 * @param approvalFrame
	 */
	void create(ApprovalFrame approvalFrame, String phaseID);

	/**
	 * Update ApprovalFrame
	 * 
	 * @param approvalFrame
	 */

	void update(ApprovalFrame approvalFrame, String phaseID);

	/**
	 * Delete ApprovalFrame
	 * 
	 * @param companyID
	 * @param appID
	 * @param phaseID
	 */
	void delete (ApprovalFrame approvalFrame);
	/**
	 * get list frame by list phase
	 * @param listPhase
	 * @return
	 */
	List<ApprovalFrame> getListFrameByListPhase(String companyID,List<String> listPhaseID);

}
