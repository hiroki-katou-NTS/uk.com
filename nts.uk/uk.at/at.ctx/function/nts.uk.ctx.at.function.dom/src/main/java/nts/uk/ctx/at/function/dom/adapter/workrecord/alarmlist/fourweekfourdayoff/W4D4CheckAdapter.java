package nts.uk.ctx.at.function.dom.adapter.workrecord.alarmlist.fourweekfourdayoff;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapterDto;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface W4D4CheckAdapter {
	Optional<ValueExtractAlarm> checkHoliday(String workplaceID, String employeeID,
			DatePeriod period,List<String> listHolidayWorkTypeCode,List<RecordWorkInfoFunAdapterDto> listWorkInfoOfDailyPerformance);
}
