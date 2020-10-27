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
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDayInfoPerWork;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDayInfoPerWorkPK;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDayTsAtdSche;
import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtDayTsAtdSchePK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.ScheduleTimeSheet;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author lamdt
 *
 */
@Stateless
public class JpaWorkInformationRepository extends JpaRepository implements WorkInformationRepository {

	private static final String FIND_BY_EMPLOYEE_ID = "SELECT a FROM KrcdtDayInfoPerWork a "
			+ " WHERE a.krcdtDayInfoPerWorkPK.employeeId = :employeeId ";

	private String FIND_BY_WORKTYPE_PERIOD = "SELECT c.krcdtDayInfoPerWorkPK.ymd" + " FROM KrcdtDayInfoPerWork c"
			+ " WHERE c.krcdtDayInfoPerWorkPK.ymd >= :startDate" + " AND c.krcdtDayInfoPerWorkPK.ymd <= :endDate"
			+ " AND c.recordWorkWorktypeCode = :workTypeCode" + " AND c.krcdtDayInfoPerWorkPK.employeeId = :employeeId";

	private String FIND_BY_LIST_DATE = "SELECT c " + " FROM KrcdtDayInfoPerWork c"
			+ " WHERE c.krcdtDayInfoPerWorkPK.ymd IN :dates" + " AND c.krcdtDayInfoPerWorkPK.employeeId = :employeeId";

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
				entity.krcdtDayTsAtdSchePK = new KrcdtDayTsAtdSchePK(
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
				entity.krcdtDayInfoPerWorkPK = new KrcdtDayInfoPerWorkPK(
						rec.getString("SID"), rec.getGeneralDate("YMD"));
				entity.recordWorkWorktypeCode = rec.getString("RECORD_WORK_WORKTYPE_CODE");
				entity.recordWorkWorktimeCode = rec.getString("RECORD_WORK_WORKTIME_CODE");
				entity.scheduleWorkWorktypeCode = rec.getString("SCHEDULE_WORK_WORKTYPE_CODE");
				entity.scheduleWorkWorktimeCode = rec.getString("SCHEDULE_WORK_WORKTIME_CODE");
				entity.calculationState = rec.getInt("CALCULATION_STATE");
				entity.goStraightAttribute = rec.getInt("GO_STRAIGHT_ATR");
				entity.backStraightAttribute = rec.getInt("BACK_STRAIGHT_ATR");
				entity.dayOfWeek = rec.getInt("DAY_OF_WEEK");
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
				entity.krcdtDayTsAtdSchePK = new KrcdtDayTsAtdSchePK(rec.getString("SID"),
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
				WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(employeeId,
						new WorkInformation(rec.getString("RECORD_WORK_WORKTYPE_CODE"), rec.getString("RECORD_WORK_WORKTIME_CODE")),
						new WorkInformation(rec.getString("SCHEDULE_WORK_WORKTYPE_CODE"), rec.getString("SCHEDULE_WORK_WORKTIME_CODE")),
						calcState == CalculationState.Calculated.value ? CalculationState.Calculated
								: CalculationState.No_Calculated,
						goStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use,
						backStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use, ymd,
						EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
						scheduleTimess.stream().filter(c -> c.krcdtDayTsAtdSchePK.ymd.equals(ymd))
								.map(c -> c.toDomain()).collect(Collectors.toList()));
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
				entity.krcdtDayTsAtdSchePK = new KrcdtDayTsAtdSchePK(rec.getString("SID"),
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
				WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(employeeId,
						new WorkInformation(rec.getString("RECORD_WORK_WORKTYPE_CODE"), rec.getString("RECORD_WORK_WORKTIME_CODE")),
						new WorkInformation(rec.getString("SCHEDULE_WORK_WORKTYPE_CODE"), rec.getString("SCHEDULE_WORK_WORKTIME_CODE")),
						calcState == CalculationState.Calculated.value ? CalculationState.Calculated
								: CalculationState.No_Calculated,
						goStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use,
						backStraight == NotUseAttribute.Use.value ? NotUseAttribute.Use : NotUseAttribute.Not_use, ymd,
						EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class),
						scheduleTimes.stream().filter(c -> c.krcdtDayTsAtdSchePK.ymd.equals(ymd))
								.map(c -> c.toDomain()).collect(Collectors.toList()));
				domain.setVersion(rec.getLong("EXCLUS_VER"));
				return domain;
			});
		}
	}

	@Override
	public void updateByKey(WorkInfoOfDailyPerformance domain) {

		if (domain != null) {
			KrcdtDayInfoPerWork data = this.findKrcdtDayInfoPerWorkWithJdbc(domain.getEmployeeId(), domain.getYmd())
					.orElseGet(() -> new KrcdtDayInfoPerWork(
							new KrcdtDayInfoPerWorkPK(domain.getEmployeeId(), domain.getYmd())));

			internalUpdate(domain, data);
		}
	}

	private void internalUpdate(WorkInfoOfDailyPerformance domain, KrcdtDayInfoPerWork data) {
		if (domain.getWorkInformation().getRecordInfo() != null) {
			WorkInformation record = domain.getWorkInformation().getRecordInfo();
			
			data.recordWorkWorktypeCode = record.getWorkTypeCode().v();
			data.recordWorkWorktimeCode = record.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null);
		}
		
		if (domain.getWorkInformation().getScheduleInfo() != null) {
			WorkInformation sched = domain.getWorkInformation().getScheduleInfo();
			
			data.scheduleWorkWorktypeCode = sched.getWorkTypeCode().v();
			data.scheduleWorkWorktimeCode = sched.getWorkTimeCodeNotNull().map(m -> m.v()).orElse(null);
		}
		
		data.calculationState = domain.getWorkInformation().getCalculationState().value;
		data.backStraightAttribute = domain.getWorkInformation().getBackStraightAtr().value;
		data.goStraightAttribute = domain.getWorkInformation().getGoStraightAtr().value;
		data.dayOfWeek = domain.getWorkInformation().getDayOfWeek().value;
		data.version = domain.getVersion();
		
		if(domain.getWorkInformation().getScheduleTimeSheets().isEmpty()){
			data.scheduleTimes.forEach(c -> {
				this.commandProxy().remove(getEntityManager().merge(c));
			});
			data.scheduleTimes = new ArrayList<>();
//				this.getEntityManager().flush();
		} else {
			if (data.scheduleTimes == null || data.scheduleTimes.isEmpty()) {
				data.scheduleTimes = domain.getWorkInformation().getScheduleTimeSheets().stream()
						.map(c -> new KrcdtDayTsAtdSche(
								new KrcdtDayTsAtdSchePK(domain.getEmployeeId(), domain.getYmd(),
										c.getWorkNo().v()),
								c.getAttendance().valueAsMinutes(), c.getLeaveWork().valueAsMinutes()))
						.collect(Collectors.toList());
			} else {
				data.scheduleTimes.stream().forEach(st -> {
					domain.getWorkInformation().getScheduleTimeSheets().stream()
							.filter(dst -> dst.getWorkNo().v() == st.krcdtDayTsAtdSchePK.workNo).findFirst()
							.ifPresent(dst -> {
								st.attendance = dst.getAttendance().valueAsMinutes();
								st.leaveWork = dst.getLeaveWork().valueAsMinutes();
							});
				});
			}   
			List<KrcdtDayTsAtdSche> schedules = new ArrayList<>();
			try (PreparedStatement stmtSche = this.connection().prepareStatement(
					"select * from KRCDT_DAY_TS_ATD_SCHE"
					+ " where SID = ? and YMD = ?")) {
				stmtSche.setString(1, domain.getEmployeeId());
				stmtSche.setDate(2, Date.valueOf(domain.getYmd().localDate()));
				schedules = new NtsResultSet(stmtSche.executeQuery()).getList(rs -> {
					KrcdtDayTsAtdSchePK pks = new KrcdtDayTsAtdSchePK();
					pks.employeeId = rs.getString("SID");
					pks.ymd = rs.getGeneralDate("YMD");
					pks.workNo = rs.getInt("WORK_NO");
							
					KrcdtDayTsAtdSche es = new KrcdtDayTsAtdSche();
					es.krcdtDayTsAtdSchePK = pks;
					es.attendance = rs.getInt("ATTENDANCE");
					es.leaveWork = rs.getInt("LEAVE_WORK");
					
					return es;
				});
			} catch (SQLException e) {
			}
			if(schedules.isEmpty()) {
				this.commandProxy().insertAll(data.scheduleTimes);
			}else {
				this.commandProxy().updateAll(data.scheduleTimes);
			}
		}

		this.commandProxy().update(data);
	}

	@SneakyThrows
	private Optional<KrcdtDayInfoPerWork> findKrcdtDayInfoPerWorkWithJdbc(String employeeId, GeneralDate ymd) {
		
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
		KrcdtDayInfoPerWorkPK pk = new KrcdtDayInfoPerWorkPK();
		pk.employeeId = rec.getString("SID");
		pk.ymd = rec.getGeneralDate("YMD");
		
		KrcdtDayInfoPerWork entity = new KrcdtDayInfoPerWork();
		entity.krcdtDayInfoPerWorkPK = pk;
		entity.recordWorkWorktypeCode = rec.getString("RECORD_WORK_WORKTYPE_CODE");
		entity.recordWorkWorktimeCode = rec.getString("RECORD_WORK_WORKTIME_CODE");
		entity.scheduleWorkWorktypeCode = rec.getString("SCHEDULE_WORK_WORKTYPE_CODE");
		entity.scheduleWorkWorktimeCode = rec.getString("SCHEDULE_WORK_WORKTIME_CODE");
		entity.calculationState = rec.getInt("CALCULATION_STATE");
		entity.goStraightAttribute = rec.getInt("GO_STRAIGHT_ATR");
		entity.backStraightAttribute = rec.getInt("BACK_STRAIGHT_ATR");
		entity.dayOfWeek = rec.getInt("DAY_OF_WEEK");
		entity.version = rec.getLong("EXCLUS_VER");
		try (PreparedStatement stmtSche = this.connection().prepareStatement(
					"select * from KRCDT_DAY_TS_ATD_SCHE"
					+ " where SID = ? and YMD = ?")) {
			stmtSche.setString(1, pk.employeeId);
			stmtSche.setDate(2, Date.valueOf(pk.ymd.toLocalDate()));
			entity.scheduleTimes = new NtsResultSet(stmtSche.executeQuery()).getList(rs -> {
				KrcdtDayTsAtdSchePK pks = new KrcdtDayTsAtdSchePK();
				pks.employeeId = rs.getString("SID");
				pks.ymd = rs.getGeneralDate("YMD");
				pks.workNo = rs.getInt("WORK_NO");
						
				KrcdtDayTsAtdSche es = new KrcdtDayTsAtdSche();
				es.krcdtDayTsAtdSchePK = pks;
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
		query.append(" WHERE af.krcdtDayInfoPerWorkPK.employeeId IN :employeeId ");
		query.append(" AND af.krcdtDayInfoPerWorkPK.ymd IN :date");
		TypedQueryWrapper<Object[]> tQuery = this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
					.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
					.getList().stream().filter(c -> {
						KrcdtDayInfoPerWork af = (KrcdtDayInfoPerWork) c[0];
						return p.get(af.krcdtDayInfoPerWorkPK.employeeId).contains(af.krcdtDayInfoPerWorkPK.ymd);
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
				WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(sid, 
						new WorkInformation(c.getString("RECORD_WORK_WORKTYPE_CODE"), c.getString("RECORD_WORK_WORKTIME_CODE")), 
						new WorkInformation(c.getString("SCHEDULE_WORK_WORKTYPE_CODE"), c.getString("SCHEDULE_WORK_WORKTIME_CODE")), 
						calcState == null ? null : EnumAdaptor.valueOf(calcState, CalculationState.class), 
						goStraight == null ? null : EnumAdaptor.valueOf(goStraight, NotUseAttribute.class), 
						backStraight == null ? null : EnumAdaptor.valueOf(backStraight, NotUseAttribute.class), 
						ymd, dayOfWeek == null ? null : EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class), 
						getScheduleTime(scheTimes, sid, ymd));
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
					WorkInfoOfDailyPerformance domain = new WorkInfoOfDailyPerformance(sid, 
							new WorkInformation(c.getString("RECORD_WORK_WORKTYPE_CODE"), c.getString("RECORD_WORK_WORKTIME_CODE")), 
							new WorkInformation(c.getString("SCHEDULE_WORK_WORKTYPE_CODE"), c.getString("SCHEDULE_WORK_WORKTIME_CODE")), 
							calcState == null ? null : EnumAdaptor.valueOf(calcState, CalculationState.class), 
							goStraight == null ? null : EnumAdaptor.valueOf(goStraight, NotUseAttribute.class), 
							backStraight == null ? null : EnumAdaptor.valueOf(backStraight, NotUseAttribute.class), 
							ymd, dayOfWeek == null ? null : EnumAdaptor.valueOf(dayOfWeek, DayOfWeek.class), 
							getScheduleTime(scheTimes, sid, ymd));
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
		this.queryProxy().find(new KrcdtDayInfoPerWorkPK(employeeId, date), KrcdtDayInfoPerWork.class).ifPresent(entity -> {
			entity.dirtying();
			this.commandProxy().update(entity);
		});
	}

	@Override
	public void dirtying(String employeeId, GeneralDate date, long version){
		this.queryProxy().find(new KrcdtDayInfoPerWorkPK(employeeId, date), KrcdtDayInfoPerWork.class).ifPresent(entity -> {
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
		builderString.append(" WHERE a.krcdtDayInfoPerWorkPK.employeeId IN :employeeIds");
		builderString.append(" AND a.krcdtDayInfoPerWorkPK.ymd IN :processingYmds");
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
