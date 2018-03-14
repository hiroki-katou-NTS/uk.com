package nts.uk.ctx.at.record.dom.divergence.time.setting;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.attendance.AttendanceItem;


public interface DivergenceTimeRepository {

	/**
	 * get all divergence time
	 * @param companyId
	 * @return
	 */
	List<DivergenceTime> getAllDivTime(String companyId);
	/**
	 * get all divergence reason
	 * @param companyId
	 * @param divTimeId
	 * @return
	 */
	List<DivergenceReason> getDivReasonByCode(String companyId, int divTimeId);
	/**
	 * get all attendance item
	 * @param companyId
	 * @param divTimeId
	 * @return
	 */
	List<AttendanceItem> getallItembyCode(String companyId, int divTimeId);
	/**
	 * update divergence time
	 * @param divTime
	 */
	void updateDivTime(DivergenceTime divTime);
	/**
	 * add divergence reason
	 * @param divReason
	 */
	void addDivReason(int divTimeId, String company,DivergenceReason divReason);
	/**
	 * update divergence reason
	 * @param divReason
	 */
	void updateDivReason(int divTimeId, String company,DivergenceReason divReason);
	/**
	 * delete divergence reason
	 * @param companyId
	 * @param divTimeId
	 * @param divReasonCode
	 */
	void deleteDivReason(String companyId, int divTimeId, String divReasonCode);
	/**
	 * get divergence time
	 * @param companyId
	 * @param divTimeId
	 * @param divReasonCode
	 * @return
	 */
	Optional<DivergenceReason> getDivReason(String companyId, int divTimeId, String divReasonCode);
	/**
	 * add list item id new
	 * @param lstItemId
	 */
	void addAttendanceItemId(String companyId, int divTimeId,Double AttendanceId);
	/**
	 * delete list item id old
	 * @param companyId
	 * @param divTimeId
	 */
	void deleteAttendItemId(String companyId, int divTimeId, Double AttendanceId);
	
	/**
	 * NamPT
	 * getDivergenceTimeName for mapping AttendanceItemName
	 */
	List<DivergenceTime> getDivergenceTimeName(String companyId, List<Integer> divTimeIds);
}
