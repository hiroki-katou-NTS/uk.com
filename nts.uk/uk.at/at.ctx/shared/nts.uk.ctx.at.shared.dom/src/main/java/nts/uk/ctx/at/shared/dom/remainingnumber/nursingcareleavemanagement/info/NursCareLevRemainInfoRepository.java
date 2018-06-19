package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

public interface NursCareLevRemainInfoRepository {
	/**
	 * get all NursingCareLeaveRemainingInfo
	 * @return
	 */
	Optional<NursingCareLeaveRemainingInfo> getChildCareByEmpId(String empId);
	
	/**
	 * get all NursingCareLeaveRemainingInfo
	 * @return
	 */
	Optional<NursingCareLeaveRemainingInfo> getCareByEmpId(String empId);
	
	/**
	 * add ChildCareLeaveRemainingInfo or LeaveForCareInfo object
	 * @param obj
	 */
	void add(NursingCareLeaveRemainingInfo obj, String cId);	
	
	/**
	 * update ChildCareLeaveRemainingInfo or LeaveForCareInfo object
	 * @param obj
	 */
	void update(NursingCareLeaveRemainingInfo obj, String cId);
	
	/**
	 * remove ChildCareLeaveRemainingInfo or LeaveForCareInfo object
	 * @param obj
	 */
	void remove(NursingCareLeaveRemainingInfo obj, String cId);
}
