package nts.uk.ctx.at.record.app.command.dailyperform.calculationattribute;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.AutoCalculationSettingDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalHolidaySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfLeaveEarlySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfOverTime;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalRaisingSalarySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LeaveAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.LimitOfOverTimeSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SalaryCalAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.SpecificSalaryCalAttr;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;

public class CalcAttrOfDailyPerformanceCommand extends DailyWorkCommonCommand {

	@Getter
	private CalcAttrOfDailyPerformanceDto data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = (CalcAttrOfDailyPerformanceDto) item;
	}
	
	@Override
	public CalAttrOfDailyPerformance toDomain() {
		return new CalAttrOfDailyPerformance(this.getEmployeeId(), this.getWorkDate(), newAutoCalcSetting(data.getFlexExcessTime()),
				new AutoCalRaisingSalarySetting(
						getEnum(data.getRasingSalarySetting().getSalaryCalSetting(), SalaryCalAttr.class),
						getEnum(data.getRasingSalarySetting().getSpecificSalaryCalSetting(),
								SpecificSalaryCalAttr.class)),
				new AutoCalHolidaySetting(newAutoCalcSetting(data.getHolidayTimeSetting().getHolidayWorkTime()),
						newAutoCalcSetting(data.getHolidayTimeSetting().getLateNightTime())),
				new AutoCalOfOverTime(newAutoCalcSetting(data.getOvertimeSetting().getEarlyOverTime()),
						newAutoCalcSetting(data.getOvertimeSetting().getEarlyMidnightOverTime()),
						newAutoCalcSetting(data.getOvertimeSetting().getNormalOverTime()),
						newAutoCalcSetting(data.getOvertimeSetting().getNormalMidnightOverTime()),
						newAutoCalcSetting(data.getOvertimeSetting().getLegalOverTime()),
						newAutoCalcSetting(data.getOvertimeSetting().getLegalMidnightOverTime())),
				new AutoCalOfLeaveEarlySetting(getEnum(data.getLeaveEarlySetting().getLeaveEarly(), LeaveAttr.class),
						getEnum(data.getLeaveEarlySetting().getLeaveLate(), LeaveAttr.class)),
				new AutoCalcSetOfDivergenceTime(getEnum(data.getDivergenceTime(), DivergenceTimeAttr.class)));
	}
	
	private AutoCalculationSetting newAutoCalcSetting(AutoCalculationSettingDto dto) {
		return new AutoCalculationSetting(getEnum(dto.getCalculationAttr(), AutoCalculationCategoryOutsideHours.class),
				getEnum(dto.getUpperLimitSetting(), LimitOfOverTimeSetting.class));
	}

	private <T> T getEnum(int value, Class<T> enumType) {
		return ConvertHelper.getEnum(value, enumType);
	}
}
