package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.Optional;

public interface LeaveForCareDataRepo {
	
	/**
	 * get all CareForLeaveRemainingData
	 * @return
	 */
	Optional<LeaveForCareData> getCareByEmpId(String empId);
	
	/**
	 * add LeaveForCareData object
	 * @param obj
	 */
	void add(LeaveForCareData obj, String cId);	
	
	/**
	 * update LeaveForCareData object
	 * @param obj
	 */
	void update(LeaveForCareData obj, String cId);
	
}
