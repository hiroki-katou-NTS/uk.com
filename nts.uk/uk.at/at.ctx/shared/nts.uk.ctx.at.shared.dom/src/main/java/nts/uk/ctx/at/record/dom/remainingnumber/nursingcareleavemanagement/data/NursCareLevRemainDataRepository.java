package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.Optional;

public interface NursCareLevRemainDataRepository {
	
	/**
	 * get all ChildCareLeaveRemainingData
	 * @return
	 */
	Optional<NursingCareLeaveRemainingData> getChildCareByEmpId(String empId);
	
	/**
	 * get all CareForLeaveRemainingData
	 * @return
	 */
	Optional<NursingCareLeaveRemainingData> getCareByEmpId(String empId);
	
	/**
	 * add ChildCareLeaveRemainingData or LeaveForCareData object
	 * @param obj
	 */
	void add(NursingCareLeaveRemainingData obj, String cId);	
	
	/**
	 * update ChildCareLeaveRemainingData or LeaveForCareData object
	 * @param obj
	 */
	void update(NursingCareLeaveRemainingData obj, String cId);
	
	/**
	 * remove ChildCareLeaveRemainingData or LeaveForCareData object
	 * @param obj
	 */
	void remove(NursingCareLeaveRemainingData obj, String cId);
}
