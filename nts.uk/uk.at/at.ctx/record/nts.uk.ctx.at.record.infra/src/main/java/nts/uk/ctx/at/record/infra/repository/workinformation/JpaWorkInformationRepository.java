package nts.uk.ctx.at.record.infra.repository.workinformation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.SneakyThrows;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDaiPerWorkInfoPK;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDayInfoPerWork;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDayTsAtdSche;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtWorkScheduleTimePK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.FuriClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NumberOfDaySuspension;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;

/**
 *
 * @author lamdt
 *
 */
@Stateless
public class JpaWorkInformationRepository extends JpaRepository implements WorkInformationRepository {

	private static final String FIND_BY_EMPLOYEE_ID = "SELECT a FROM KrcdtDayInfoPerWork a "
			+ " WHERE a.krcdtDaiPerWorkInfoPK.employeeId = :employeeId ";

	private String FIND_BY_WORKTYPE_PERIOD = "SELECT c.krcdtDaiPerWorkInfoPK.ymd" + " FROM KrcdtDayInfoPerWork c"
			+ " WHERE c.krcdtDaiPerWorkInfoPK.ymd >= :startDate" + " AND c.krcdtDaiPerWorkInfoPK.ymd <= :endDate"
			+ " AND c.recordWorkWorktypeCode = :workTypeCode" + " AND c.krcdtDaiPerWorkInfoPK.employeeId = :employeeId";

