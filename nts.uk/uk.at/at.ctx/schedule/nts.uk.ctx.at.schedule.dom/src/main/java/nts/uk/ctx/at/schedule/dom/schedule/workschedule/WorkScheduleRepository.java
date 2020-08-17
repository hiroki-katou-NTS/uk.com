package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

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
	
}

