package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

public interface ChildCareLeaveRemInfoRepository {
	
	/**
	 * get all NursingCareLeaveRemainingInfo
	 * @return
	 */
	Optional<ChildCareLeaveRemainingInfo> getChildCareByEmpId(String empId);
	
	/**
	 * add ChildCareLeaveRemainingInfo object
	 * @param obj
	 */
	void add(ChildCareLeaveRemainingInfo obj, String cId);	
	
	/**
	 * update ChildCareLeaveRemainingInfo object
	 * @param obj
	 */
	void update(ChildCareLeaveRemainingInfo obj, String cId);
	
}
