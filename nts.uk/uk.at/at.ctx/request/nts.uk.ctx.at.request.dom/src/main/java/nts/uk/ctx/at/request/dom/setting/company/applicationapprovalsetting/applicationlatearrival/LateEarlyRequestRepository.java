package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival;

import java.util.Optional;

/**
 * TanLV
 *
 */
public interface LateEarlyRequestRepository {
	/**
	 * Find
	 * @return
	 */
	Optional<LateEarlyRequest> getLateEarlyRequest();
	
	/**
	 * Add
	 * @param withDrawalReqSet
	 */
	void addLateEarlyRequest(LateEarlyRequest lateEarlyRequest);
	
	/**
	 * Update
	 * @param withDrawalReqSet
	 */
	void updateLateEarlyRequest(LateEarlyRequest lateEarlyRequest);
}
