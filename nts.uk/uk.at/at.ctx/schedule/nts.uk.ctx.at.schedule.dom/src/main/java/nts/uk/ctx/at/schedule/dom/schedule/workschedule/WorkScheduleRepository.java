package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
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
	 * [3] Update(勤務予定)
	 * @param workSchedule
	 */
	
	void update(WorkSchedule workSchedule);
	/** Delete (勤務予定) **/
	void delete(String sid , GeneralDate ymd);

	void delete(String sid, DatePeriod datePeriod);

	boolean checkExits(String employeeID, GeneralDate ymd);

	List<WorkSchedule> getList(List<String> sids, DatePeriod period);

	Optional<ShortTimeOfDailyAttd> getShortTime(String sid, GeneralDate ymd, int childCareAtr, int frameNo);

	void insert(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID);

	void deleteAllShortTime(String sid, GeneralDate ymd);

	boolean checkExitsShortTime(String employeeID, GeneralDate ymd);

	void insertAtdLvwTimes(TimeLeavingWork leavingWork, String sID, GeneralDate yMD, String cID);

	void deleteSchAtdLvwTime(String sid, GeneralDate ymd, int workNo);
	
}

