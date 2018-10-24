package nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface LeaveManaDataRepository {
	
	// ドメインモデル「休出管理データ」を取得
	// 社員ID=パラメータ「社員ID」
	// 代休消化区分=未消化

	List<LeaveManagementData> getBySidWithsubHDAtr(String cid, String sid, int state);
	
	List<LeaveManagementData> getByComDayOffId(String cid, String sid, String comDayOffID);
	
	List<LeaveManagementData> getBySidNotUnUsed(String cid, String sid);
	
	/**
	 * 
	 * @param cid
	 * @param sid
	 * @param ymd ・休出日 < INPUT．集計開始日
	 * @return
	 */
	List<LeaveManagementData> getBySidDate(String cid, String sid, GeneralDate ymd);
	
	List<LeaveManagementData> getBySid(String cid, String sid);

	
	List<LeaveManagementData> getByDateCondition (String cid, String sid, GeneralDate startDate, GeneralDate endDate);
	
	List<LeaveManagementData> getBySidWithHolidayDate(String cid, String sid, GeneralDate dateHoliday);
	
	void create(LeaveManagementData domain);
	
	void update(LeaveManagementData domain);
	
	void updateByLeaveIds(List<String> leaveIds);
	
	public void updateUnUseDayLeaveId(String leaveId, Double unUsedDay, List<DaysOffMana> daysOffMana);
	
	void updateSubByLeaveId(String leaveId, Boolean check);
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
	 * @param leaveId 休出データID
	 * @param isCheckedExpired 期限切れ
	 * @param expiredDate 期限
	 * @param occurredDays 休出日数
	 * @param unUsedDays 未使用日数
	 */
	void udpateByHolidaySetting(String leaveId, Boolean isCheckedExpired, GeneralDate expiredDate, double occurredDays, double unUsedDays);

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
	/**
	 * ドメインモデル「休出管理データ」を取得する
	 * @param sid
	 * @param dateData　・使用期限日≧INPUT.期間.開始年月日・使用期限日≦INPUT.期間.終了年月日
	 * OR ・消滅日≧INPUT.期間.開始年月日　・消滅日≦INPUT.期間.終了年月日
	 * @param unUseDays ・未使用日数＞unUseDays
	 * @param subHDAtr 代休消化区分 = subHDAtr
	 * @return
	 */
	List<LeaveManagementData> getByExtinctionPeriod(String sid, DatePeriod tmpDateData, DatePeriod dateData, double unUseDays, DigestionAtr subHDAtr);

	List<LeaveManagementData> getByHoliday(String sid, Boolean unknownDate, DatePeriod dayOff);
	
	void deleteById(List<String> leaveId);
	
	Integer getDeadlineCompensatoryLeaveCom(String sID, GeneralDate dayOff, int deadlMonth);
	/**
	 * 
	 * @param cid
	 * @param sid
	 * @param ymd 休出日 < INPUT．集計開始日 OR 休出日がない
	 * @param unUse 未使用日数 >0  OR 未使用時間数 > 0
	 * @param state  代休消化区分
	 * @return
	 */
	List<LeaveManagementData> getBySidYmd(String cid, String sid, GeneralDate ymd, DigestionAtr state);
}
