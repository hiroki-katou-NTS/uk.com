package nts.uk.ctx.at.request.dom.adapter.timeleaveapplication;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;

import java.util.List;

public interface DailyAttendanceTimeAdapter {

    DailyAttendanceTimeImport calcDailyAttendance(String employeeId, GeneralDate ymd, String workTypeCode, String workTimeCode,
                                                  List<TimeZone> lstTimeZone, List<AttendanceTime> breakStartTime, List<AttendanceTime> breakEndTime);

}
