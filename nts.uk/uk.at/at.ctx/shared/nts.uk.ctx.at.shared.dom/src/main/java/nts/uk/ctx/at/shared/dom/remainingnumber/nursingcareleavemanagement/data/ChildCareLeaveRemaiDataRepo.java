package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data;

import java.util.List;
import java.util.Optional;

public interface ChildCareLeaveRemaiDataRepo {

//	/** 介護対象 */
//	private boolean careManagement;

	/**
	 * get all ChildCareLeaveRemainingData
	 * @return
	 */
	Optional<ChildCareLeaveRemainingData> getChildCareByEmpId(String empId);

	List<ChildCareLeaveRemainingData> getChildCareByEmpIds(String cid, List<String> empIds);

	/**
	 * add ChildCareLeaveRemainingData
	 * @param obj
	 */
	void add(ChildCareLeaveRemainingData obj, String cId);

	/**
	 * @author lanlt
	 * add ChildCareLeaveRemainingData
	 * @param obj
	 */
	void addAll(String cid, List<ChildCareLeaveRemainingData> domains);

	/**
	 * update ChildCareLeaveRemainingData
	 * @param obj
	 */
	void update(ChildCareLeaveRemainingData obj, String cId);

	/**
	 * @author lanlt
	 * @param cid
	 * @param domains
	 */
	void updateAll(String cid, List<ChildCareLeaveRemainingData> domains);

}
