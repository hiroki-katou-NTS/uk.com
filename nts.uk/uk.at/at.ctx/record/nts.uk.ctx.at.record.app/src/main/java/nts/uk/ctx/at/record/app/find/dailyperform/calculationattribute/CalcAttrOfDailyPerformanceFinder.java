package nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.AutoCalHolidaySettingDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.AutoCalOfLeaveEarlySettingDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.AutoCalOfOverTimeDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.AutoCalRaisingSalarySettingDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.AutoCalculationSettingDto;
import nts.uk.ctx.at.record.app.find.dailyperform.calculationattribute.dto.CalcAttrOfDailyPerformanceDto;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalHolidaySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfLeaveEarlySetting;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfOverTime;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalculationSetting;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.app.util.attendanceitem.FinderFacade;

@Stateless
public class CalcAttrOfDailyPerformanceFinder extends FinderFacade {

	@Inject
	private CalAttrOfDailyPerformanceRepository repo;

	@SuppressWarnings("unchecked")
	@Override
	public CalcAttrOfDailyPerformanceDto find(String employeeId, GeneralDate baseDate) {
		CalAttrOfDailyPerformance domain = this.repo.find(employeeId, baseDate);
		return toDto(domain);
	}

	private CalcAttrOfDailyPerformanceDto toDto(CalAttrOfDailyPerformance domain) {
		CalcAttrOfDailyPerformanceDto result = new CalcAttrOfDailyPerformanceDto();
		if (domain != null) {
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

	private AutoCalRaisingSalarySettingDto newAutoCalcSalarySetting(CalAttrOfDailyPerformance domain) {
		return domain == null ? null : new AutoCalRaisingSalarySettingDto(
						domain.getRasingSalarySetting().getSalaryCalSetting().value,
						domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value);
	}

	private AutoCalOfLeaveEarlySettingDto newAutoCalcLeaveSetting(AutoCalOfLeaveEarlySetting domain) {
		return domain == null ? null : new AutoCalOfLeaveEarlySettingDto(
						domain.getLeaveEarly().value, 
						domain.getLeaveLate().value);
	}

	private AutoCalHolidaySettingDto newAutoCalcHolidaySetting(AutoCalHolidaySetting domain) {
		return domain == null ? null : new AutoCalHolidaySettingDto(newAutoCalcSetting(domain.getHolidayWorkTime()),
						newAutoCalcSetting(domain.getLateNightTime()));
	}

	private AutoCalOfOverTimeDto getOverTimeSetting(AutoCalOfOverTime domain) {
		return domain == null ? null : new AutoCalOfOverTimeDto(newAutoCalcSetting(domain.getEarlyOverTime()),
						newAutoCalcSetting(domain.getEarlyMidnightOverTime()),
						newAutoCalcSetting(domain.getNormalOverTime()),
						newAutoCalcSetting(domain.getNormalMidnightOverTime()),
						newAutoCalcSetting(domain.getLegalOverTime()),
						newAutoCalcSetting(domain.getLegalMidnightOverTime()));
	}

	private AutoCalculationSettingDto newAutoCalcSetting(AutoCalculationSetting domain) {
		return domain == null ? null : new AutoCalculationSettingDto(
					domain.getCalculationAttr().value, 
					domain.getUpperLimitSetting().value);
	}

}
