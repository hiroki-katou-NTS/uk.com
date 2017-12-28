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
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalOfOverTime;
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
		CalcAttrOfDailyPerformanceDto result = new CalcAttrOfDailyPerformanceDto();
		CalAttrOfDailyPerformance domain = this.repo.find(employeeId, baseDate);
		if(domain != null){
			result.setDivergenceTime(domain.getDivergenceTime().getDivergenceTime().value);
			result.setEmployeeId(domain.getEmployeeId());
			result.setFlexExcessTime(new AutoCalculationSettingDto(domain.getFlexExcessTime().getCalculationAttr().value,
					domain.getFlexExcessTime().getUpperLimitSetting().value));
			result.setHolidayTimeSetting(new AutoCalHolidaySettingDto(
					new AutoCalculationSettingDto(
							domain.getHolidayTimeSetting().getHolidayWorkTime().getCalculationAttr().value,
							domain.getHolidayTimeSetting().getHolidayWorkTime().getUpperLimitSetting().value),
					new AutoCalculationSettingDto(
							domain.getHolidayTimeSetting().getLateNightTime().getCalculationAttr().value,
							domain.getHolidayTimeSetting().getLateNightTime().getUpperLimitSetting().value)));
			result.setLeaveEarlySetting(
					new AutoCalOfLeaveEarlySettingDto(domain.getLeaveEarlySetting().getLeaveEarly().value,
							domain.getLeaveEarlySetting().getLeaveLate().value));
			result.setOvertimeSetting(getOverTimeSetting(domain.getOvertimeSetting()));
			result.setRasingSalarySetting(
					new AutoCalRaisingSalarySettingDto(domain.getRasingSalarySetting().getSalaryCalSetting().value,
							domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value));
			result.setYmd(domain.getYmd());
		}
		return result;
	}

	private AutoCalOfOverTimeDto getOverTimeSetting(AutoCalOfOverTime domain) {
		return new AutoCalOfOverTimeDto(
				new AutoCalculationSettingDto(domain.getEarlyOverTime().getCalculationAttr().value,
						domain.getEarlyOverTime().getUpperLimitSetting().value),
				new AutoCalculationSettingDto(domain.getEarlyMidnightOverTime().getCalculationAttr().value,
						domain.getEarlyMidnightOverTime().getUpperLimitSetting().value),
				new AutoCalculationSettingDto(domain.getNormalOverTime().getCalculationAttr().value,
						domain.getNormalOverTime().getUpperLimitSetting().value),
				new AutoCalculationSettingDto(domain.getNormalMidnightOverTime().getCalculationAttr().value,
						domain.getNormalMidnightOverTime().getUpperLimitSetting().value),
				new AutoCalculationSettingDto(domain.getLegalOverTime().getCalculationAttr().value,
						domain.getLegalOverTime().getUpperLimitSetting().value),
				new AutoCalculationSettingDto(domain.getLegalMidnightOverTime().getCalculationAttr().value,
						domain.getLegalMidnightOverTime().getUpperLimitSetting().value));
	}

}
