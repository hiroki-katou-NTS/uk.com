package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

public interface CareLeaveRemainingInfoRepository {

	/**
	 * get all NursingCareLeaveRemainingInfo
	 * 
	 * @return
	 */
	Optional<CareLeaveRemainingInfo> getCareByEmpId(String empId);
	
	/**
	 * @author lanlt
	 * @param cid
	 * @param empIds
	 * @return
	 */
	List<CareLeaveRemainingInfo> getCareByEmpIdsAndCid(String cid, List<String> empIds);
	
	Optional<CareLeaveDataInfo> getCareInfoDataBysId(String empId);
	/**
	 * @author lanlt
	 * @param sids
	 * @return
	 */
	List<CareLeaveDataInfo> getAllCareInfoDataBysId(String cid, List<String> sids);
	
	List<CareLeaveDataInfo> getAllCareInfoDataBysIdCps013(String cid, List<String> sids, Map<String, Object> enums);

	/**
	 * add LeaveForCareInfo object
	 * 
	 * @param obj
	 */
	void add(CareLeaveRemainingInfo obj, String cId);
	
	/**
	 * @author lanlt
	 * @param cid
	 * @param domains
	 */
	void addAll(String cid, List<CareLeaveRemainingInfo> domains);

	/**
	 * update LeaveForCareInfo object
	 * 
	 * @param obj
	 */
	void update(CareLeaveRemainingInfo obj, String cId);
	
	/**
	 * @author lanlt
	 * @param cid
	 * @param domains
	 */
	void updateAll(String cid, List<CareLeaveRemainingInfo> domains);
	
	/**
	 * 上限日数更新
	 * @param ThisFiscalYear 本年度上限日数
	 */
	void updateMaxDay(String sid, ChildCareNurseUpperLimit ThisFiscalYear);

}
