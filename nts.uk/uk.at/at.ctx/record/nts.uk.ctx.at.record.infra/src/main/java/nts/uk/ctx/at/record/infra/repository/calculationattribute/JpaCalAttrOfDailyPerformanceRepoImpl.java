package nts.uk.ctx.at.record.infra.repository.calculationattribute;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstDaiCalculationSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstDaiCalculationSetPK;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstFlexAutoCalSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstHolAutoCalSet;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstOtAutoCalSet;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.TimeLimitUpperLimitSetting;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaCalAttrOfDailyPerformanceRepoImpl extends JpaRepository implements CalAttrOfDailyPerformanceRepository {

	private static final String REMOVE_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcstDaiCalculationSet a ");
		builderString.append("WHERE a.krcstDaiCalculationSetPK.sid = :employeeId ");
		builderString.append("AND a.krcstDaiCalculationSetPK.ymd = :ymd ");
		REMOVE_BY_KEY = builderString.toString();
	}

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
				calc.bonusPayNormalCalSet = domain.getRasingSalarySetting().isRaisingSalaryCalcAtr() ? 1 : 0;
				calc.bonusPaySpeCalSet = domain.getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr() ? 1 : 0;
			}
			if (domain.getDivergenceTime() != null) {
				calc.divergenceTime = domain.getDivergenceTime().getDivergenceTime().value;
			}
			if (domain.getLeaveEarlySetting() != null) {
				calc.leaveEarlySet = domain.getLeaveEarlySetting().isLate() ? 1 : 0;
				calc.leaveLateSet = domain.getLeaveEarlySetting().isLeaveEarly() ? 1 : 0;
			}
			setFlexCalcSetting(domain.getFlexExcessTime().getFlexOtTime(), flexCalc);
			setHolidayCalcSetting(domain.getHolidayTimeSetting(), holidayCalc);
			setOvertimeCalcSetting(domain.getOvertimeSetting(), overtimeCalc);
			commandProxy().update(flexCalc);
			commandProxy().update(holidayCalc);
			commandProxy().update(overtimeCalc);
			commandProxy().update(calc);
			this.getEntityManager().flush();
		}
	}

	@Override
	public void add(CalAttrOfDailyPerformance domain) {
		KrcstFlexAutoCalSet flexCalc = new KrcstFlexAutoCalSet(IdentifierUtil.randomUniqueId());
		setFlexCalcSetting(domain.getFlexExcessTime().getFlexOtTime(), flexCalc);

		KrcstHolAutoCalSet holidayCalc = new KrcstHolAutoCalSet(IdentifierUtil.randomUniqueId());
		setHolidayCalcSetting(domain.getHolidayTimeSetting(), holidayCalc);

		KrcstOtAutoCalSet overtimeCalc = new KrcstOtAutoCalSet(IdentifierUtil.randomUniqueId());
		setOvertimeCalcSetting(domain.getOvertimeSetting(), overtimeCalc);

		KrcstDaiCalculationSet calcSet = new KrcstDaiCalculationSet(
				new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()));
		if (domain.getRasingSalarySetting() != null) {
			calcSet.bonusPayNormalCalSet = domain.getRasingSalarySetting().isRaisingSalaryCalcAtr() ? 1 : 0;
			calcSet.bonusPaySpeCalSet = domain.getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr() ? 1 : 0;
		}
		if (domain.getDivergenceTime() != null) {
			calcSet.divergenceTime = domain.getDivergenceTime().getDivergenceTime().value;
		}
		if (domain.getLeaveEarlySetting() != null) {
			calcSet.leaveEarlySet = domain.getLeaveEarlySetting().isLeaveEarly() ? 1 : 0;
			calcSet.leaveLateSet = domain.getLeaveEarlySetting().isLate() ? 1 : 0;
		}
		calcSet.overTimeWorkId = overtimeCalc.overTimeWorkId;
		calcSet.flexExcessTimeId = flexCalc.flexExcessTimeId;
		calcSet.holWorkTimeId = holidayCalc.holWorkTimeId;
		commandProxy().insert(flexCalc);
		commandProxy().insert(holidayCalc);
		commandProxy().insert(overtimeCalc);
		commandProxy().insert(calcSet);
		this.getEntityManager().flush();
	}

	@Override
	public List<CalAttrOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate) {
		List<CalAttrOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT c, ot, f, ho FROM KrcstDaiCalculationSet c ");
		query.append(" LEFT JOIN KrcstOtAutoCalSet ot ON c.overTimeWorkId = ot.overTimeWorkId ");
		query.append(" LEFT JOIN KrcstFlexAutoCalSet f ON c.flexExcessTimeId = f.flexExcessTimeId ");
		query.append(" LEFT JOIN KrcstHolAutoCalSet ho ON c.holWorkTimeId = ho.holWorkTimeId ");
		query.append(" WHERE c.krcstDaiCalculationSetPK.sid IN :ids ");
		query.append(" AND c.krcstDaiCalculationSetPK.ymd <= :end AND c.krcstDaiCalculationSetPK.ymd >= :start");
		
		TypedQueryWrapper<Object[]> tCalcQuery=  this.queryProxy().query(query.toString(), Object[].class);
//		
//		StringBuilder builder = new StringBuilder("SELECT c FROM KrcstDaiCalculationSet c ");
//		builder.append("WHERE c.krcstDaiCalculationSetPK.sid IN :ids ");
//		builder.append("AND c.krcstDaiCalculationSetPK.ymd <= :end AND c.krcstDaiCalculationSetPK.ymd >= :start");
//		TypedQueryWrapper<KrcstDaiCalculationSet> tCalcQuery=  this.queryProxy().query(builder.toString(), KrcstDaiCalculationSet.class);
//		TypedQueryWrapper<KrcstOtAutoCalSet> tOtQuery=  this.queryProxy()
//						.query("SELECT c FROM KrcstOtAutoCalSet c WHERE c.overTimeWorkId IN :ids", KrcstOtAutoCalSet.class);
//		TypedQueryWrapper<KrcstFlexAutoCalSet> tFlexQuery=  this.queryProxy()
//						.query("SELECT c FROM KrcstFlexAutoCalSet c WHERE c.flexExcessTimeId IN :ids", KrcstFlexAutoCalSet.class);
//		TypedQueryWrapper<KrcstHolAutoCalSet> tHolQuery=  this.queryProxy()
//						.query("SELECT c FROM KrcstHolAutoCalSet c WHERE c.holWorkTimeId IN :ids", KrcstHolAutoCalSet.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			List<Object[]> calces = tCalcQuery.setParameter("ids", empIds)
												.setParameter("start", baseDate.start())
												.setParameter("end", baseDate.end()).getList();
			if (!calces.isEmpty()) {
				result.addAll(calces.stream().map(e -> {
					KrcstDaiCalculationSet c = (KrcstDaiCalculationSet) e[0];
					KrcstOtAutoCalSet ot = (KrcstOtAutoCalSet) e[1];
					KrcstFlexAutoCalSet flex = (KrcstFlexAutoCalSet) e[2];
					KrcstHolAutoCalSet holi = (KrcstHolAutoCalSet) e[3];
					return toDomain(c, flex, holi, ot);
				}).collect(Collectors.toList()));
			}
		});
		return result;
	}

	private CalAttrOfDailyPerformance toDomain(KrcstDaiCalculationSet calc, KrcstFlexAutoCalSet flexCalc,
			KrcstHolAutoCalSet holidayCalc, KrcstOtAutoCalSet overtimeCalc) {
		AutoCalSetting flex = null;
		AutoCalRestTimeSetting holiday = null;
		AutoCalOvertimeSetting overtime = null;
		if (flexCalc != null) {
			flex = newAutoCalcSetting(flexCalc.flexExcessTimeCalAtr, flexCalc.flexExcessLimitSet);
		}
		if (holidayCalc != null) {
			holiday = new AutoCalRestTimeSetting(
					newAutoCalcSetting(holidayCalc.holWorkTimeCalAtr, holidayCalc.holWorkTimeLimitSet),
					newAutoCalcSetting(holidayCalc.lateNightTimeCalAtr, holidayCalc.lateNightTimeLimitSet));
		}
		if (overtimeCalc != null) {
			overtime = new AutoCalOvertimeSetting(
					newAutoCalcSetting(overtimeCalc.earlyOverTimeCalAtr, overtimeCalc.earlyOverTimeLimitSet),
					newAutoCalcSetting(overtimeCalc.earlyMidOtCalAtr, overtimeCalc.earlyMidOtLimitSet),
					newAutoCalcSetting(overtimeCalc.normalOverTimeCalAtr, overtimeCalc.normalOverTimeLimitSet),
					newAutoCalcSetting(overtimeCalc.normalMidOtCalAtr, overtimeCalc.normalMidOtLimitSet),
					newAutoCalcSetting(overtimeCalc.legalOverTimeCalAtr, overtimeCalc.legalOverTimeLimitSet),
					newAutoCalcSetting(overtimeCalc.legalMidOtCalAtr, overtimeCalc.legalMidOtLimitSet));
		}

		return new CalAttrOfDailyPerformance(calc.krcstDaiCalculationSetPK.sid, calc.krcstDaiCalculationSetPK.ymd,
				new AutoCalFlexOvertimeSetting(flex),
				new AutoCalRaisingSalarySetting(
						calc.bonusPaySpeCalSet == 1 ? true : false,
						calc.bonusPayNormalCalSet == 1 ? true : false
						),
				holiday, overtime,
				new AutoCalcOfLeaveEarlySetting(calc.leaveEarlySet == 1 ? true : false,
						calc.leaveLateSet  == 1 ? true : false),
				new AutoCalcSetOfDivergenceTime(getEnum(calc.divergenceTime, DivergenceTimeAttr.class)));
	}

	private void setOvertimeCalcSetting(AutoCalOvertimeSetting domain, KrcstOtAutoCalSet overtimeCalc) {
		if (domain != null) {
			overtimeCalc.earlyMidOtCalAtr = domain.getEarlyMidOtTime() == null ? 0
					: domain.getEarlyMidOtTime().getCalAtr().value;
			overtimeCalc.earlyMidOtLimitSet = domain.getEarlyMidOtTime() == null ? 0
					: domain.getEarlyMidOtTime().getUpLimitORtSet().value;
			overtimeCalc.earlyOverTimeCalAtr = domain.getEarlyOtTime() == null ? 0
					: domain.getEarlyOtTime().getCalAtr().value;
			overtimeCalc.earlyOverTimeLimitSet = domain.getEarlyOtTime() == null ? 0
					: domain.getEarlyOtTime().getUpLimitORtSet().value;
			overtimeCalc.legalMidOtCalAtr = domain.getLegalMidOtTime() == null ? 0
					: domain.getLegalMidOtTime().getCalAtr().value;
			overtimeCalc.legalMidOtLimitSet = domain.getLegalMidOtTime() == null ? 0
					: domain.getLegalMidOtTime().getUpLimitORtSet().value;
			overtimeCalc.legalOverTimeCalAtr = domain.getLegalOtTime() == null ? 0
					: domain.getLegalOtTime().getCalAtr().value;
			overtimeCalc.legalOverTimeLimitSet = domain.getLegalOtTime() == null ? 0
					: domain.getLegalOtTime().getUpLimitORtSet().value;
			overtimeCalc.normalMidOtCalAtr = domain.getNormalMidOtTime() == null ? 0
					: domain.getNormalMidOtTime().getCalAtr().value;
			overtimeCalc.normalMidOtLimitSet = domain.getNormalMidOtTime() == null ? 0
					: domain.getNormalMidOtTime().getUpLimitORtSet().value;
			overtimeCalc.normalOverTimeCalAtr = domain.getNormalOtTime() == null ? 0
					: domain.getNormalOtTime().getCalAtr().value;
			overtimeCalc.normalOverTimeLimitSet = domain.getNormalOtTime() == null ? 0
					: domain.getNormalOtTime().getUpLimitORtSet().value;
		}
	}

	private void setFlexCalcSetting(AutoCalSetting domain, KrcstFlexAutoCalSet flexCalc) {
		if (domain != null) {
			flexCalc.flexExcessLimitSet = domain.getUpLimitORtSet() == null ? 0 : domain.getUpLimitORtSet().value;
			flexCalc.flexExcessTimeCalAtr = domain.getCalAtr() == null ? 0 : domain.getCalAtr().value;
		}
	}

	private void setHolidayCalcSetting(AutoCalRestTimeSetting domain, KrcstHolAutoCalSet holidayCalc) {
		if (domain != null) {
			holidayCalc.holWorkTimeCalAtr = domain.getRestTime() == null ? 0 : domain.getRestTime().getCalAtr().value;
			holidayCalc.holWorkTimeLimitSet = domain.getRestTime() == null ? 0
					: domain.getRestTime().getUpLimitORtSet().value;
			holidayCalc.lateNightTimeCalAtr = domain.getLateNightTime() == null ? 0
					: domain.getLateNightTime().getCalAtr().value;
			holidayCalc.lateNightTimeLimitSet = domain.getLateNightTime() == null ? 0
					: domain.getLateNightTime().getUpLimitORtSet().value;
		}
	}

	private AutoCalSetting newAutoCalcSetting(int calc, int limit) {
		return new AutoCalSetting(getEnum(limit, TimeLimitUpperLimitSetting.class),
				getEnum(calc, AutoCalAtrOvertime.class));
	}

	private <T> T getEnum(int value, Class<T> className) {
		return EnumAdaptor.valueOf(value, className);
	}

	@Override
	public void deleteByKey(String employeeId, GeneralDate baseDate) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCST_DAI_CALCULATION_SET Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + baseDate + "'" ;
		try {
			con.createStatement().executeUpdate(sqlQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
//		this.getEntityManager().createQuery(REMOVE_BY_KEY).setParameter("employeeId", employeeId)
//				.setParameter("ymd", baseDate).executeUpdate();
//		this.getEntityManager().flush();
	}

	@Override
	public List<CalAttrOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<CalAttrOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT c, ot, f, ho FROM KrcstDaiCalculationSet c ");
		query.append(" LEFT JOIN KrcstOtAutoCalSet ot ON c.overTimeWorkId = ot.overTimeWorkId ");
		query.append(" LEFT JOIN KrcstFlexAutoCalSet f ON c.flexExcessTimeId = f.flexExcessTimeId ");
		query.append(" LEFT JOIN KrcstHolAutoCalSet ho ON c.holWorkTimeId = ho.holWorkTimeId ");
		query.append("WHERE c.krcstDaiCalculationSetPK.sid IN :ids ");
		query.append("AND c.krcstDaiCalculationSetPK.ymd IN :date");
		
		TypedQueryWrapper<Object[]> tCalcQuery=  this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			List<Object[]> calces = tCalcQuery.setParameter("ids", p.keySet())
								.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet())).getList();
			calces = calces.stream().filter(e -> {
				KrcstDaiCalculationSet c = (KrcstDaiCalculationSet) e[0];
				return p.get(c.krcstDaiCalculationSetPK.sid).contains(c.krcstDaiCalculationSetPK.ymd);
			}).collect(Collectors.toList());
			if (!calces.isEmpty()) {
				result.addAll(calces.stream().map(e -> {
					KrcstDaiCalculationSet c = (KrcstDaiCalculationSet) e[0];
					KrcstOtAutoCalSet ot = (KrcstOtAutoCalSet) e[1];
					KrcstFlexAutoCalSet flex = (KrcstFlexAutoCalSet) e[2];
					KrcstHolAutoCalSet holi = (KrcstHolAutoCalSet) e[3];
					return toDomain(c, flex, holi, ot);
				}).collect(Collectors.toList()));
			}
		});
		return result;
	}

}
