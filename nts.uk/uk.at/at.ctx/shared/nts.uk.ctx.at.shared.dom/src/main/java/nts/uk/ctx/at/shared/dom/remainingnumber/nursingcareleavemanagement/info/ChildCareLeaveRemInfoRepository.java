package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

public interface ChildCareLeaveRemInfoRepository {

	/**
	 * get all NursingCareLeaveRemainingInfo
	 * @return
	 */
	Optional<ChildCareLeaveRemainingInfo> getChildCareByEmpId(String empId);

	/**
	 * @author lanlt
	 * @param cid
	 * @param empIds
	 * @return
	 */
	List<ChildCareLeaveRemainingInfo> getChildCareByEmpIdsAndCid(String cid, List<String> empIds);

	/**
	 * add ChildCareLeaveRemainingInfo object
	 * @param obj
	 */
	void add(ChildCareLeaveRemainingInfo obj, String cId);

	/**
	 * @author lanlt
	 * @param cid
	 * @param domains
	 */
	void addAll(String cid, List<ChildCareLeaveRemainingInfo> domains);

	/**
	 * update ChildCareLeaveRemainingInfo object
	 * @param obj
	 */
	void update(ChildCareLeaveRemainingInfo obj, String cId);

	/**
	 * @author lanlt
	 * @param cid
	 * @param domains
	 */
	void updateAll(String cid, List<ChildCareLeaveRemainingInfo> domains);
	
	/**
	 * 上限日数更新
	 * @param ThisFiscalYear 本年度上限日数
	 */
	void updateMaxDay(String sid, ChildCareNurseUpperLimit ThisFiscalYear);

}
