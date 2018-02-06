package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalHolidaySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfLeaveEarlySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfOverTime;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SalaryCalAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SpecificSalaryCalAttr;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;

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
	@AttendanceItemValue(type = ValueType.INTEGER)
	private int divergenceTime;
	
	public static CalcAttrOfDailyPerformanceDto getDto(CalAttrOfDailyPerformance domain) {
		CalcAttrOfDailyPerformanceDto result = new CalcAttrOfDailyPerformanceDto();
		if (domain != null) {
			result.setEmployeeId(domain.getEmployeeId());
			result.setYmd(domain.getYmd());
			result.setDivergenceTime(domain.getDivergenceTime().getDivergenceTime().value);
			result.setEmployeeId(domain.getEmployeeId());
			result.setFlexExcessTime(newAutoCalcSetting(domain.getFlexExcessTime()));
			result.setHolidayTimeSetting(newAutoCalcHolidaySetting(domain.getHolidayTimeSetting()));
			result.setLeaveEarlySetting(newAutoCalcLeaveSetting(domain.getLeaveEarlySetting()));
			result.setOvertimeSetting(getOverTimeSetting(domain.getOvertimeSetting()));
			result.setRasingSalarySetting(newAutoCalcSalarySetting(domain));
			result.setYmd(domain.getYmd());
		}
		return result;
	}

	private static AutoCalRaisingSalarySettingDto newAutoCalcSalarySetting(CalAttrOfDailyPerformance domain) {
		return domain == null ? null : new AutoCalRaisingSalarySettingDto(
						domain.getRasingSalarySetting().getSalaryCalSetting().value,
						domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value);
	}

	private static AutoCalOfLeaveEarlySettingDto newAutoCalcLeaveSetting(AutoCalOfLeaveEarlySetting domain) {
		return domain == null ? null : new AutoCalOfLeaveEarlySettingDto(
						domain.getLeaveEarly().value, 
						domain.getLeaveLate().value);
	}

	private static AutoCalHolidaySettingDto newAutoCalcHolidaySetting(AutoCalHolidaySetting domain) {
		return domain == null ? null : new AutoCalHolidaySettingDto(
						newAutoCalcSetting(domain.getHolidayWorkTime()),
						newAutoCalcSetting(domain.getLateNightTime()));
	}

	private static AutoCalOfOverTimeDto getOverTimeSetting(AutoCalOfOverTime domain) {
		return domain == null ? null : new AutoCalOfOverTimeDto(newAutoCalcSetting(domain.getEarlyOverTime()),
						newAutoCalcSetting(domain.getEarlyMidnightOverTime()),
						newAutoCalcSetting(domain.getNormalOverTime()),
						newAutoCalcSetting(domain.getNormalMidnightOverTime()),
						newAutoCalcSetting(domain.getLegalOverTime()),
						newAutoCalcSetting(domain.getLegalMidnightOverTime()));
	}

	private static AutoCalculationSettingDto newAutoCalcSetting(AutoCalculationSetting domain) {
		return domain == null ? null : new AutoCalculationSettingDto(
					domain.getCalculationAttr().value, 
					domain.getUpperLimitSetting().value);
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public CalAttrOfDailyPerformance toDomain() {
		return new CalAttrOfDailyPerformance(
				this.employeeId, 
				this.ymd, 
				newAutoCalcSetting(this.flexExcessTime),
				createAutoCalcRaisingSalarySetting(),
				createAutoCalcHolidaySetting(),
				createAutoOverTimeSetting(),
				createAutoCalcLeaveSetting(),
				new AutoCalcSetOfDivergenceTime(getEnum(this.divergenceTime, DivergenceTimeAttr.class)));
	}

	private AutoCalRaisingSalarySetting createAutoCalcRaisingSalarySetting() {
		return this.rasingSalarySetting == null ? null : new AutoCalRaisingSalarySetting(
				getEnum(this.rasingSalarySetting.getSalaryCalSetting(), SalaryCalAttr.class),
				getEnum(this.rasingSalarySetting.getSpecificSalaryCalSetting(), SpecificSalaryCalAttr.class));
	}

	private AutoCalHolidaySetting createAutoCalcHolidaySetting() {
		return this.holidayTimeSetting == null ? null : new AutoCalHolidaySetting(
				newAutoCalcSetting(this.holidayTimeSetting.getHolidayWorkTime()),
				newAutoCalcSetting(this.holidayTimeSetting.getLateNightTime()));
	}

	private AutoCalOfLeaveEarlySetting createAutoCalcLeaveSetting() {
		return this.leaveEarlySetting == null ? null : new AutoCalOfLeaveEarlySetting(
				getEnum(this.leaveEarlySetting.getLeaveEarly(), LeaveAttr.class),
				getEnum(this.leaveEarlySetting.getLeaveLate(), LeaveAttr.class));
	}

	private AutoCalOfOverTime createAutoOverTimeSetting() {
		return this.overtimeSetting == null ? null : new AutoCalOfOverTime(
				newAutoCalcSetting(this.overtimeSetting.getEarlyOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getEarlyMidnightOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getNormalOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getNormalMidnightOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getLegalOverTime()),
				newAutoCalcSetting(this.overtimeSetting.getLegalMidnightOverTime()));
	}
	
	private AutoCalculationSetting newAutoCalcSetting(AutoCalculationSettingDto dto) {
		return dto == null ? null : new AutoCalculationSetting(
				getEnum(dto.getCalculationAttr(), AutoCalAtrOvertime.class),
				getEnum(dto.getUpperLimitSetting(), TimeLimitUpperLimitSetting.class));
	}

	private <T> T getEnum(int value, Class<T> enumType) {
		return ConvertHelper.getEnum(value, enumType);
	}
}
