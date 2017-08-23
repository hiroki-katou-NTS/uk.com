package nts.uk.ctx.at.request.dom.application.common.approvalframe;

import java.util.List;
import java.util.Optional;

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
	 * @param companyID
	 * @param phaseID
	 * @param dispOrder
	 * @return
	 */
	Optional<ApprovalFrame> findByCode(String companyID , String phaseID , int dispOrder);
	
	/**
	 * Create ApprovalFrame
	 * @param approvalFrame
	 */
	void create (ApprovalFrame approvalFrame); 
	
	/**
	 * Update ApprovalFrame
	 * @param approvalFrame
	 */
	
	void update (ApprovalFrame approvalFrame); 
	
	/**
	 * Delete ApprovalFrame
	 * @param companyID
	 * @param appID
	 * @param phaseID
	 */
	void delete (String companyID , String phaseID , int dispOrder );

}
