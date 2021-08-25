package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import nts.uk.ctx.at.aggregation.dom.scheduletable.OneDayEmployeeAttendanceInfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

import javax.ejb.Stateless;
import java.util.*;

/**
 * 一日分の社員の表示情報を取得す
 */
@Stateless
public class EmployeeOneDayAttendanceInfoQuery {

    /**
     * 取得する
     */
    public List<OneDayEmployeeAttendanceInfo> get(List<IntegrationOfDaily> integrationOfDailyList) {
        List<OneDayEmployeeAttendanceInfo> listEmpOneDayAttendanceInfo = new ArrayList<>();
        // Loop：日別勤怠 in Input.List<日別勤怠(Work)>
        for (IntegrationOfDaily integration : integrationOfDailyList) {
            // 1.1 作る(日別勤怠(Work))
            OneDayEmployeeAttendanceInfo data = OneDayEmployeeAttendanceInfo.create(integration);
            listEmpOneDayAttendanceInfo.add(data);
        }
        return listEmpOneDayAttendanceInfo;
    }
}
