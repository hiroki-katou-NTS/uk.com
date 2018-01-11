package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;

@Data
@AttendanceItemRoot(rootName = "日別実績の計算区分")
public class CalcAttrOfDailyPerformanceDto implements ConvertibleAttendanceItem {

	/** 社員ID: 社員ID */
	private String employeeId;

	/** 年月日: 年月日 */
	private GeneralDate ymd;

	/** フレックス超過時間: フレックス超過時間の自動計算設定 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "フレックス超過時間")
	private AutoCalculationSettingDto flexExcessTime;

	/** 加給: 加給の自動計算設定 */
	@AttendanceItemLayout(layout = "B", jpPropertyName = "加給")
	private AutoCalRaisingSalarySettingDto rasingSalarySetting;

	/** 休出時間: 休出時間の自動計算設定 */
	@AttendanceItemLayout(layout = "C", jpPropertyName = "休出時間")
	private AutoCalHolidaySettingDto holidayTimeSetting;

	/** 残業時間: 残業時間の自動計算設定 */
	@AttendanceItemLayout(layout = "D", jpPropertyName = "残業時間")
	private AutoCalOfOverTimeDto overtimeSetting;

	/** 遅刻早退: 遅刻早退の自動計算設定 */
	@AttendanceItemLayout(layout = "E", jpPropertyName = "遅刻早退")
	private AutoCalOfLeaveEarlySettingDto leaveEarlySetting;

	/** 乖離時間: 乖離時間の自動計算設定 */
	@AttendanceItemLayout(layout = "F", jpPropertyName = "乖離時間")
	@AttendanceItemValue(itemId = 640, type = ValueType.INTEGER)
	private int divergenceTime;
}