	private String FIND_BY_LIST_DATE = "SELECT c " + " FROM KrcdtDayInfoPerWork c"
			+ " WHERE c.krcdtDaiPerWorkInfoPK.ymd IN :dates" + " AND c.krcdtDaiPerWorkInfoPK.employeeId = :employeeId";

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd) {

		List<KrcdtDayTsAtdSche> scheduleTimes;
		try (PreparedStatement sqlSchedule = this.connection().prepareStatement(
				"select * from KRCDT_DAY_TS_ATD_SCHE"
				+ " where SID = ? and YMD = ?")) {
			sqlSchedule.setString(1, employeeId);
			sqlSchedule.setDate(2, Date.valueOf(ymd.localDate()));
			scheduleTimes = new NtsResultSet(sqlSchedule.executeQuery()).getList(rec -> {
				KrcdtDayTsAtdSche entity = new KrcdtDayTsAtdSche();
				entity.krcdtWorkScheduleTimePK = new KrcdtWorkScheduleTimePK(
						rec.getString("SID"),
						rec.getGeneralDate("YMD"),
						rec.getInt("WORK_NO"));
				entity.attendance = rec.getInt("ATTENDANCE");
				entity.leaveWork = rec.getInt("LEAVE_WORK");
				return entity;
			});
		};

		Optional<KrcdtDayInfoPerWork> workInfo;
		try (PreparedStatement sqlWorkInfo = this.connection().prepareStatement(
				"select * from KRCDT_DAY_INFO_PER_WORK"
				+ " where SID = ? and YMD = ?")) {
			sqlWorkInfo.setString(1, employeeId);
			sqlWorkInfo.setDate(2, Date.valueOf(ymd.localDate()));

			 workInfo = new NtsResultSet(sqlWorkInfo.executeQuery()).getSingle(rec -> {
				KrcdtDayInfoPerWork entity = new KrcdtDayInfoPerWork();
				entity.krcdtDaiPerWorkInfoPK = new KrcdtDaiPerWorkInfoPK(
						rec.getString("SID"), rec.getGeneralDate("YMD"));
				entity.recordWorkWorktypeCode = rec.getString("RECORD_WORK_WORKTYPE_CODE");
				entity.recordWorkWorktimeCode = rec.getString("RECORD_WORK_WORKTIME_CODE");
				entity.calculationState = rec.getInt("CALCULATION_STATE");
				entity.goStraightAttribute = rec.getInt("GO_STRAIGHT_ATR");
				entity.backStraightAttribute = rec.getInt("BACK_STRAIGHT_ATR");
				entity.dayOfWeek = rec.getInt("DAY_OF_WEEK");
				entity.treatAsSubstituteAtr = rec.getInt("TREAT_AS_SUBSTITUTE_ATR");
				entity.treatAsSubstituteDays = rec.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
				entity.scheduleTimes = scheduleTimes;
				entity.version = rec.getLong("EXCLUS_VER");
				return entity;
			});
		}

		return workInfo.map(c -> c.toDomain());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkInfoOfDailyPerformance> findByEmployeeId(String employeeId) {
		return this.queryProxy().query(FIND_BY_EMPLOYEE_ID, KrcdtDayInfoPerWork.class)
				.setParameter("employeeId", employeeId).getList(c -> c.toDomain());
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {

		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAY_TS_ATD_SCHE Where SID = " + "'" + employeeId + "'" + " and YMD = "
				+ "'" + ymd + "'";
		String perWorkInfo = "Delete From KRCDT_DAY_INFO_PER_WORK Where SID = " + "'" + employeeId + "'" + " and YMD = "
				+ "'" + ymd + "'";
		try {
			con.createStatement().executeUpdate(sqlQuery);
			con.createStatement().executeUpdate(perWorkInfo);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		// this.getEntityManager().createQuery(DEL_BY_KEY_ID).setParameter("employeeId",
		// employeeId).setParameter("ymd", ymd)
		// .executeUpdate();
		//
		// this.getEntityManager().createQuery(DEL_BY_KEY).setParameter("employeeId",
		// employeeId).setParameter("ymd", ymd)
		// .executeUpdate();
		// this.getEntityManager().flush();
	}

	@Override
	@SneakyThrows
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {

		List<KrcdtDayTsAtdSche> scheduleTimes = new ArrayList<>();

		try (PreparedStatement sqlSchedule = this.connection().prepareStatement(
				"select * from KRCDT_DAY_TS_ATD_SCHE where SID = ? and YMD >= ? and YMD <= ? order by YMD ")) {
			sqlSchedule.setString(1, employeeId);
			sqlSchedule.setDate(2, Date.valueOf(datePeriod.start().localDate()));
			sqlSchedule.setDate(3, Date.valueOf(datePeriod.end().localDate()));
			scheduleTimes = new NtsResultSet(sqlSchedule.executeQuery()).getList(rec -> {
				KrcdtDayTsAtdSche entity = new KrcdtDayTsAtdSche();
				entity.krcdtWorkScheduleTimePK = new KrcdtWorkScheduleTimePK(rec.getString("SID"),
						rec.getGeneralDate("YMD"), rec.getInt("WORK_NO"));
				entity.attendance = rec.getInt("ATTENDANCE");
				entity.leaveWork = rec.getInt("LEAVE_WORK");
				return entity;
			});
		}

		List<KrcdtDayTsAtdSche> scheduleTimess = scheduleTimes;
		try (PreparedStatement statement = this.connection()
				.prepareStatement(" select * from KRCDT_DAY_INFO_PER_WORK h " + " where SID = ? " + " and YMD <= ? "
						+ " and YMD >= ? " + " order by YMD ")) {

			statement.setString(1, employeeId);
			statement.setDate(2, Date.valueOf(datePeriod.end().localDate()));
			statement.setDate(3, Date.valueOf(datePeriod.start().localDate()));

			return new NtsResultSet(statement.executeQuery()).getList(rec -> {
				GeneralDate ymd = rec.getGeneralDate("YMD");
				int calcState = rec.getInt("CALCULATION_STATE"), goStraight = rec.getInt("GO_STRAIGHT_ATR"),
						backStraight = rec.getInt("BACK_STRAIGHT_ATR"), dayOfWeek = rec.getInt("DAY_OF_WEEK");
				Integer treatAsSubstituteAtr = rec.getInt("TREAT_AS_SUBSTITUTE_ATR");
				Double treatAsSubstituteDays = rec.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
				WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(employeeId,
						new WorkInformation(rec.getString("RECORD_WORK_WORKTYPE_CODE"), rec.getString("RECORD_WORK_WORKTIME_CODE")),
						calcState == CalculationState.Calculated.value ? CalculationState.Calculated
								: CalculationState.No_Calculated,
						goStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use,
						backStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use, ymd,
						EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
						scheduleTimess.stream().filter(c -> c.krcdtWorkScheduleTimePK.ymd.equals(ymd))
								.map(c -> c.toDomain()).collect(Collectors.toList()),
						(treatAsSubstituteAtr != null && treatAsSubstituteDays != null)
								? Optional.of(new NumberOfDaySuspension(new UsedDays(treatAsSubstituteDays),
										EnumAdaptor.valueOf(treatAsSubstituteAtr, FuriClassifi.class)))
								: Optional.empty());
				domain.setVersion(rec.getLong("EXCLUS_VER"));
				return domain;
			});
		}
		// return this.queryProxy().query(FIND_BY_PERIOD_ORDER_BY_YMD,
		// KrcdtDayInfoPerWork.class)
		// .setParameter("employeeId", employeeId).setParameter("startDate",
		// datePeriod.start())
		// .setParameter("endDate", datePeriod.end()).getList(f ->
		// f.toDomain());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmdDesc(String employeeId, DatePeriod datePeriod) {
		List<KrcdtDayTsAtdSche> scheduleTimes;
		try (PreparedStatement sqlSchedule = this.connection().prepareStatement(
					"select * from KRCDT_DAY_TS_ATD_SCHE where SID = ? and YMD >= ? and YMD <= ? order by YMD desc")) {
			sqlSchedule.setString(1, employeeId);
			sqlSchedule.setDate(2, Date.valueOf(datePeriod.start().localDate()));
			sqlSchedule.setDate(3, Date.valueOf(datePeriod.end().localDate()));
			scheduleTimes = new NtsResultSet(sqlSchedule.executeQuery()).getList(rec -> {
				KrcdtDayTsAtdSche entity = new KrcdtDayTsAtdSche();
				entity.krcdtWorkScheduleTimePK = new KrcdtWorkScheduleTimePK(rec.getString("SID"),
						rec.getGeneralDate("YMD"), rec.getInt("WORK_NO"));
				entity.attendance = rec.getInt("ATTENDANCE");
				entity.leaveWork = rec.getInt("LEAVE_WORK");
				return entity;
			});
		}

		try (PreparedStatement sqlWorkInfo = this.connection().prepareStatement(
					"select * from KRCDT_DAY_INFO_PER_WORK where SID = ? and YMD >= ? and YMD <= ? order by YMD desc ")) {
			sqlWorkInfo.setString(1, employeeId);
			sqlWorkInfo.setDate(2, Date.valueOf(datePeriod.start().localDate()));
			sqlWorkInfo.setDate(3, Date.valueOf(datePeriod.end().localDate()));

			return new NtsResultSet(sqlWorkInfo.executeQuery()).getList(rec -> {
				GeneralDate ymd = rec.getGeneralDate("YMD");
				int calcState = rec.getInt("CALCULATION_STATE"), goStraight = rec.getInt("GO_STRAIGHT_ATR"),
						backStraight = rec.getInt("BACK_STRAIGHT_ATR"), dayOfWeek = rec.getInt("DAY_OF_WEEK");
				Integer treatAsSubstituteAtr = rec.getInt("TREAT_AS_SUBSTITUTE_ATR");
				Double treatAsSubstituteDays = rec.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
				WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(employeeId,
						new WorkInformation(rec.getString("RECORD_WORK_WORKTYPE_CODE"), rec.getString("RECORD_WORK_WORKTIME_CODE")),
						calcState == CalculationState.Calculated.value ? CalculationState.Calculated
								: CalculationState.No_Calculated,
						goStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use,
						backStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use, ymd,
						EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
						scheduleTimes.stream().filter(c -> c.krcdtWorkScheduleTimePK.ymd.equals(ymd))
								.map(c -> c.toDomain()).collect(Collectors.toList()),
						(treatAsSubstituteAtr != null && treatAsSubstituteDays != null)
								? Optional.of(new NumberOfDaySuspension(new UsedDays(treatAsSubstituteDays),
										EnumAdaptor.valueOf(treatAsSubstituteAtr, FuriClassifi.class)))
								: Optional.empty());
				domain.setVersion(rec.getLong("EXCLUS_VER"));
				return domain;
			});
		}
	}

	@Override
	public void updateByKey(WorkInfoOfDailyPerformance domain) {

		if (domain != null) {
			KrcdtDayInfoPerWork data = this.findKrcdtDaiPerWorkInfoWithJdbc(domain.getEmployeeId(), domain.getYmd())
					.orElseGet(() -> new KrcdtDayInfoPerWork(
							new KrcdtDaiPerWorkInfoPK(domain.getEmployeeId(), domain.getYmd())));

			internalUpdate(domain, data);
		}
	}

	@SneakyThrows
	private void internalUpdate(WorkInfoOfDailyPerformance domain, KrcdtDayInfoPerWork data) {
		if (domain.getWorkInformation().getRecordInfo() != null) {
			WorkInformation record = domain.getWorkInformation().getRecordInfo();

			data.recordWorkWorktypeCode = record.getWorkTypeCode().v();
			data.recordWorkWorktimeCode = record.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null);
		}

		data.calculationState = domain.getWorkInformation().getCalculationState().value;
		data.backStraightAttribute = domain.getWorkInformation().getBackStraightAtr().value;
		data.goStraightAttribute = domain.getWorkInformation().getGoStraightAtr().value;
		data.dayOfWeek = domain.getWorkInformation().getDayOfWeek().value;
		data.treatAsSubstituteAtr = domain.getWorkInformation().getNumberDaySuspension().map(x -> x.getClassifiction().value).orElse(null);
		data.treatAsSubstituteDays = domain.getWorkInformation().getNumberDaySuspension().map(x -> x.getDays().v()).orElse(null);
		data.version = domain.getVersion();

		if(domain.getWorkInformation().getScheduleTimeSheets().isEmpty()) {

			data.scheduleTimes = new ArrayList<>();
		} else {
			if (data.scheduleTimes == null || data.scheduleTimes.isEmpty()) {
				data.scheduleTimes = domain.getWorkInformation().getScheduleTimeSheets().stream()
						.map(c -> KrcdtDayTsAtdSche.toEntity(domain.getEmployeeId(), domain.getYmd(), c))
						.collect(Collectors.toList());
			} else {
				val newSche = domain.getWorkInformation().getScheduleTimeSheets();
				for(ScheduleTimeSheet stNew : newSche) {
					data.scheduleTimes.stream().forEach(stOld -> {
						if(stOld.krcdtWorkScheduleTimePK.workNo == stNew.getWorkNo().v()) {
							stOld.attendance = stNew.getAttendance().valueAsMinutes();
							stOld.leaveWork = stNew.getLeaveWork().valueAsMinutes();
						}
					});
				}
				if(newSche.size() >  data.scheduleTimes.size()) {
					val oldNos = data.scheduleTimes.stream().map(c -> c.krcdtWorkScheduleTimePK.workNo)
															.collect(Collectors.toList());

					val newEnt = newSche.stream().filter(c -> !oldNos.contains(c.getWorkNo().v()))
										.map(c -> KrcdtDayTsAtdSche.toEntity(domain.getEmployeeId(), domain.getYmd(), c))
										.collect(Collectors.toList());

					data.scheduleTimes.addAll(newEnt);
				} else if (newSche.size() <  data.scheduleTimes.size()) {
					val newNos = newSche.stream().map(c -> c.getWorkNo().v())
										.collect(Collectors.toList());
					data.scheduleTimes.removeIf(x -> !newNos.contains(x.krcdtWorkScheduleTimePK.workNo));
				}

			}
		}
		this.commandProxy().update(data);
//		this.commandProxy().update(data.scheduleTimes);

	}

	@SneakyThrows
	private Optional<KrcdtDayInfoPerWork> findKrcdtDaiPerWorkInfoWithJdbc(String employeeId, GeneralDate ymd) {

		try (PreparedStatement stmtFindById = this.connection().prepareStatement(
				"select * from KRCDT_DAY_INFO_PER_WORK"
				+ " where SID = ? and YMD = ?")) {
			stmtFindById.setString(1, employeeId);
			stmtFindById.setDate(2, Date.valueOf(ymd.toLocalDate()));

			return new NtsResultSet(stmtFindById.executeQuery()).getSingle(rec -> {
				return convertToEntity(rec);
			});
		}
	}

	@SneakyThrows
	private KrcdtDayInfoPerWork convertToEntity(NtsResultRecord rec) {
		KrcdtDaiPerWorkInfoPK pk = new KrcdtDaiPerWorkInfoPK();
		pk.employeeId = rec.getString("SID");
		pk.ymd = rec.getGeneralDate("YMD");

		KrcdtDayInfoPerWork entity = new KrcdtDayInfoPerWork();
		entity.krcdtDaiPerWorkInfoPK = pk;
		entity.recordWorkWorktypeCode = rec.getString("RECORD_WORK_WORKTYPE_CODE");
		entity.recordWorkWorktimeCode = rec.getString("RECORD_WORK_WORKTIME_CODE");
		entity.calculationState = rec.getInt("CALCULATION_STATE");
		entity.goStraightAttribute = rec.getInt("GO_STRAIGHT_ATR");
		entity.backStraightAttribute = rec.getInt("BACK_STRAIGHT_ATR");
		entity.dayOfWeek = rec.getInt("DAY_OF_WEEK");
		entity.treatAsSubstituteAtr = rec.getInt("TREAT_AS_SUBSTITUTE_ATR");
		entity.treatAsSubstituteDays = rec.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
		entity.version = rec.getLong("EXCLUS_VER");
		try (PreparedStatement stmtSche = this.connection().prepareStatement(
					"select * from KRCDT_DAY_TS_ATD_SCHE"
					+ " where SID = ? and YMD = ?")) {
			stmtSche.setString(1, pk.employeeId);
			stmtSche.setDate(2, Date.valueOf(pk.ymd.toLocalDate()));
			entity.scheduleTimes = new NtsResultSet(stmtSche.executeQuery()).getList(rs -> {
				KrcdtWorkScheduleTimePK pks = new KrcdtWorkScheduleTimePK();
				pks.employeeId = rs.getString("SID");
				pks.ymd = rs.getGeneralDate("YMD");
				pks.workNo = rs.getInt("WORK_NO");

				KrcdtDayTsAtdSche es = new KrcdtDayTsAtdSche();
				es.krcdtWorkScheduleTimePK = pks;
				es.attendance = rs.getInt("ATTENDANCE");
				es.leaveWork = rs.getInt("LEAVE_WORK");

				return es;
			});
		}

		return entity;
	}

	@Override
	public void insert(WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		this.commandProxy().insert(KrcdtDayInfoPerWork.toEntity(workInfoOfDailyPerformance));
	}

	@Override
	public void updateByKeyFlush(WorkInfoOfDailyPerformance workInfoOfDailyPerformance) {
		this.updateByKey(workInfoOfDailyPerformance);
		this.getEntityManager().flush();

	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkInfoOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<Object[]> result = new ArrayList<>();
		StringBuilder query = new StringBuilder(
				"SELECT af, c from KrcdtDayInfoPerWork af LEFT JOIN af.scheduleTimes c ");
		query.append(" WHERE af.krcdtDaiPerWorkInfoPK.employeeId IN :employeeId ");
		query.append(" AND af.krcdtDaiPerWorkInfoPK.ymd IN :date");
		TypedQueryWrapper<Object[]> tQuery = this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
					.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
					.getList().stream().filter(c -> {
						KrcdtDayInfoPerWork af = (KrcdtDayInfoPerWork) c[0];
						return p.get(af.krcdtDaiPerWorkInfoPK.employeeId).contains(af.krcdtDaiPerWorkInfoPK.ymd);
					}).collect(Collectors.toList()));
		});
		return toDomainFromJoin(result);
	}

	private List<WorkInfoOfDailyPerformance> toDomainFromJoin(List<Object[]> result) {
		return result.stream()
				.collect(Collectors.groupingBy(c1 -> c1[0],
						Collectors.collectingAndThen(Collectors.toList(),
								list -> list.stream().filter(c -> c[1] != null).map(c -> (KrcdtDayTsAtdSche) c[1])
										.collect(Collectors.toList()))))
				.entrySet().stream()
				.map(e -> KrcdtDayInfoPerWork.toDomain((KrcdtDayInfoPerWork) e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkInfoOfDailyPerformance> findByPeriodOrderByYmdAndEmps(List<String> employeeIds,
			DatePeriod datePeriod) {
		if(employeeIds.isEmpty())
			return Collections.emptyList();

		List<WorkInfoOfDailyPerformance> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(internalQuery(datePeriod, subList));
		});
		resultList.sort(Comparator.comparing(WorkInfoOfDailyPerformance::getYmd));
		return resultList;
	}

	@SneakyThrows
	private List<WorkInfoOfDailyPerformance> internalQuery(DatePeriod datePeriod, List<String> subList) {
		String subIn = NtsStatement.In.createParamsString(subList);

		Map<String, Map<GeneralDate, List<ScheduleTimeSheet>>> scheTimes = new HashMap<>();
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAY_TS_ATD_SCHE WHERE SID IN (" + subIn + ") AND YMD >= ? AND YMD <= ?")){
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 1, subList.get(i));
			}
			stmt.setDate(1 + subList.size(), Date.valueOf(datePeriod.start().localDate()));
			stmt.setDate(2 + subList.size(), Date.valueOf(datePeriod.end().localDate()));
			new NtsResultSet(stmt.executeQuery()).getList(c -> {
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				if(!scheTimes.containsKey(sid)){
					scheTimes.put(sid, new HashMap<>());
				}
				if(!scheTimes.get(sid).containsKey(ymd)) {
					scheTimes.get(sid).put(ymd, new ArrayList<>());
				}
				getScheduleTime(scheTimes, sid, ymd).add(new ScheduleTimeSheet(c.getInt("WORK_NO"),
						c.getInt("ATTENDANCE"), c.getInt("LEAVE_WORK")));
				return null;
			});
		};
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAY_INFO_PER_WORK WHERE SID IN (" + subIn + ") AND YMD >= ? AND YMD <= ?")){
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 1, subList.get(i));
			}
			stmt.setDate(1 + subList.size(), Date.valueOf(datePeriod.start().localDate()));
			stmt.setDate(2 + subList.size(), Date.valueOf(datePeriod.end().localDate()));
			return new NtsResultSet(stmt.executeQuery()).getList(c -> {
				Integer calcState = c.getInt("CALCULATION_STATE"), goStraight = c.getInt("GO_STRAIGHT_ATR"),
						backStraight = c.getInt("BACK_STRAIGHT_ATR"), dayOfWeek = c.getInt("DAY_OF_WEEK");
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				Integer treatAsSubstituteAtr = c.getInt("TREAT_AS_SUBSTITUTE_ATR");
				Double treatAsSubstituteDays = c.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
				WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(sid,
						new WorkInformation(c.getString("RECORD_WORK_WORKTYPE_CODE"), c.getString("RECORD_WORK_WORKTIME_CODE")),
						calcState == null ? null : EnumAdaptor.valueOf(calcState, CalculationState.class),
						goStraight == null ? null : EnumAdaptor.valueOf(goStraight, NotUseAttribute.class),
						backStraight == null ? null : EnumAdaptor.valueOf(backStraight, NotUseAttribute.class),
						ymd, dayOfWeek == null ? null : EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
						getScheduleTime(scheTimes, sid, ymd),
						(treatAsSubstituteAtr != null && treatAsSubstituteDays != null)
						? Optional.of(new NumberOfDaySuspension(new UsedDays(treatAsSubstituteDays),
								EnumAdaptor.valueOf(treatAsSubstituteAtr, FuriClassifi.class)))
						: Optional.empty()
						);
				domain.setVersion(c.getLong("EXCLUS_VER"));
				return domain;
			});
		}
	}

	private List<ScheduleTimeSheet> getScheduleTime(Map<String, Map<GeneralDate, List<ScheduleTimeSheet>>> scheTimes,
			String sid, GeneralDate ymd) {
		if(scheTimes.containsKey(sid)){
			if(scheTimes.get(sid).containsKey(ymd)){
				return scheTimes.get(sid).get(ymd);
			}
		}
		return new ArrayList<>();
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<GeneralDate> getByWorkTypeAndDatePeriod(String employeeId, String workTypeCode, DatePeriod period) {
		List<GeneralDate> lstOutput = this.queryProxy()
				.query(FIND_BY_WORKTYPE_PERIOD, GeneralDate.class)
				.setParameter("startDate", period.start())
				.setParameter("endDate", period.end())
				.setParameter("workTypeCode", workTypeCode)
				.setParameter("employeeId", employeeId)
				.getList();
		return lstOutput;
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public List<WorkInfoOfDailyPerformance> findByListDate(String employeeId, List<GeneralDate> dates) {
		if(dates.isEmpty())
			return Collections.emptyList();

		List<WorkInfoOfDailyPerformance> lstOutput = new ArrayList<>();
		CollectionUtil.split(dates, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String subIn = NtsStatement.In.createParamsString(subList);
			Map<String, Map<GeneralDate, List<ScheduleTimeSheet>>> scheTimes = new HashMap<>();
			try(val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAY_TS_ATD_SCHE "
					+ "WHERE YMD IN (" + subIn + ") "
					+ "AND SID = ?")){
				for (int i = 0; i < subList.size(); i++) {
					stmt.setDate(i + 1, Date.valueOf(subList.get(i).localDate()));
				}
				stmt.setString(1  + subList.size(), employeeId);

				new NtsResultSet(stmt.executeQuery()).getList(c -> {
					String sid = c.getString("SID");
					GeneralDate ymd = c.getGeneralDate("YMD");
					if(!scheTimes.containsKey(sid)){
						scheTimes.put(sid, new HashMap<>());
					}
					if(!scheTimes.get(sid).containsKey(ymd)) {
						scheTimes.get(sid).put(ymd, new ArrayList<>());
					}
					getScheduleTime(scheTimes, sid, ymd).add(new ScheduleTimeSheet(c.getInt("WORK_NO"),
							c.getInt("ATTENDANCE"), c.getInt("LEAVE_WORK")));
					return null;
				});
			} catch (SQLException e1) {
				throw new RuntimeException(e1);
			}
			try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAY_INFO_PER_WORK "
					+ "WHERE YMD IN (" + subIn + ") AND SID = ?")){
				for (int i = 0; i < subList.size(); i++) {
					stmt.setDate(i + 1, Date.valueOf(subList.get(i).localDate()));
				}
				stmt.setString(1 + subList.size(), employeeId);
				List<WorkInfoOfDailyPerformance> lstOuputTmp =  new NtsResultSet(stmt.executeQuery()).getList(c -> {
					Integer calcState = c.getInt("CALCULATION_STATE"), goStraight = c.getInt("GO_STRAIGHT_ATR"),
							backStraight = c.getInt("BACK_STRAIGHT_ATR"), dayOfWeek = c.getInt("DAY_OF_WEEK");
					String sid = c.getString("SID");
					GeneralDate ymd = c.getGeneralDate("YMD");
					Integer treatAsSubstituteAtr = c.getInt("TREAT_AS_SUBSTITUTE_ATR");
					Double treatAsSubstituteDays = c.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
					WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(sid,
							new WorkInformation(c.getString("RECORD_WORK_WORKTYPE_CODE"), c.getString("RECORD_WORK_WORKTIME_CODE")),
							calcState == null ? null : EnumAdaptor.valueOf(calcState, CalculationState.class),
							goStraight == null ? null : EnumAdaptor.valueOf(goStraight, NotUseAttribute.class),
							backStraight == null ? null : EnumAdaptor.valueOf(backStraight, NotUseAttribute.class),
							ymd, dayOfWeek == null ? null : EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
							getScheduleTime(scheTimes, sid, ymd),
							(treatAsSubstituteAtr != null && treatAsSubstituteDays != null)
							? Optional.of(new NumberOfDaySuspension(new UsedDays(treatAsSubstituteDays),
									EnumAdaptor.valueOf(treatAsSubstituteAtr, FuriClassifi.class)))
							: Optional.empty());
					domain.setVersion(c.getLong("EXCLUS_VER"));
					return domain;
				});
				lstOutput.addAll(lstOuputTmp);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return lstOutput;
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public long getVer(String employeeId, GeneralDate date) {
		try (PreparedStatement stmtFindById = this.connection().prepareStatement(
				"select EXCLUS_VER from KRCDT_DAY_INFO_PER_WORK"
				+ " where SID = ? and YMD = ?")) {
			stmtFindById.setString(1, employeeId);
			stmtFindById.setDate(2, Date.valueOf(date.toLocalDate()));

			return new NtsResultSet(stmtFindById.executeQuery()).getSingle(rec -> {
				return rec.getLong(1);
			}).orElse(0L);
		}
	}

	@Override
	public void dirtying(String employeeId, GeneralDate date){
		this.queryProxy().find(new KrcdtDaiPerWorkInfoPK(employeeId, date), KrcdtDayInfoPerWork.class).ifPresent(entity -> {
			entity.dirtying();
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void dirtying(String employeeId, GeneralDate date, long version){
		this.queryProxy().find(new KrcdtDaiPerWorkInfoPK(employeeId, date), KrcdtDayInfoPerWork.class).ifPresent(entity -> {
			entity.dirtying();
			entity.version = version;
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void dirtying(List<String> employeeId, List<GeneralDate> date) {
		finds(employeeId, date).forEach(entity -> {
			entity.dirtying();
			this.commandProxy().update(entity);
		});
	}

	private List<KrcdtDayInfoPerWork> finds(List<String> employeeIds, List<GeneralDate> ymds) {
		List<KrcdtDayInfoPerWork> result = new ArrayList<>();
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a FROM KrcdtDayInfoPerWork a");
		builderString.append(" WHERE a.krcdtDaiPerWorkInfoPK.employeeId IN :employeeIds");
		builderString.append(" AND a.krcdtDaiPerWorkInfoPK.ymd IN :processingYmds");
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, sublistEmployeeIds -> {
			CollectionUtil.split(ymds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, sublistYMDs -> {
				result.addAll(this.getEntityManager().createQuery(builderString.toString(), KrcdtDayInfoPerWork.class)
										.setParameter("employeeIds", sublistEmployeeIds)
										.setParameter("processingYmds", sublistYMDs).getResultList());
			});
		});

		return result;
	}

}
