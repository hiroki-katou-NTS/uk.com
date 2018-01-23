package nts.uk.ctx.at.record.infra.repository.calculationattribute;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

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
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaCalAttrOfDailyPerformanceRepoImpl extends JpaRepository implements CalAttrOfDailyPerformanceRepository {

	@Override
	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate) {
		KrcstDaiCalculationSet calc = this.queryProxy()
				.find(new KrcstDaiCalculationSetPK(employeeId, baseDate), KrcstDaiCalculationSet.class).orElse(null);
		if (calc != null) {
			KrcstFlexAutoCalSet flexCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.flexExcessTimeId, 36), KrcstFlexAutoCalSet.class).orElse(null);
			KrcstHolAutoCalSet holidayCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.holWorkTimeId, 36), KrcstHolAutoCalSet.class).orElse(null);
			KrcstOtAutoCalSet overtimeCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.overTimeWorkId, 36), KrcstOtAutoCalSet.class).orElse(null);
			return toDomain(calc, flexCalc, holidayCalc, overtimeCalc);
		}
		return null;
	}

	public CalAttrOfDailyPerformance toDomain(KrcstDaiCalculationSet calc, KrcstFlexAutoCalSet flexCalc,
			KrcstHolAutoCalSet holidayCalc, KrcstOtAutoCalSet overtimeCalc) {
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

		return new CalAttrOfDailyPerformance(calc.krcstDaiCalculationSetPK.sid, calc.krcstDaiCalculationSetPK.ymd, flex,
				new AutoCalRaisingSalarySetting(getEnum(calc.bonusPayNormalCalSet, SalaryCalAttr.class),
						getEnum(calc.bonusPaySpeCalSet, SpecificSalaryCalAttr.class)),
				holiday, overtime,
				new AutoCalOfLeaveEarlySetting(getEnum(calc.leaveEarlySet, LeaveAttr.class),
						getEnum(calc.leaveLateSet, LeaveAttr.class)),
				new AutoCalcSetOfDivergenceTime(getEnum(calc.divergenceTime, DivergenceTimeAttr.class)));
	}

	@Override
	public void update(CalAttrOfDailyPerformance domain) {
		KrcstDaiCalculationSet calc = this.queryProxy()
				.find(new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()),
						KrcstDaiCalculationSet.class)
				.orElse(null);
		if (calc == null) {
			add(domain);
		} else {
			KrcstFlexAutoCalSet flexCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.flexExcessTimeId, 36), KrcstFlexAutoCalSet.class).orElse(null);
			KrcstHolAutoCalSet holidayCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.holWorkTimeId, 36), KrcstHolAutoCalSet.class).orElse(null);
			KrcstOtAutoCalSet overtimeCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.overTimeWorkId, 36), KrcstOtAutoCalSet.class).orElse(null);
			if (domain.getRasingSalarySetting() != null) {
				calc.bonusPayNormalCalSet = domain.getRasingSalarySetting().getSalaryCalSetting().value;
				calc.bonusPaySpeCalSet = domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value;
			}
			if (domain.getDivergenceTime() != null) {
				calc.divergenceTime = domain.getDivergenceTime().getDivergenceTime().value;
			}
			if (domain.getLeaveEarlySetting() != null) {
				calc.leaveEarlySet = domain.getLeaveEarlySetting().getLeaveEarly().value;
				calc.leaveLateSet = domain.getLeaveEarlySetting().getLeaveLate().value;
			}
			setFlexCalcSetting(domain.getFlexExcessTime(), flexCalc);
			setHolidayCalcSetting(domain.getHolidayTimeSetting(), holidayCalc);
			setOvertimeCalcSetting(domain.getOvertimeSetting(), overtimeCalc);
			commandProxy().update(flexCalc);
			commandProxy().update(holidayCalc);
			commandProxy().update(overtimeCalc);
			commandProxy().update(calc);
		}
	}

	@Override
	public void add(CalAttrOfDailyPerformance domain) {
		KrcstFlexAutoCalSet flexCalc = new KrcstFlexAutoCalSet(IdentifierUtil.randomUniqueId());
		setFlexCalcSetting(domain.getFlexExcessTime(), flexCalc);

		KrcstHolAutoCalSet holidayCalc = new KrcstHolAutoCalSet(IdentifierUtil.randomUniqueId());
		setHolidayCalcSetting(domain.getHolidayTimeSetting(), holidayCalc);

		KrcstOtAutoCalSet overtimeCalc = new KrcstOtAutoCalSet(IdentifierUtil.randomUniqueId());
		setOvertimeCalcSetting(domain.getOvertimeSetting(), overtimeCalc);

		KrcstDaiCalculationSet calcSet = new KrcstDaiCalculationSet(
				new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()));
		if (domain.getRasingSalarySetting() != null) {
			calcSet.bonusPayNormalCalSet = domain.getRasingSalarySetting().getSalaryCalSetting().value;
			calcSet.bonusPaySpeCalSet = domain.getRasingSalarySetting().getSpecificSalaryCalSetting().value;
		}
		if (domain.getDivergenceTime() != null) {
			calcSet.divergenceTime = domain.getDivergenceTime().getDivergenceTime().value;
		}
		if (domain.getLeaveEarlySetting() != null) {
			calcSet.leaveEarlySet = domain.getLeaveEarlySetting().getLeaveEarly().value;
			calcSet.leaveLateSet = domain.getLeaveEarlySetting().getLeaveLate().value;
		}
		calcSet.overTimeWorkId = overtimeCalc.overTimeWorkId;
		calcSet.flexExcessTimeId = flexCalc.flexExcessTimeId;
		calcSet.holWorkTimeId = holidayCalc.holWorkTimeId;
		commandProxy().insert(flexCalc);
		commandProxy().insert(holidayCalc);
		commandProxy().insert(overtimeCalc);
		commandProxy().insert(calcSet);
	}

	private void setOvertimeCalcSetting(AutoCalOfOverTime domain, KrcstOtAutoCalSet overtimeCalc) {
		if (domain != null) {
			overtimeCalc.earlyMidOtCalAtr = domain.getEarlyMidnightOverTime() == null ? 1
					: domain.getEarlyMidnightOverTime().getCalculationAttr().value;
			overtimeCalc.earlyMidOtLimitSet = domain.getEarlyMidnightOverTime() == null ? 0
					: domain.getEarlyMidnightOverTime().getUpperLimitSetting().value;
			overtimeCalc.earlyOverTimeCalAtr = domain.getEarlyOverTime() == null ? 1
					: domain.getEarlyOverTime().getCalculationAttr().value;
			overtimeCalc.earlyOverTimeLimitSet = domain.getEarlyOverTime() == null ? 0
					: domain.getEarlyOverTime().getUpperLimitSetting().value;
			overtimeCalc.legalMidOtCalAtr = domain.getLegalMidnightOverTime() == null ? 1
					: domain.getLegalMidnightOverTime().getCalculationAttr().value;
			overtimeCalc.legalMidOtLimitSet = domain.getLegalMidnightOverTime() == null ? 0
					: domain.getLegalMidnightOverTime().getUpperLimitSetting().value;
			overtimeCalc.legalOverTimeCalAtr = domain.getLegalOverTime() == null ? 1
					: domain.getLegalOverTime().getCalculationAttr().value;
			overtimeCalc.legalOverTimeLimitSet = domain.getLegalOverTime() == null ? 0
					: domain.getLegalOverTime().getUpperLimitSetting().value;
			overtimeCalc.normalMidOtCalAtr = domain.getNormalMidnightOverTime() == null ? 1
					: domain.getNormalMidnightOverTime().getCalculationAttr().value;
			overtimeCalc.normalMidOtLimitSet = domain.getNormalMidnightOverTime() == null ? 0
					: domain.getNormalMidnightOverTime().getUpperLimitSetting().value;
			overtimeCalc.normalOverTimeCalAtr = domain.getNormalOverTime() == null ? 1
					: domain.getNormalOverTime().getCalculationAttr().value;
			overtimeCalc.normalOverTimeLimitSet = domain.getNormalOverTime() == null ? 0
					: domain.getNormalOverTime().getUpperLimitSetting().value;
		}
	}

	private void setFlexCalcSetting(AutoCalculationSetting domain, KrcstFlexAutoCalSet flexCalc) {
		if (domain != null) {
			flexCalc.flexExcessLimitSet = domain.getUpperLimitSetting().value;
			flexCalc.flexExcessTimeCalAtr = domain.getCalculationAttr().value;
		}
	}

	private void setHolidayCalcSetting(AutoCalHolidaySetting domain, KrcstHolAutoCalSet holidayCalc) {
		if (domain != null) {
			holidayCalc.holWorkTimeCalAtr = domain.getHolidayWorkTime() == null ? 1
					: domain.getHolidayWorkTime().getCalculationAttr().value;
			holidayCalc.holWorkTimeLimitSet = domain.getHolidayWorkTime() == null ? 0
					: domain.getHolidayWorkTime().getUpperLimitSetting().value;
			holidayCalc.lateNightTimeCalAtr = domain.getLateNightTime() == null ? 1
					: domain.getLateNightTime().getCalculationAttr().value;
			holidayCalc.lateNightTimeLimitSet = domain.getLateNightTime() == null ? 0
					: domain.getLateNightTime().getUpperLimitSetting().value;
		}
	}

	private AutoCalculationSetting newAutoCalcSetting(int calc, int limit) {
		return new AutoCalculationSetting(getEnum(calc, AutoCalculationCategoryOutsideHours.class),
				getEnum(limit, LimitOfOverTimeSetting.class));
	}

	private <T> T getEnum(int value, Class<T> className) {
		return EnumAdaptor.valueOf(value, className);
	}

	@Override
	public List<CalAttrOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate) {
		StringBuilder builder = new StringBuilder("SELECT c FROM KrcstDaiCalculationSet c ");
		builder.append("WHERE c.krcstDaiCalculationSetPK.sid IN :ids ");
		builder.append("AND c.krcstDaiCalculationSetPK.ymd <= :end AND c.krcstDaiCalculationSetPK.ymd >= :start");
		String childQuery = "SELECT c FROM {0} c WHERE c.{1} IN :ids";
		List<KrcstDaiCalculationSet> calces = this.queryProxy().query(builder.toString(), KrcstDaiCalculationSet.class)
				.setParameter("ids", employeeId).setParameter("end", baseDate.end())
				.setParameter("start", baseDate.start()).getList();
		if(calces.isEmpty()){
			return new ArrayList<>();
		}
		List<KrcstOtAutoCalSet> ots = this.queryProxy()
				.query(childQuery.replace("{0}", "KrcstOtAutoCalSet").replace("{1}", "overTimeWorkId"),
						KrcstOtAutoCalSet.class)
				.setParameter("ids", calces.stream().map(c -> c.overTimeWorkId).collect(Collectors.toList())).getList();
		List<KrcstFlexAutoCalSet> flexes = this.queryProxy()
				.query(childQuery.replace("{0}", "KrcstFlexAutoCalSet").replace("{1}", "flexExcessTimeId"),
						KrcstFlexAutoCalSet.class)
				.setParameter("ids", calces.stream().map(c -> c.flexExcessTimeId).collect(Collectors.toList())).getList();
		List<KrcstHolAutoCalSet> holies = this.queryProxy()
				.query(childQuery.replace("{0}", "KrcstHolAutoCalSet").replace("{1}", "holWorkTimeId"),
						KrcstHolAutoCalSet.class)
				.setParameter("ids", calces.stream().map(c -> c.holWorkTimeId).collect(Collectors.toList())).getList();
		return calces.stream().map(c -> {
			KrcstFlexAutoCalSet flex = flexes.stream().filter(f -> f.flexExcessTimeId.equals(c.flexExcessTimeId)).findFirst().orElse(null);
			KrcstOtAutoCalSet ot = ots.stream().filter(f -> f.overTimeWorkId.equals(c.overTimeWorkId)).findFirst().orElse(null);
			KrcstHolAutoCalSet holi = holies.stream().filter(f -> f.holWorkTimeId.equals(c.holWorkTimeId)).findFirst().orElse(null);
			return toDomain(c, flex, holi, ot);
		}).collect(Collectors.toList());
	}

}
