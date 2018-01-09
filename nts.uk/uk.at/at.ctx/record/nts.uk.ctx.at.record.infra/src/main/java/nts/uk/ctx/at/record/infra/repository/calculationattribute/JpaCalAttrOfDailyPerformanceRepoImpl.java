package nts.uk.ctx.at.record.infra.repository.calculationattribute;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
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
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstDaiCalculationSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstDaiCalculationSetPK;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstFlexAutoCalSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstHolAutoCalSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstOtAutoCalSet;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalculationCategoryOutsideHours;

@Stateless
public class JpaCalAttrOfDailyPerformanceRepoImpl extends JpaRepository implements CalAttrOfDailyPerformanceRepository {

	@Override
	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate) {
		KrcstDaiCalculationSet calc = this.queryProxy()
				.find(new KrcstDaiCalculationSetPK(employeeId, baseDate), KrcstDaiCalculationSet.class).orElse(null);
		if (calc != null) {
			KrcstFlexAutoCalSet flexCalc = this.queryProxy().find(calc.flexExcessTimeId, KrcstFlexAutoCalSet.class)
					.orElse(null);
			KrcstHolAutoCalSet holidayCalc = this.queryProxy().find(calc.holWorkTimeId, KrcstHolAutoCalSet.class)
					.orElse(null);
			KrcstOtAutoCalSet overtimeCalc = this.queryProxy().find(calc.overTimeWorkId, KrcstOtAutoCalSet.class)
					.orElse(null);
			AutoCalculationSetting flex = null;
			AutoCalHolidaySetting holiday = null;
			AutoCalOfOverTime overtime = null;
			if (flexCalc != null) {
				flex = newAutoCalcSetting(flexCalc.flexExcessTimeCalAtr, flexCalc.flexExcessLimitSet);
			}
			if (holidayCalc != null) {
				holiday = new AutoCalHolidaySetting(
						newAutoCalcSetting(holidayCalc.holWorkTimeCalAtr, holidayCalc.holWorkTimeLimitSet),
						newAutoCalcSetting(holidayCalc.lateNightTimeCalAtr, holidayCalc.lateNightTimeLimitSet));
			}
			if (overtimeCalc != null) {
				overtime = new AutoCalOfOverTime(
						newAutoCalcSetting(overtimeCalc.earlyOverTimeCalAtr, overtimeCalc.earlyOverTimeLimitSet),
						newAutoCalcSetting(overtimeCalc.earlyMidOtCalAtr, overtimeCalc.earlyMidOtLimitSet),
						newAutoCalcSetting(overtimeCalc.normalOverTimeCalAtr, overtimeCalc.normalOverTimeLimitSet),
						newAutoCalcSetting(overtimeCalc.normalMidOtCalAtr, overtimeCalc.normalMidOtLimitSet),
						newAutoCalcSetting(overtimeCalc.legalOverTimeCalAtr, overtimeCalc.legalOverTimeLimitSet),
						newAutoCalcSetting(overtimeCalc.legalMidOtCalAtr, overtimeCalc.legalMidOtLimitSet));
			}

			return new CalAttrOfDailyPerformance(employeeId, baseDate, flex,
					new AutoCalRaisingSalarySetting(getEnum(calc.bonusPayNormalCalSet, SalaryCalAttr.class),
							getEnum(calc.bonusPaySpeCalSet, SpecificSalaryCalAttr.class)),
					holiday, overtime,
					new AutoCalOfLeaveEarlySetting(getEnum(calc.leaveEarlySet, LeaveAttr.class),
							getEnum(calc.leaveLateSet, LeaveAttr.class)),
					new AutoCalcSetOfDivergenceTime(getEnum(calc.divergenceTime, DivergenceTimeAttr.class)));
		}
		return null;
	}

	@Override
	public void update(CalAttrOfDailyPerformance domain) {
		KrcstDaiCalculationSet calc = this.queryProxy()
				.find(new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()), KrcstDaiCalculationSet.class).orElse(null);
		KrcstFlexAutoCalSet flexCalc = this.queryProxy().find(calc.flexExcessTimeId, KrcstFlexAutoCalSet.class)
				.orElse(null);
		KrcstHolAutoCalSet holidayCalc = this.queryProxy().find(calc.holWorkTimeId, KrcstHolAutoCalSet.class)
				.orElse(null);
		KrcstOtAutoCalSet overtimeCalc = this.queryProxy().find(calc.overTimeWorkId, KrcstOtAutoCalSet.class)
				.orElse(null);
		calc.bonusPayNormalCalSet = domain.getRasingSalarySetting().getSalaryCalSetting().value;
		calc.bonusPaySpeCalSet = domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value;
		calc.divergenceTime = domain.getDivergenceTime().getDivergenceTime().value;
		calc.leaveEarlySet = domain.getLeaveEarlySetting().getLeaveEarly().value;
		calc.leaveLateSet = domain.getLeaveEarlySetting().getLeaveLate().value;
		flexCalc.flexExcessLimitSet = domain.getFlexExcessTime().getUpperLimitSetting().value;
		flexCalc.flexExcessTimeCalAtr = domain.getFlexExcessTime().getCalculationAttr().value;
		holidayCalc.holWorkTimeCalAtr = domain.getHolidayTimeSetting().getHolidayWorkTime().getCalculationAttr().value;
		holidayCalc.holWorkTimeLimitSet = domain.getHolidayTimeSetting().getHolidayWorkTime().getUpperLimitSetting().value;
		holidayCalc.lateNightTimeCalAtr = domain.getHolidayTimeSetting().getLateNightTime().getCalculationAttr().value;
		holidayCalc.lateNightTimeLimitSet = domain.getHolidayTimeSetting().getLateNightTime().getUpperLimitSetting().value;
		overtimeCalc.earlyMidOtCalAtr = domain.getOvertimeSetting().getEarlyMidnightOverTime().getCalculationAttr().value;
		overtimeCalc.earlyMidOtLimitSet = domain.getOvertimeSetting().getEarlyMidnightOverTime().getUpperLimitSetting().value;
		overtimeCalc.earlyOverTimeCalAtr = domain.getOvertimeSetting().getEarlyOverTime().getCalculationAttr().value;
		overtimeCalc.earlyOverTimeLimitSet = domain.getOvertimeSetting().getEarlyOverTime().getUpperLimitSetting().value;
		overtimeCalc.legalMidOtCalAtr = domain.getOvertimeSetting().getLegalMidnightOverTime().getCalculationAttr().value;
		overtimeCalc.legalMidOtLimitSet = domain.getOvertimeSetting().getLegalMidnightOverTime().getUpperLimitSetting().value;
		overtimeCalc.legalOverTimeCalAtr = domain.getOvertimeSetting().getLegalOverTime().getCalculationAttr().value;
		overtimeCalc.legalOverTimeLimitSet = domain.getOvertimeSetting().getLegalOverTime().getUpperLimitSetting().value;
		overtimeCalc.normalMidOtCalAtr = domain.getOvertimeSetting().getNormalMidnightOverTime().getCalculationAttr().value;
		overtimeCalc.normalMidOtLimitSet = domain.getOvertimeSetting().getNormalMidnightOverTime().getUpperLimitSetting().value;
		overtimeCalc.normalOverTimeCalAtr = domain.getOvertimeSetting().getNormalOverTime().getCalculationAttr().value;
		overtimeCalc.normalOverTimeLimitSet = domain.getOvertimeSetting().getNormalOverTime().getUpperLimitSetting().value;
		commandProxy().update(flexCalc);
		commandProxy().update(holidayCalc);
		commandProxy().update(overtimeCalc);
		commandProxy().update(calc);
	}

	@Override
	public void add(CalAttrOfDailyPerformance domain) {
		KrcstFlexAutoCalSet flexCalc = new KrcstFlexAutoCalSet(IdentifierUtil.randomUniqueId(),
				domain.getFlexExcessTime().getCalculationAttr().value,
				domain.getFlexExcessTime().getUpperLimitSetting().value);
		KrcstHolAutoCalSet holidayCalc = new KrcstHolAutoCalSet(IdentifierUtil.randomUniqueId(),
				domain.getHolidayTimeSetting().getHolidayWorkTime().getCalculationAttr().value,
				domain.getHolidayTimeSetting().getHolidayWorkTime().getUpperLimitSetting().value,
				domain.getHolidayTimeSetting().getLateNightTime().getCalculationAttr().value,
				domain.getHolidayTimeSetting().getLateNightTime().getUpperLimitSetting().value);
		KrcstOtAutoCalSet overtimeCalc = new KrcstOtAutoCalSet(IdentifierUtil.randomUniqueId(),
				domain.getOvertimeSetting().getEarlyOverTime().getCalculationAttr().value,
				domain.getOvertimeSetting().getEarlyOverTime().getUpperLimitSetting().value,
				domain.getOvertimeSetting().getEarlyMidnightOverTime().getCalculationAttr().value,
				domain.getOvertimeSetting().getEarlyMidnightOverTime().getUpperLimitSetting().value,
				domain.getOvertimeSetting().getNormalOverTime().getCalculationAttr().value,
				domain.getOvertimeSetting().getNormalOverTime().getUpperLimitSetting().value,
				domain.getOvertimeSetting().getNormalMidnightOverTime().getCalculationAttr().value,
				domain.getOvertimeSetting().getNormalMidnightOverTime().getUpperLimitSetting().value,
				domain.getOvertimeSetting().getLegalOverTime().getCalculationAttr().value,
				domain.getOvertimeSetting().getLegalOverTime().getUpperLimitSetting().value,
				domain.getOvertimeSetting().getLegalMidnightOverTime().getCalculationAttr().value,
				domain.getOvertimeSetting().getLegalMidnightOverTime().getUpperLimitSetting().value);
		KrcstDaiCalculationSet calcSet = new KrcstDaiCalculationSet(
				new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()), flexCalc.flexExcessTimeId,
				domain.getRasingSalarySetting().getSalaryCalSetting().value,
				domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value, holidayCalc.holWorkTimeId,
				overtimeCalc.overTimeWorkId, domain.getLeaveEarlySetting().getLeaveLate().value,
				domain.getLeaveEarlySetting().getLeaveEarly().value, domain.getDivergenceTime().getDivergenceTime().value);
		commandProxy().insert(flexCalc);
		commandProxy().insert(holidayCalc);
		commandProxy().insert(overtimeCalc);
		commandProxy().insert(calcSet);
	}

	private AutoCalculationSetting newAutoCalcSetting(int calc, int limit) {
		return new AutoCalculationSetting(getEnum(calc, AutoCalculationCategoryOutsideHours.class),
				getEnum(limit, LimitOfOverTimeSetting.class));
	}

	private <T> T getEnum(int value, Class<T> className) {
		return EnumAdaptor.valueOf(value, className);
	}

}
