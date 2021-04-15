package nts.uk.ctx.at.shared.dom.adapter.workschedule;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
/**
 * 
 * @author tutk
 *
 */
public interface WorkScheWorkInforSharedAdapter {
	public Optional<WorkScheduleWorkSharedImport> get(String employeeID , GeneralDate ymd);
	
	public Optional<WorkInfoOfDailyAttendance> getWorkInfoOfDailyAttendance(String employeeID , GeneralDate ymd);
	
}
