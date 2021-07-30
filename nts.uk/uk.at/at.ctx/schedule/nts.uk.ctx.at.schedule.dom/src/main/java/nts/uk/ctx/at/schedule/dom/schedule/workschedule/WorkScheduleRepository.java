package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;

/**
 * 勤務予定Repository
 * @author HieuLT
 */
public interface WorkScheduleRepository {

	/**
	 * get
	 * 社員1日分の勤務予定を取得する
	 * @param employeeID 社員ID
	 * @param ymd 年月日
	 * @return 勤務予定
	 */
	Optional<WorkSchedule> get(String employeeID , GeneralDate ymd);

	/**
	 * get*
	 * 社員複数人の期間分の勤務予定を取得する
	 * @param sids 社員IDリスト
	 * @param period 期間
	 * @return 勤務予定(List)
	 */
	List<WorkSchedule> getList(List<String> sids, DatePeriod period);

	/**
	 * 社員の期間分の勤務予定を取得する
	 * @param sid 社員ID
	 * @param period 期間
	 * @return 勤務予定(List)
	 */
	List<WorkSchedule> getListBySid(String sid, DatePeriod period);


	boolean checkExits(String employeeID, GeneralDate ymd);


	/**
	 * Insert(勤務予定)
	 * @param workSchedule 勤務予定
	 */
	void insert(WorkSchedule workSchedule);

	/**
	 * InsertAll(会社ID, List<勤務予定>)
	 * @param cid 会社ID
	 * @param workSchedule 勤務予定リスト
	 */
	void insertAll(String cID, List<WorkSchedule> workSchedule);


	/**
	 * Update(社員ID, 期間)
	 * @param sid 社員ID
	 * @param datePeriod 期間
	 */
	void update(WorkSchedule workSchedule);


	/**
	 * Delete(社員ID, 期間)
	 * @param sid 社員ID
	 * @param datePeriod 期間
	 */
	void delete(String sid, DatePeriod datePeriod);

	/**
	 * Delete(社員ID, 年月日)
	 * @param sid 社員ID
	 * @param ymd 年月日
	 */
	void delete(String sid, GeneralDate ymd);

	/**
	 * Delete*(社員ID, List<年月日>)
	 * @param sid 社員ID
	 * @param ymds 年月日リスト
	 */
	void deleteListDate(String sid, List<GeneralDate> ymds);


	/**
	 * 最も未来の勤務予定の年月日を取得する
	 * @param listEmp 社員IDリスト
	 * @return 年月日
	 */
	Optional<GeneralDate> getMaxDateWorkSche(List<String> listEmp);

	//Optional<GeneralDate> getMaxDate(List<String> employeeIDs, GeneralDate ymd);


	Optional<ShortTimeOfDailyAttd> getShortTime(String sid, GeneralDate ymd, int childCareAtr, int frameNo);

	void insert(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID);

	void deleteAllShortTime(String sid, GeneralDate ymd);

	boolean checkExitsShortTime(String employeeID, GeneralDate ymd);

	void insertAtdLvwTimes(TimeLeavingWork leavingWork, String sID, GeneralDate yMD, String cID);

	void deleteSchAtdLvwTime(String sid, GeneralDate ymd, int workNo);

}