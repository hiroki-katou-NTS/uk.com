package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.Optional;

public interface ChildCareLeaveRemaiDataRepo {

	/**
	 * get all ChildCareLeaveRemainingData
	 * @return
	 */
	Optional<ChildCareLeaveRemainingData> getChildCareByEmpId(String empId);
	
	/**
	 * add ChildCareLeaveRemainingData 
	 * @param obj
	 */
	void add(ChildCareLeaveRemainingData obj, String cId);	
	
	/**
	 * update ChildCareLeaveRemainingData
	 * @param obj
	 */
	void update(ChildCareLeaveRemainingData obj, String cId);
	
}
