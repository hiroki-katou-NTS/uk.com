package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

public interface LeaveForCareInfoRepository {

	/**
	 * get all NursingCareLeaveRemainingInfo
	 * 
	 * @return
	 */
	Optional<LeaveForCareInfo> getCareByEmpId(String empId);

	/**
	 * add LeaveForCareInfo object
	 * 
	 * @param obj
	 */
	void add(LeaveForCareInfo obj, String cId);

	/**
	 * update LeaveForCareInfo object
	 * 
	 * @param obj
	 */
	void update(LeaveForCareInfo obj, String cId);

}
