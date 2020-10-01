package nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.pub.workinformation.InfoCheckNotRegisterPubExport;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.arc.time.calendar.period.DatePeriod;

public interface W4D4CheckPub {
	Optional<AlarmExtractionValue4W4DExport> checkHoliday(String workplaceID, String employeeID, DatePeriod period,List<String> listHolidayWorkTypeCode,List<InfoCheckNotRegisterPubExport> listWorkInfoOfDailyPerByID); 
	
	List<AlarmExtractionValue4W4DExport> checkHolidayWithSchedule(String workplaceID, String employeeID, DatePeriod period,
			List<WorkType> legalHolidayWorkTypeCodes, List<WorkType> illegalHolidayWorkTypeCodes,
			List<InfoCheckNotRegisterPubExport> listWorkInfoOfDailyPerformance,
			List<InfoCheckNotRegisterPubExport> basicSchedules);
}
