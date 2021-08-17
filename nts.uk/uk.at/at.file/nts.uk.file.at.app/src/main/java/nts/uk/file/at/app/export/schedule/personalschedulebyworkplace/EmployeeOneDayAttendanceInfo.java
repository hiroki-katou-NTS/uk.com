package nts.uk.file.at.app.export.schedule.personalschedulebyworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.scheduletable.ScheduleTableAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceDayAttr;

import java.util.Map;
import java.util.Optional;

/**
 * 一日分の社員の勤怠情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeOneDayAttendanceInfo {
    private String employeeId;
    private GeneralDate date;
    //    就業時間帯略名　＝　1.2の結果.就業時間帯略名
    private Optional<String> workTimeName;
    //    シフトマスタ名称　＝　1.3の結果.シフトマスタの表示情報.名称
    private Optional<String> shiftMasterName;
    //    シフト背景色　＝　1.3の結果..シフトマスタの表示情報.色
    private Optional<String> shiftBackgroundColor;
    //    勤務種類略名　＝　1.4の結果.勤務種類略名
    private Optional<String> workTypeName;
    //    出勤休日区分　＝　1.5の結果
    private Optional<AttendanceDayAttr> attendanceDayAttr;
    // 勤怠データMap
    private Map<ScheduleTableAttendanceItem, ItemValue> attendanceData;
}
