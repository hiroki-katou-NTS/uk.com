/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.prospect.countdays.attendance;

import java.util.HashMap;
import java.util.Map;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus.AttendanceStatusList;

/**
 * 日別勤怠から出勤してるかを判断する
 * @author raiki_asada
 */
public class CheckAttendanceForIntegrationOfDaily {
    
    public static boolean check(IntegrationOfDaily integrationOfDaily) {
        
        Map<GeneralDate, AttendanceTimeOfDailyAttendance> attendanceTimeOfDailys = new HashMap<>();
	Map<GeneralDate, TimeLeavingOfDailyAttd> timeLeavingOfDailys = new HashMap<>();
        
        integrationOfDaily.getAttendanceTimeOfDailyPerformance()
                .ifPresent(attendanceTimeOfDaily -> attendanceTimeOfDailys.put(integrationOfDaily.getYmd(), attendanceTimeOfDaily));
        integrationOfDaily.getAttendanceLeave()
                .ifPresent(timeLeavingOfDaily -> timeLeavingOfDailys.put(integrationOfDaily.getYmd(), timeLeavingOfDaily));
        
        AttendanceStatusList statusList = new AttendanceStatusList(attendanceTimeOfDailys, timeLeavingOfDailys);
        return statusList.isAttendanceDay(integrationOfDaily.getYmd());
    }
}
