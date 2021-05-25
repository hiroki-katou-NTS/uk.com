package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;

/**
 * 
 * @author HieuLT
 *
 */
public interface WorkScheduleRepository {
	
	
	/** 		
	 * [1] get 社員1日分の勤務予定を取得する
	 * @param employeeID
	 * @param ymd
	 * @return
	 */
	Optional<WorkSchedule> get(String employeeID , GeneralDate ymd);
	
	/**
	 * [2] Insert(勤務予定)
	 * @param workSchedule
	 */
	void insert(WorkSchedule workSchedule); 
	
	
	
	/**
	 * [5] InsertAll(会社ID, 勤務予定リスト)
	 * @param cid
	 * @param workSchedule
	 */
	void insertAll(String cID, List<WorkSchedule> workSchedule);
	
	/**
	 * [6] Update(社員ID, 期間)
	 * @param sid
	 * @param datePeriod
	 */
	
	void update(WorkSchedule workSchedule);
	
	void updateConfirmedState(WorkSchedule workSchedule);
	
	void delete(String sid, DatePeriod datePeriod);
	
	/** Delete (勤務予定) **/
	void delete(String sid , GeneralDate ymd);

	boolean checkExits(String employeeID, GeneralDate ymd);

	List<WorkSchedule> getList(List<String> sids, DatePeriod period);

	Optional<ShortTimeOfDailyAttd> getShortTime(String sid, GeneralDate ymd, int childCareAtr, int frameNo);

	void insert(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID);

	void deleteAllShortTime(String sid, GeneralDate ymd);

	boolean checkExitsShortTime(String employeeID, GeneralDate ymd);

	void insertAtdLvwTimes(TimeLeavingWork leavingWork, String sID, GeneralDate yMD, String cID);

	void deleteSchAtdLvwTime(String sid, GeneralDate ymd, int workNo);

	void deleteListDate(String sid, List<GeneralDate> ymds);
	
	List<WorkSchedule> getListBySid(String sid, DatePeriod period);
	/**
	 * 	[7] 最も未来の勤務予定の年月日を取得する
	 * @param listEmp
	 * @return
	 */
	Optional<GeneralDate> getMaxDateWorkSche(List<String> listEmp);

	//Optional<GeneralDate> getMaxDate(List<String> employeeIDs, GeneralDate ymd);
}

