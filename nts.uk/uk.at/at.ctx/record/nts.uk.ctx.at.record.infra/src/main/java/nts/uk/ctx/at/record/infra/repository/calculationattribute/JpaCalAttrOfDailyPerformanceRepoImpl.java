package nts.uk.ctx.at.record.infra.repository.calculationattribute;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang3.StringUtils;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.record.dom.calculationattribute.repo.CalAttrOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcdtDayInfoCalc;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcstDaiCalculationSetPK;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcmtCalcSetFlex;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcmtCalcSetHdWork;
import nts.uk.ctx.at.record.infra.entity.daily.calculationattribute.KrcmtCalcSetOverTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalSetting;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class JpaCalAttrOfDailyPerformanceRepoImpl extends JpaRepository implements CalAttrOfDailyPerformanceRepository {

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate) {
		KrcdtDayInfoCalc calc = this.queryProxy()
				.find(new KrcstDaiCalculationSetPK(employeeId, baseDate), KrcdtDayInfoCalc.class).orElse(null);
		if (calc != null) {
			//1
			String sql1 = "select * from KRCMT_CALC_SET_FLEX "
					+ " where FLEX_EXCESS_TIME_ID = ?";
			Optional<KrcmtCalcSetFlex> flexCalc= Optional.empty();
			try (PreparedStatement stmt = this.connection().prepareStatement(sql1)) {
				stmt.setString(1 , StringUtils.rightPad(calc.flexExcessTimeId, 36));
				flexCalc = new NtsResultSet(stmt.executeQuery()).getSingle(rec -> KrcmtCalcSetFlex.MAPPER.toEntity(rec));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			//2
			String sql2 = "select * from KRCMT_CALC_SET_HD_WORK "
					+ " where HOL_WORK_TIME_ID = ?";
			Optional<KrcmtCalcSetHdWork> holidayCalc= Optional.empty();
			try (PreparedStatement stmt = this.connection().prepareStatement(sql2)) {
				stmt.setString(1 , StringUtils.rightPad(calc.holWorkTimeId, 36));
				holidayCalc = new NtsResultSet(stmt.executeQuery()).getSingle(rec -> KrcmtCalcSetHdWork.MAPPER.toEntity(rec));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			//3
			String sql3 = "select * from KRCMT_CALC_SET_OVER_TIME "
					+ " where OVER_TIME_WORK_ID = ?";
			Optional<KrcmtCalcSetOverTime> overtimeCalc= Optional.empty();
			try (PreparedStatement stmt = this.connection().prepareStatement(sql3)) {
				stmt.setString(1 , StringUtils.rightPad(calc.overTimeWorkId, 36));
				overtimeCalc = new NtsResultSet(stmt.executeQuery()).getSingle(rec -> KrcmtCalcSetOverTime.MAPPER.toEntity(rec));
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			return calc.toDomain(flexCalc.get(), holidayCalc.get(), overtimeCalc.get());
		}
		return null;
	}

	@Override
	public void update(CalAttrOfDailyPerformance domain) {
		KrcdtDayInfoCalc calc = this.queryProxy()
				.find(new KrcstDaiCalculationSetPK(domain.getEmployeeId(), domain.getYmd()),
						KrcdtDayInfoCalc.class)
				.orElse(null);
		if (calc == null) {
			this.add(domain);
		} else {
			KrcmtCalcSetFlex flexCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.flexExcessTimeId, 36), KrcmtCalcSetFlex.class).orElse(null);
			KrcmtCalcSetHdWork holidayCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.holWorkTimeId, 36), KrcmtCalcSetHdWork.class).orElse(null);
			KrcmtCalcSetOverTime overtimeCalc = this.queryProxy()
					.find(StringUtils.rightPad(calc.overTimeWorkId, 36), KrcmtCalcSetOverTime.class).orElse(null);
			if (domain.getCalcategory().getRasingSalarySetting() != null) {
				calc.bonusPayNormalCalSet = domain.getCalcategory().getRasingSalarySetting().isRaisingSalaryCalcAtr() ? 1 : 0;
				calc.bonusPaySpeCalSet = domain.getCalcategory().getRasingSalarySetting().isSpecificRaisingSalaryCalcAtr() ? 1 : 0;
			}
			if (domain.getCalcategory().getDivergenceTime() != null) {
				calc.divergenceTime = domain.getCalcategory().getDivergenceTime().getDivergenceTime().value;
			}
			if (domain.getCalcategory().getLeaveEarlySetting() != null) {
				calc.leaveEarlySet = domain.getCalcategory().getLeaveEarlySetting().isLeaveEarly() ? 1 : 0;
				calc.leaveLateSet = domain.getCalcategory().getLeaveEarlySetting().isLate() ? 1 : 0;
			}
			setFlexCalcSetting(domain.getCalcategory().getFlexExcessTime().getFlexOtTime(), flexCalc);
			setHolidayCalcSetting(domain.getCalcategory().getHolidayTimeSetting(), holidayCalc);
			setOvertimeCalcSetting(domain.getCalcategory().getOvertimeSetting(), overtimeCalc);
			commandProxy().update(flexCalc);
			commandProxy().update(holidayCalc);
			commandProxy().update(overtimeCalc);
			commandProxy().update(calc);
//			this.getEntityManager().flush();
		}
	}

	@Override
	public void add(CalAttrOfDailyPerformance domain) {
		KrcmtCalcSetFlex flexCalc = KrcmtCalcSetFlex.toEntity(domain.getCalcategory().getFlexExcessTime(), IdentifierUtil.randomUniqueId());
		KrcmtCalcSetHdWork holidayCalc = KrcmtCalcSetHdWork.toEntity(domain.getCalcategory().getHolidayTimeSetting(), IdentifierUtil.randomUniqueId());
		KrcmtCalcSetOverTime overtimeCalc = KrcmtCalcSetOverTime.toEntity(domain.getCalcategory().getOvertimeSetting(), IdentifierUtil.randomUniqueId());
		KrcdtDayInfoCalc calcSet = KrcdtDayInfoCalc.toEntity(domain, flexCalc.flexExcessTimeId, holidayCalc.holWorkTimeId, overtimeCalc.overTimeWorkId);
		commandProxy().insert(flexCalc);
		commandProxy().insert(holidayCalc);
		commandProxy().insert(overtimeCalc);
		commandProxy().insert(calcSet);
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<CalAttrOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate) {
		List<CalAttrOfDailyPerformance> result = new ArrayList<>();
		
		
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQuery(baseDate, empIds));
		});
		return result;
	}

	@SneakyThrows
	private List<CalAttrOfDailyPerformance> internalQuery(DatePeriod baseDate, List<String> empIds) {
		String subEmp = NtsStatement.In.createParamsString(empIds);
		StringBuilder query = new StringBuilder("SELECT * FROM KRCDT_DAY_INFO_CALC c  ");
		query.append(" LEFT JOIN KRCMT_CALC_SET_OVER_TIME ot ON c.OVER_TIME_WORK_ID = ot.OVER_TIME_WORK_ID  ");
		query.append(" LEFT JOIN KRCMT_CALC_SET_FLEX f ON c.FLEX_EXCESS_TIME_ID = f.FLEX_EXCESS_TIME_ID  ");
		query.append(" LEFT JOIN KRCMT_CALC_SET_HD_WORK ho ON c.HOL_WORK_TIME_ID = ho.HOL_WORK_TIME_ID ");
		query.append(" WHERE c.YMD <= ? AND c.YMD >= ? ");
		query.append(" AND c.SID IN (" + subEmp + ")");
		try (val stmt = this.connection().prepareStatement(query.toString())){
			stmt.setDate(1, Date.valueOf(baseDate.end().localDate()));
			stmt.setDate(2, Date.valueOf(baseDate.start().localDate()));
			for (int i = 0; i < empIds.size(); i++) {
				stmt.setString(i + 3, empIds.get(i));
			}
			return new NtsResultSet(stmt.executeQuery())
					.getList(rec -> KrcdtDayInfoCalc.MAPPER.toEntity(rec).toDomain(
							KrcmtCalcSetFlex.MAPPER.toEntity(rec),
							KrcmtCalcSetHdWork.MAPPER.toEntity(rec),
							KrcmtCalcSetOverTime.MAPPER.toEntity(rec)));
		}
	}

	private void setOvertimeCalcSetting(AutoCalOvertimeSetting domain, KrcmtCalcSetOverTime overtimeCalc) {
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

	private void setFlexCalcSetting(AutoCalSetting domain, KrcmtCalcSetFlex flexCalc) {
		if (domain != null) {
			flexCalc.flexExcessLimitSet = domain.getUpLimitORtSet() == null ? 0 : domain.getUpLimitORtSet().value;
			flexCalc.flexExcessTimeCalAtr = domain.getCalAtr() == null ? 0 : domain.getCalAtr().value;
		}
	}

	private void setHolidayCalcSetting(AutoCalRestTimeSetting domain, KrcmtCalcSetHdWork holidayCalc) {
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

	@Override
	public void deleteByKey(String employeeId, GeneralDate baseDate) {
		
		this.queryProxy().find(new KrcstDaiCalculationSetPK(employeeId, baseDate), KrcdtDayInfoCalc.class).ifPresent(entity -> {
			this.commandProxy().remove(entity);
			this.queryProxy().find(StringUtils.rightPad(entity.flexExcessTimeId, 36), KrcmtCalcSetFlex.class).ifPresent(e -> {
						this.commandProxy().remove(e);
					});
			this.queryProxy().find(StringUtils.rightPad(entity.holWorkTimeId, 36), KrcmtCalcSetHdWork.class).ifPresent(e -> {
						this.commandProxy().remove(e);
					});
			this.queryProxy().find(StringUtils.rightPad(entity.overTimeWorkId, 36), KrcmtCalcSetOverTime.class).ifPresent(e -> {
						this.commandProxy().remove(e);
					});
		});
		
//		Connection con = this.getEntityManager().unwrap(Connection.class);
//		String sqlQuery = "Delete From KRCDT_DAY_INFO_CALC Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + baseDate + "'" ;
//		try {
//			con.createStatement().executeUpdate(sqlQuery);
//			workInfo.dirtying(employeeId, baseDate);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
		
//		this.getEntityManager().createQuery(REMOVE_BY_KEY).setParameter("employeeId", employeeId)
//				.setParameter("ymd", baseDate).executeUpdate();
//		this.getEntityManager().flush();
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @SneakyThrows
	@Override
	public List<CalAttrOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<String> employeeIds = param.keySet().stream().collect(Collectors.toList());
		List<GeneralDate> dates = param.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
		List<CalAttrOfDailyPerformance> result = new ArrayList<>();

		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQueryMap(dates, empIds));
		});
		return result;
	}

    @SneakyThrows
    private List<CalAttrOfDailyPerformance> internalQueryMap(List<GeneralDate> dates, List<String> employeeIds){
    	String subEmp = NtsStatement.In.createParamsString(employeeIds);
    	String subDate = NtsStatement.In.createParamsString(dates);
		StringBuilder query = new StringBuilder("SELECT * FROM KRCDT_DAY_INFO_CALC c  ");
		query.append(" LEFT JOIN KRCMT_CALC_SET_OVER_TIME ot ON c.OVER_TIME_WORK_ID = ot.OVER_TIME_WORK_ID  ");
		query.append(" LEFT JOIN KRCMT_CALC_SET_FLEX f ON c.FLEX_EXCESS_TIME_ID = f.FLEX_EXCESS_TIME_ID  ");
		query.append(" LEFT JOIN KRCMT_CALC_SET_HD_WORK ho ON c.HOL_WORK_TIME_ID = ho.HOL_WORK_TIME_ID ");
		query.append(" WHERE c.SID IN (" + subEmp + ")");
		query.append(" AND c.YMD IN (" + subDate  + ")");
		try (val stmt = this.connection().prepareStatement(query.toString())){
			for (int i = 0; i < employeeIds.size(); i++) {
				stmt.setString(i + 1, employeeIds.get(i));
			}
			
			for (int i = 0; i < dates.size(); i++) {
				stmt.setDate(1 + i + employeeIds.size(), Date.valueOf(dates.get(i).localDate()));
			}
			return new NtsResultSet(stmt.executeQuery())
					.getList(rec -> KrcdtDayInfoCalc.MAPPER.toEntity(rec).toDomain(
							KrcmtCalcSetFlex.MAPPER.toEntity(rec),
							KrcmtCalcSetHdWork.MAPPER.toEntity(rec),
							KrcmtCalcSetOverTime.MAPPER.toEntity(rec)));
		}
    }
}
