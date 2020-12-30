package nts.uk.ctx.at.request.ac.record.timeleaveapplication;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyAttendanceTimePubImport;
import nts.uk.ctx.at.request.dom.adapter.timeleaveapplication.DailyAttendanceTimeAdapter;
import nts.uk.ctx.at.request.dom.adapter.timeleaveapplication.DailyAttendanceTimeImport;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DailyAttendanceTimeImpl implements DailyAttendanceTimeAdapter {

    @Inject
    private DailyAttendanceTimePub dailyAttendanceTimePub;

    @Override
    public DailyAttendanceTimeImport calcDailyAttendance(String employeeId, GeneralDate ymd, String workTypeCode, String workTimeCode,
                                                         List<TimeZone> lstTimeZone, List<AttendanceTime> breakStartTime, List<AttendanceTime> breakEndTime) {
        DailyAttendanceTimePubImport dailyAttendanceTimePubImport = new DailyAttendanceTimePubImport();
        dailyAttendanceTimePubImport.setEmployeeid(employeeId);
        dailyAttendanceTimePubImport.setYmd(ymd);
        dailyAttendanceTimePubImport.setWorkTypeCode(workTypeCode == null ? null : new WorkTypeCode(workTypeCode));
        dailyAttendanceTimePubImport.setWorkTimeCode(workTimeCode == null ? null : new WorkTimeCode(workTimeCode));
        dailyAttendanceTimePubImport.setLstTimeZone(lstTimeZone);
        dailyAttendanceTimePubImport.setBreakStartTime(breakStartTime);
        dailyAttendanceTimePubImport.setBreakEndTime(breakEndTime);

        DailyAttendanceTimePubExport export = dailyAttendanceTimePub.calcDailyAttendance(dailyAttendanceTimePubImport);
        return new DailyAttendanceTimeImport(
            export.getOverTime(),
            export.getHolidayWorkTime(),
            export.getBonusPayTime(),
            export.getSpecBonusPayTime(),
            export.getFlexTime(),
            export.getMidNightTime(),
            export.getTimeOutSideMidnight(),
            export.getCalOvertimeMidnight(),
            export.getCalHolidayMidnight()
        );
    }
}
