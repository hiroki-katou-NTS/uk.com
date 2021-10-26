package nts.uk.file.at.app.export.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecGettingAtr;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;

import java.util.Optional;

/**
 * シフト表示情報dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDisplayInfoDto {
    // 社員ID=日別勤怠.社員ID
    private String employeeId;

    // 年月日=日別勤怠.年月日
    private GeneralDate ymd;

    // 予実区分=予実区分
    private ScheRecGettingAtr targetData;

    // Optional<シフトコード＞=5.1の結果.コード
    private Optional<String> shiftCode;

    // Optional<シフト名＞=5.1の結果.表示情報.名称
    private Optional<String> shiftName;

    // Optional<出勤休日区分>=5.2の結果.表示情報
    private Optional<AttendanceHolidayAttr> attendanceHolidayAttr;
}
