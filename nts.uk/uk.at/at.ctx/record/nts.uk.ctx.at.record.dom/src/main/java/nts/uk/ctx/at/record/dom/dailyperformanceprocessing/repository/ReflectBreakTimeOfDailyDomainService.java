package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.BreakTimeZoneSettingOutPut;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

public interface ReflectBreakTimeOfDailyDomainService {
		//就業時間帯の休憩時間帯を日別実績に反映する
	public BreakTimeOfDailyPerformance reflectBreakTime(String companyId,String employeeID, GeneralDate processingDate,String empCalAndSumExecLogID,TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance, 
				WorkInfoOfDailyPerformance WorkInfo);
	
		//就業時間帯の休憩時間帯を日別実績に写す
	public BreakTimeOfDailyPerformance reflectBreakTimeZone(String companyId, String employeeID, GeneralDate processingDate,
			String empCalAndSumExecLogID, TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance,
			WorkInfoOfDailyPerformance WorkInfo);

	public Optional<BreakTimeOfDailyPerformance> getBreakTime(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance WorkInfo);
	
	public Optional<BreakTimeOfDailyPerformance> getBreakTime(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance WorkInfo, List<ErrorAlarmWorkRecord> errorMaster);
	
	public Optional<BreakTimeOfDailyPerformance> getBreakTime(String companyId, String employeeID, GeneralDate processingDate,
			WorkInfoOfDailyPerformance WorkInfo, List<ErrorAlarmWorkRecord> errorMaster, Optional<WorkTimeSetting> workTime);

	boolean CheckBreakTimeFromFixedWorkSetting(String companyId, int weekdayHolidayClassification,
			String workTimeCode, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut, WorkStyle checkWorkDay);
	
	boolean confirmIntermissionTimeZone(String companyId, int weekdayHolidayClassification,
			String workTimeCode, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut);
	
	boolean confirmInterFlexWorkSetting(String companyId, int weekdayHolidayClassification,
			String workTimeCode, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut, WorkStyle checkWorkDay);
	
	boolean ConfirmInterTimezoneStaggeredWorkSetting(String companyId, String employeeID,
			GeneralDate processingDate, String empCalAndSumExecLogID, int weekdayHolidayClassification,
			WorkInfoOfDailyPerformance WorkInfo, BreakTimeZoneSettingOutPut breakTimeZoneSettingOutPut,
			WorkStyle checkWorkDay);
}
