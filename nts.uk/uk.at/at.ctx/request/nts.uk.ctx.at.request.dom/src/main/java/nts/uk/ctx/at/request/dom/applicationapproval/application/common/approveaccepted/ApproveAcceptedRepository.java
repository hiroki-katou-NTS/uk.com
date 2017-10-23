package nts.uk.ctx.at.request.dom.applicationapproval.application.common.approveaccepted;

import java.util.List;
import java.util.Optional;

public interface ApproveAcceptedRepository {
	/**
	 * get list approver accepted
	 * @param companyID
	 * @param phaseID
	 * @return
	 */
	List<ApproveAccepted> getAllApproverAccepted(String companyID , String frameID);
	/**
	 * get approver accepted by code
	 * @param companyID
	 * @param phaseID
	 * @param dispOrder
	 * @return
	 */
	Optional<ApproveAccepted> getApproverAcceptedByCode(String companyID , String phaseID , int dispOrder,String approverSID);
	
	/**
	 * create approver accepted
	 * @param approveAccepted
	 */
	void createApproverAccepted(ApproveAccepted approveAccepted, String frameID);
	
	void updateApproverAccepted(ApproveAccepted approveAccepted, String frameID);

	/**
	 * delete approver accepted by code
	 * @param companyID
	 * @param phaseID
	 * @param dispOrder
	 */
	void deleteApproverAccepted(ApproveAccepted approveAccepted);

}
