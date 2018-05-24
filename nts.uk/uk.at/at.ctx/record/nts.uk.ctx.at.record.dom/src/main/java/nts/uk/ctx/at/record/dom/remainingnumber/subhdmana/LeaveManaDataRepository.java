package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface LeaveManaDataRepository {
	
	// ドメインモデル「休出管理データ」を取得
	// 社員ID=パラメータ「社員ID」
	// 代休消化区分=未消化

	List<LeaveManagementDataAgg> getBySidWithsubHDAtr(String cid, String sid, int state);
	
	List<LeaveManagementDataAgg> getByComDayOffId(String cid, String sid, String comDayOffID);
	
	List<LeaveManagementDataAgg> getBySidNotUnUsed(String cid, String sid);
	
	List<LeaveManagementDataAgg> getBySid(String cid, String sid);
	
	List<LeaveManagementDataAgg> getByDateCondition (String cid, String sid, GeneralDate startDate, GeneralDate endDate);
	
	List<LeaveManagementDataAgg> getBySidWithHolidayDate(String cid, String sid, GeneralDate dateHoliday);

	void create(LeaveManagementDataAgg domain);
	
	void updateByLeaveIds(List<String> leaveIds);
	
	void updateNotByLeaveIds(List<String> leaveIds);
	/**
	 * Get domain 休出管理データ by ID
	 * 
	 * @param leaveManaId
	 *            ID
	 * @return
	 */
	Optional<LeaveManagementDataAgg> getByLeaveId(String leaveManaId);

	/**
	 * Update domain 休出管理データ
	 * @param domain
	 */
	void udpate(LeaveManagementDataAgg domain);

	/**
	 * Delete domain 休出管理データ
	 * @param leaveId ID
	 */
	void deleteByLeaveId(String leaveId);
	
}
