package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;
import java.util.Optional;

public interface LeaveForCareDataRepo {
	
	/**
	 * get all CareForLeaveRemainingData
	 * @return
	 */
	Optional<LeaveForCareData> getCareByEmpId(String empId);
	
	/**
	 * @author lanlt
	 * @param cid
	 * @param empIds
	 * @return
	 */
	List<LeaveForCareData> getCareByEmpIds(String cid, List<String> empIds);
	
	/**
	 * add LeaveForCareData object
	 * @param obj
	 */
	void add(LeaveForCareData obj, String cId);	
	
	/**
	 * @author lanlt
	 * add LeaveForCareData object
	 * @param obj
	 */
	void addAll(String cid, List<LeaveForCareData> domains);	
	/**
	 * update LeaveForCareData object
	 * @param obj
	 */
	void update(LeaveForCareData obj, String cId);
	
	/**
	 * @author lanlt
	 * @param cid
	 * @param domains
	 */
	void updateAll(String cid, List<LeaveForCareData> domains);	
	
}
