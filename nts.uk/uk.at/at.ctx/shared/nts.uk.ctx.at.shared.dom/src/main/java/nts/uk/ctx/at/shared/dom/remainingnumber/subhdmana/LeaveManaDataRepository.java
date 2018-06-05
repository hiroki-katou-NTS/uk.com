package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface LeaveManaDataRepository {
	
	// ドメインモデル「休出管理データ」を取得
	// 社員ID=パラメータ「社員ID」
	// 代休消化区分=未消化

	List<LeaveManagementData> getBySidWithsubHDAtr(String cid, String sid, int state);
	
	List<LeaveManagementData> getByComDayOffId(String cid, String sid, String comDayOffID);
	
	List<LeaveManagementData> getBySidNotUnUsed(String cid, String sid);
	
	List<LeaveManagementData> getBySid(String cid, String sid);
	
	List<LeaveManagementData> getByDateCondition (String cid, String sid, GeneralDate startDate, GeneralDate endDate);
	
	List<LeaveManagementData> getBySidWithHolidayDate(String cid, String sid, GeneralDate dateHoliday);

	void create(LeaveManagementData domain);
	
	void updateByLeaveIds(List<String> leaveIds);
	
	public void updateUnUseDayLeaveId(List<String> leaveIds);
	
	void updateSubByLeaveId(List<String> leaveIds);
	/**
	 * Get domain 休出管理データ by ID
	 * 
	 * @param leaveManaId
	 *            ID
	 * @return
	 */
	Optional<LeaveManagementData> getByLeaveId(String leaveManaId);

	/**
	 * Update domain 休出管理データ
	 * @param domain
	 */
	void udpate(LeaveManagementData domain);

	/**
	 * Delete domain 休出管理データ
	 * @param leaveId ID
	 */
	void deleteByLeaveId(String leaveId);
	/**
	 * ドメインモデル「休出管理データ」を取得する
	 * @param sid
	 * @param dateData ・休出日が指定期間内
	 * ・休出日≧INPUT.期間.開始年月日
	 * ・休出日≦INPUT.期間.終了年月日
	 * @return
	 */
	List<LeaveManagementData> getByDayOffDatePeriod(String sid, DatePeriod dateData);
	
}
