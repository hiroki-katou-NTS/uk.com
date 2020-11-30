package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
//import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtWorkScheduleTime;
//import nts.uk.ctx.at.record.infra.entity.workinformation.KrcdtWorkScheduleTimePK;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiLeavingWork;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiLeavingWorkPK;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWork;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWorkPK;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.infra.data.jdbc.JDBCUtil;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaTimeLeavingOfDailyPerformanceRepository extends JpaRepository
		implements TimeLeavingOfDailyPerformanceRepository {

	private static final String FIND_BY_KEY;
	
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		try (val timeLeavingWorkStatement = this.connection().prepareStatement(
					"delete from KRCDT_TIME_LEAVING_WORK where SID = ? and YMD = ? and TIME_LEAVING_TYPE = ?")) {
			timeLeavingWorkStatement.setString(1, employeeId);
			timeLeavingWorkStatement.setDate(2, Date.valueOf(ymd.toLocalDate()));
			timeLeavingWorkStatement.setInt(3, 0);
			timeLeavingWorkStatement.execute();
			
			try (val statement = this.connection().prepareStatement(
					"delete from KRCDT_DAI_LEAVING_WORK where SID = ? and YMD = ?")) {
				statement.setString(1, employeeId);
				statement.setDate(2, Date.valueOf(ymd.toLocalDate()));
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<TimeLeavingOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtDaiLeavingWork.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(f -> f.toDomain());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	@SneakyThrows
	public List<TimeLeavingOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {

		List<KrcdtTimeLeavingWork> scheduleTimes = new ArrayList<>();

		try (PreparedStatement sqlSchedule = this.connection().prepareStatement(
				"select * from KRCDT_TIME_LEAVING_WORK where SID = ? and YMD >= ? and YMD <= ? order by YMD ")) {
			sqlSchedule.setString(1, employeeId);
			sqlSchedule.setDate(2, Date.valueOf(datePeriod.start().localDate()));
			sqlSchedule.setDate(3, Date.valueOf(datePeriod.end().localDate()));

			scheduleTimes = new NtsResultSet(sqlSchedule.executeQuery()).getList(rec -> {
				KrcdtTimeLeavingWork entity = new KrcdtTimeLeavingWork();
				entity.krcdtTimeLeavingWorkPK = new KrcdtTimeLeavingWorkPK(rec.getString("SID"), rec.getInt("WORK_NO"),
						rec.getGeneralDate("YMD"), rec.getInt("TIME_LEAVING_TYPE"));
				entity.attendanceActualTime = rec.getInt("ATD_ACTUAL_TIME");
				entity.attendanceActualPlaceCode = rec.getString("ATD_ACTUAL_PLACE_CODE");
				entity.attendanceActualSourceInfo = rec.getInt("ATD_ACTUAL_SOURCE_INFO");

				entity.attendanceStampTime = rec.getInt("ATD_STAMP_TIME");
				entity.attendanceStampPlaceCode = rec.getString("ATD_STAMP_PLACE_CODE");
				entity.attendanceStampSourceInfo = rec.getInt("ATD_STAMP_SOURCE_INFO");

				entity.attendanceNumberStamp = rec.getInt("ATD_NUMBER_STAMP");

				entity.leaveWorkActualTime = rec.getInt("LWK_ACTUAL_TIME");
				entity.leaveWorkActualPlaceCode = rec.getString("LWK_ACTUAL_PLACE_CODE");
				entity.leaveActualSourceInfo = rec.getInt("LWK_ACTUAL_SOURCE_INFO");

				entity.leaveWorkStampTime = rec.getInt("LWK_STAMP_TIME");
				entity.leaveWorkStampPlaceCode = rec.getString("LWK_STAMP_PLACE_CODE");
				entity.leaveWorkStampSourceInfo = rec.getInt("LWK_STAMP_SOURCE_INFO");

				entity.leaveWorkNumberStamp = rec.getInt("LWK_NUMBER_STAMP");

				entity.atdOvertime = rec.getInt("ATD_OVERTIME");
				entity.atdLateNightOvertime = rec.getInt("ATD_LATE_NIGHT_OVERTIME");
				entity.atdBreakStart = rec.getInt("ATD_BREAK_START");
				entity.atdBreakEnd = rec.getInt("ATD_BREAK_END");
				
				entity.lwkOvertime = rec.getInt("LWK_OVERTIME");
				entity.lwkLateNightOvertime = rec.getInt("LWK_LATE_NIGHT_OVERTIME");
				entity.lwkBreakStart = rec.getInt("LWK_BREAK_START");
				entity.lwkBreakEnd = rec.getInt("LWK_BREAK_END");
				return entity;
			});
		}

		List<KrcdtTimeLeavingWork> newScheduleTimes = scheduleTimes;

		try (PreparedStatement sqlScheduleNew = this.connection().prepareStatement(
				"select * from KRCDT_DAI_LEAVING_WORK where SID = ? and YMD >= ? and YMD <= ? order by YMD ")) {
			sqlScheduleNew.setString(1, employeeId);
			sqlScheduleNew.setDate(2, Date.valueOf(datePeriod.start().localDate()));
			sqlScheduleNew.setDate(3, Date.valueOf(datePeriod.end().localDate()));

			return new NtsResultSet(sqlScheduleNew.executeQuery()).getList(newRec -> {
				GeneralDate ymd = newRec.getGeneralDate("YMD");
				return new TimeLeavingOfDailyPerformance(employeeId, new WorkTimes(newRec.getInt("WORK_TIMES")),
						newScheduleTimes.stream().filter(c -> c.krcdtTimeLeavingWorkPK.ymd.equals(ymd))
								.map(item -> item.toDomain()).collect(Collectors.toList()),
						ymd);
			});
		}

		// return this.queryProxy().query(FIND_BY_PERIOD_ORDER_BY_YMD,
		// KrcdtDaiLeavingWork.class)
		// .setParameter("employeeId", employeeId).setParameter("start",
		// datePeriod.start())
		// .setParameter("end", datePeriod.end()).getList(f -> f.toDomain());
	}

	@Override
	public void update(TimeLeavingOfDailyPerformance domain) {
		if (domain == null) {
			return;
		}
		
		internalUpdate(domain, getDailyLeaving(domain.getEmployeeId(), domain.getYmd()));
		// this.getEntityManager().flush();
	}

	private void internalUpdate(TimeLeavingOfDailyPerformance domain,KrcdtDaiLeavingWork entity) {
		List<KrcdtTimeLeavingWork> timeWorks = entity.timeLeavingWorks;
		entity.workTimes = domain.getAttendance().getWorkTimes() == null ? null : domain.getAttendance().getWorkTimes().v();
		domain.getAttendance().getTimeLeavingWorks().stream().forEach(c -> {
			KrcdtTimeLeavingWork krcdtTimeLeavingWork = timeWorks.stream()
					.filter(x -> x.krcdtTimeLeavingWorkPK.workNo == c.getWorkNo().v()
							&& x.krcdtTimeLeavingWorkPK.timeLeavingType == 0)
					.findFirst().orElse(null);
			boolean isNew = krcdtTimeLeavingWork == null;
			if (isNew) {
				krcdtTimeLeavingWork = new KrcdtTimeLeavingWork();
				krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK = new KrcdtTimeLeavingWorkPK(domain.getEmployeeId(),
						c.getWorkNo().v(), domain.getYmd(), 0);
			}
			if (c.getAttendanceStamp().isPresent()) {
				TimeActualStamp attendanceStamp = c.getAttendanceStamp().get();
				WorkStamp attendanceActualS = attendanceStamp.getActualStamp().orElse(null);
				WorkStamp attendanceS = attendanceStamp.getStamp().orElse(null);
				// set 時間外時間
				OvertimeDeclaration overtimeDeclaration = attendanceStamp.getOvertimeDeclaration().orElse(null);
				if (overtimeDeclaration != null) {
					krcdtTimeLeavingWork.atdOvertime = overtimeDeclaration.getOverTime() == null?null:overtimeDeclaration.getOverTime().valueAsMinutes();
					krcdtTimeLeavingWork.atdLateNightOvertime = overtimeDeclaration.getOverLateNightTime() == null?null:overtimeDeclaration.getOverLateNightTime().valueAsMinutes();
				}else {
					krcdtTimeLeavingWork.atdOvertime = null;
					krcdtTimeLeavingWork.atdLateNightOvertime = null;
				}
				//set 時間休暇時間帯
				TimeZone timeZone = attendanceStamp.getTimeVacation().orElse(null);
				if (timeZone != null) {
					krcdtTimeLeavingWork.atdBreakStart = timeZone.getStart() == null?null:timeZone.getStart().valueAsMinutes();
					krcdtTimeLeavingWork.atdBreakEnd = timeZone.getEnd() == null?null:timeZone.getEnd().valueAsMinutes();;
				}else {
					krcdtTimeLeavingWork.atdBreakStart = null;
					krcdtTimeLeavingWork.atdBreakEnd = null;
				}
				if (attendanceActualS != null) {
					krcdtTimeLeavingWork.attendanceActualTime = attendanceActualS.getTimeDay().getTimeWithDay().isPresent() ? attendanceActualS.getTimeDay().getTimeWithDay().get().valueAsMinutes()
							: null;
					krcdtTimeLeavingWork.attendanceActualPlaceCode = !attendanceActualS.getLocationCode().isPresent()
							? null : attendanceActualS.getLocationCode().get().v();
					krcdtTimeLeavingWork.attendanceActualSourceInfo = attendanceActualS.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0
							: attendanceActualS.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;

				} else {
					krcdtTimeLeavingWork.attendanceActualTime = null;
					krcdtTimeLeavingWork.attendanceActualPlaceCode = null;
					krcdtTimeLeavingWork.attendanceActualSourceInfo = null;
				}
				if (attendanceS != null) {
					krcdtTimeLeavingWork.attendanceStampTime = attendanceS.getTimeDay().getTimeWithDay().isPresent() ? attendanceS.getTimeDay().getTimeWithDay().get().valueAsMinutes()
							: null;
					krcdtTimeLeavingWork.attendanceStampPlaceCode = !attendanceS.getLocationCode().isPresent() ? null
							: attendanceS.getLocationCode().get().v();
					krcdtTimeLeavingWork.attendanceStampSourceInfo = attendanceS.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0
							: attendanceS.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;

				} else {
					krcdtTimeLeavingWork.attendanceStampTime = null;
					krcdtTimeLeavingWork.attendanceStampPlaceCode = null;
					krcdtTimeLeavingWork.attendanceStampSourceInfo = null;
				}
				krcdtTimeLeavingWork.attendanceNumberStamp = attendanceStamp.getNumberOfReflectionStamp();
			}
			if (c.getLeaveStamp().isPresent()) {
				TimeActualStamp ls = c.getLeaveStamp().get();
				WorkStamp as = ls.getActualStamp().orElse(null);
				WorkStamp s = ls.getStamp().orElse(null);
				// set 時間外時間
				OvertimeDeclaration overtimeDeclaration = ls.getOvertimeDeclaration().orElse(null);
				if (overtimeDeclaration != null) {
					krcdtTimeLeavingWork.lwkOvertime = overtimeDeclaration.getOverTime() == null?null:overtimeDeclaration.getOverTime().valueAsMinutes();
					krcdtTimeLeavingWork.lwkLateNightOvertime = overtimeDeclaration.getOverLateNightTime() == null?null:overtimeDeclaration.getOverLateNightTime().valueAsMinutes();
				}else {
					krcdtTimeLeavingWork.lwkOvertime = null;
					krcdtTimeLeavingWork.lwkLateNightOvertime = null;
				}
				//set 時間休暇時間帯
				TimeZone timeZone = ls.getTimeVacation().orElse(null);
				if (timeZone != null) {
					krcdtTimeLeavingWork.lwkBreakStart = timeZone.getStart() == null?null:timeZone.getStart().valueAsMinutes();
					krcdtTimeLeavingWork.lwkBreakEnd = timeZone.getEnd() == null?null:timeZone.getEnd().valueAsMinutes();;
				}else {
					krcdtTimeLeavingWork.lwkBreakStart = null;
					krcdtTimeLeavingWork.lwkBreakEnd = null;
				}
				
				if (as != null) {
					krcdtTimeLeavingWork.leaveWorkActualTime = as.getTimeDay().getTimeWithDay() == null ? null
							: as.getTimeDay().getTimeWithDay().get().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkActualPlaceCode = !as.getLocationCode().isPresent() ? null
							: as.getLocationCode().get().v();
					krcdtTimeLeavingWork.leaveActualSourceInfo = as.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0
							: as.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
				} else {
					krcdtTimeLeavingWork.leaveWorkActualTime = null;
					krcdtTimeLeavingWork.leaveWorkActualPlaceCode = null;
					krcdtTimeLeavingWork.leaveActualSourceInfo = null;
				}
				if (s != null) {
					krcdtTimeLeavingWork.leaveWorkStampTime = s.getTimeDay().getTimeWithDay() == null ? null
							: s.getTimeDay().getTimeWithDay().get().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkStampPlaceCode = !s.getLocationCode().isPresent() ? null
							: s.getLocationCode().get().v();
					krcdtTimeLeavingWork.leaveWorkStampSourceInfo = s.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0
							: s.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
				} else {
					krcdtTimeLeavingWork.leaveWorkStampTime = null;
					krcdtTimeLeavingWork.leaveWorkStampPlaceCode = null;
					krcdtTimeLeavingWork.leaveWorkStampSourceInfo = null;
				}
				krcdtTimeLeavingWork.leaveWorkNumberStamp = ls.getNumberOfReflectionStamp();

			}
			// krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK.timeLeavingType = 0;
			krcdtTimeLeavingWork.daiLeavingWork = entity;
			if (isNew) {
				timeWorks.add(krcdtTimeLeavingWork);
			}
		});

		entity.timeLeavingWorks = timeWorks.isEmpty() ? null : timeWorks;
		this.commandProxy().update(entity);
		if (!timeWorks.isEmpty()) {
			this.commandProxy().updateAll(entity.timeLeavingWorks);
		}
		// this.getEntityManager().flush();
	}

	private KrcdtDaiLeavingWork getDailyLeaving(String employee, GeneralDate date) {
		try (val statement = this.connection().prepareStatement(
					"select * FROM KRCDT_DAI_LEAVING_WORK where SID = ? and YMD = ?")) {
			statement.setString(1, employee);
			statement.setDate(2, Date.valueOf(date.localDate()));
			Optional<KrcdtDaiLeavingWork> krcdtDaiBreakTimes = new NtsResultSet(statement.executeQuery())
					.getSingle(rec -> {
						val entity = new KrcdtDaiLeavingWork();
						entity.krcdtDaiLeavingWorkPK = new KrcdtDaiLeavingWorkPK(employee, date);
						entity.workTimes = rec.getInt("WORK_TIMES");
						entity.timeLeavingWorks = getTimeLeavingWork(employee, date);
						return entity;
					});

			if (!krcdtDaiBreakTimes.isPresent()) {
				return getDefault(employee, date);
			}

			return krcdtDaiBreakTimes.get();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private KrcdtDaiLeavingWork getDefault(String employee, GeneralDate date) {
		KrcdtDaiLeavingWork defaultV = new KrcdtDaiLeavingWork();
		defaultV.krcdtDaiLeavingWorkPK = new KrcdtDaiLeavingWorkPK(employee, date);
		defaultV.timeLeavingWorks = new ArrayList<>();
		return defaultV;
	}

	private List<KrcdtTimeLeavingWork> getTimeLeavingWork(String employee, GeneralDate date) {
		try (PreparedStatement statement = this.connection().prepareStatement(
					"select * FROM KRCDT_TIME_LEAVING_WORK where SID = ? and YMD = ? and TIME_LEAVING_TYPE = ?")) {

			statement.setString(1, employee);
			statement.setDate(2, Date.valueOf(date.localDate()));
			statement.setInt(3, 0);
			List<KrcdtTimeLeavingWork> krcdtTimeLeaveWorks = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				val entity = new KrcdtTimeLeavingWork();
				entity.krcdtTimeLeavingWorkPK = new KrcdtTimeLeavingWorkPK();
				entity.krcdtTimeLeavingWorkPK.employeeId = employee;
				entity.krcdtTimeLeavingWorkPK.ymd = date;
				entity.krcdtTimeLeavingWorkPK.timeLeavingType = 0;
				entity.krcdtTimeLeavingWorkPK.workNo = rec.getInt("WORK_NO");
				entity.attendanceActualTime = rec.getInt("ATD_ACTUAL_TIME");
				entity.attendanceActualPlaceCode = rec.getString("ATD_ACTUAL_PLACE_CODE");
				entity.attendanceActualSourceInfo = rec.getInt("ATD_ACTUAL_SOURCE_INFO");
				entity.attendanceStampTime = rec.getInt("ATD_STAMP_TIME");
				entity.attendanceStampPlaceCode = rec.getString("ATD_STAMP_PLACE_CODE");
				entity.attendanceStampSourceInfo = rec.getInt("ATD_STAMP_SOURCE_INFO");
				entity.attendanceNumberStamp = rec.getInt("ATD_NUMBER_STAMP");
				entity.leaveWorkActualTime = rec.getInt("LWK_ACTUAL_TIME");
				entity.leaveWorkActualPlaceCode = rec.getString("LWK_ACTUAL_PLACE_CODE");
				entity.leaveActualSourceInfo = rec.getInt("LWK_ACTUAL_SOURCE_INFO");
				entity.leaveWorkStampTime = rec.getInt("LWK_STAMP_TIME");
				entity.leaveWorkStampPlaceCode = rec.getString("LWK_STAMP_PLACE_CODE");
				entity.leaveWorkStampSourceInfo = rec.getInt("LWK_STAMP_SOURCE_INFO");
				entity.leaveWorkNumberStamp = rec.getInt("LWK_NUMBER_STAMP");
				
				entity.atdOvertime = rec.getInt("ATD_OVERTIME");
				entity.atdLateNightOvertime = rec.getInt("ATD_LATE_NIGHT_OVERTIME");
				entity.atdBreakStart = rec.getInt("ATD_BREAK_START");
				entity.atdBreakEnd = rec.getInt("ATD_BREAK_END");
				
				entity.lwkOvertime = rec.getInt("LWK_OVERTIME");
				entity.lwkLateNightOvertime = rec.getInt("LWK_LATE_NIGHT_OVERTIME");
				entity.lwkBreakStart = rec.getInt("LWK_BREAK_START");
				entity.lwkBreakEnd = rec.getInt("LWK_BREAK_END");
				
				return entity;
			});

			return krcdtTimeLeaveWorks;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		if (timeLeavingOfDailyPerformance == null) {
			return;
		}
		// this.commandProxy().insert(KrcdtDaiLeavingWork.toEntity(timeLeavingOfDailyPerformance));
		// this.getEntityManager().flush();
		try {
			Connection con = this.getEntityManager().unwrap(Connection.class);
			Statement statementI = con.createStatement();

			String insertTableSQL = "INSERT INTO KRCDT_DAI_LEAVING_WORK ( SID , YMD , WORK_TIMES ) " + "VALUES( '"
					+ timeLeavingOfDailyPerformance.getEmployeeId() + "' , '" + timeLeavingOfDailyPerformance.getYmd()
					+ "' , " + timeLeavingOfDailyPerformance.getAttendance().getWorkTimes().v() + " )";
			statementI.executeUpdate(JDBCUtil.toInsertWithCommonField(insertTableSQL));

			for (TimeLeavingWork timeLeavingWork : timeLeavingOfDailyPerformance.getAttendance().getTimeLeavingWorks()) {
				// TimeLeavingWork - attendanceStamp - actualStamp

				Integer attActualTime = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent())
								? timeLeavingWork.getAttendanceStamp().get().getActualStamp().get().getTimeDay().getTimeWithDay().get()
										.valueAsMinutes()
								: null;
				Integer attActualStampSource = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent())
								? timeLeavingWork.getAttendanceStamp().get().getActualStamp().get()
										.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value
								: null;
				String attActualStampLocationCode = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getActualStamp().get().getLocationCode()
								.isPresent())
										? "'" + timeLeavingWork.getAttendanceStamp().get().getActualStamp().get()
												.getLocationCode().get().v() + "'"
										: null;

				// TimeLeavingWork - attendanceStamp - stamp
				Integer attStampTime = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent())
								? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get()
										.valueAsMinutes()
								: null;
				Integer attStampSource = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent())
								? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans().value
								: null;
				String attStampLocationCode = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode().isPresent())
								? "'" + timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode()
										.get().v() + "'"
								: null;
				// TimeLeavingWork - leaveStamp - numberOfReflectionStamp
				Integer attNumberReflec = timeLeavingWork.getAttendanceStamp().isPresent()
						? timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp() : null;

				// TimeLeavingWork - leaveStamp - actualStamp
				Integer leaveActualTime = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent())
								? timeLeavingWork.getLeaveStamp().get().getActualStamp().get().getTimeDay().getTimeWithDay().get()
										.valueAsMinutes()
								: null;
				Integer leaveActualStampSource = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent())
								? timeLeavingWork.getLeaveStamp().get().getActualStamp().get()
										.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value
								: null;
				String leaveActualStampLocationCode = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getActualStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getActualStamp().get().getLocationCode().isPresent())
								? "'" + timeLeavingWork.getLeaveStamp().get().getActualStamp().get().getLocationCode()
										.get().v() + "'"
								: null;

				// TimeLeavingWork - leaveStamp - stamp
				Integer leaveStampTime = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent())
								? timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getTimeWithDay().get()
										.valueAsMinutes()
								: null;
				Integer leaveStampSource = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent())
								? timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeDay().getReasonTimeChange().getTimeChangeMeans().value
								: null;
				String leaveStampLocationCode = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode().isPresent()) ? "'"
								+ timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode().get().v()
								+ "'" : null;

				// TimeLeavingWork - leaveStamp - numberOfReflectionStamp
				Integer leaveNumberReflec = timeLeavingWork.getLeaveStamp().isPresent()
						? timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp() : null;
				
				// overtimeDeclaration 
				//attendance
				Integer atdOvertime = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration().isPresent())
						? timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration().get().getOverTime().valueAsMinutes()
						: null;
				Integer atdLateNightOvertime = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration().isPresent())
						? timeLeavingWork.getAttendanceStamp().get().getOvertimeDeclaration().get().getOverLateNightTime().valueAsMinutes()
						: null;
				Integer atdBreakStart = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getTimeVacation().isPresent())
						? timeLeavingWork.getAttendanceStamp().get().getTimeVacation().get().getStart().valueAsMinutes()
						: null;
				Integer atdBreakEnd = (timeLeavingWork.getAttendanceStamp().isPresent()
						&& timeLeavingWork.getAttendanceStamp().get().getTimeVacation().isPresent())
						? timeLeavingWork.getAttendanceStamp().get().getTimeVacation().get().getEnd().valueAsMinutes()
						: null;
						
				//leaveStamp
				Integer lwkOvertime = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getOvertimeDeclaration().isPresent())
						? timeLeavingWork.getLeaveStamp().get().getOvertimeDeclaration().get().getOverTime().valueAsMinutes()
						: null;
				Integer lwkLateNightOvertime = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getOvertimeDeclaration().isPresent())
						? timeLeavingWork.getLeaveStamp().get().getOvertimeDeclaration().get().getOverLateNightTime().valueAsMinutes()
						: null;
				Integer lwkBreakStart = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getTimeVacation().isPresent())
						? timeLeavingWork.getLeaveStamp().get().getTimeVacation().get().getStart().valueAsMinutes()
						: null;
				Integer lwkBreakEnd = (timeLeavingWork.getLeaveStamp().isPresent()
						&& timeLeavingWork.getLeaveStamp().get().getTimeVacation().isPresent())
						? timeLeavingWork.getLeaveStamp().get().getTimeVacation().get().getEnd().valueAsMinutes()
						: null;

				String insertTimeLeaving = "INSERT INTO KRCDT_TIME_LEAVING_WORK ( SID , WORK_NO , YMD , TIME_LEAVING_TYPE, ATD_ACTUAL_TIME , ATD_ACTUAL_PLACE_CODE , "
						+ " ATD_ACTUAL_SOURCE_INFO , ATD_STAMP_TIME , ATD_STAMP_PLACE_CODE, ATD_STAMP_SOURCE_INFO, ATD_NUMBER_STAMP, "
						+ " LWK_ACTUAL_TIME, LWK_ACTUAL_PLACE_CODE , LWK_ACTUAL_SOURCE_INFO, LWK_STAMP_TIME, LWK_STAMP_PLACE_CODE , LWK_STAMP_SOURCE_INFO, LWK_NUMBER_STAMP , "
						+ " ATD_OVERTIME, ATD_LATE_NIGHT_OVERTIME, ATD_BREAK_START,ATD_BREAK_END, LWK_OVERTIME, LWK_LATE_NIGHT_OVERTIME, LWK_BREAK_START, LWK_BREAK_END  ) "
						+ " ATD_OVERTIME, ATD_LATE_NIGHT_OVERTIME, ATD_BREAK_START,ATD_BREAK_END, LWK_OVERTIME, LWK_LATE_NIGHT_OVERTIME, LWK_BREAK_START, LWK_BREAK_END  ) "
						+ "VALUES( '" + timeLeavingOfDailyPerformance.getEmployeeId() + "' , "
						+ timeLeavingWork.getWorkNo().v() + " , '" + timeLeavingOfDailyPerformance.getYmd() + "', " + 0
						+", " + attActualTime + ", " + attActualStampLocationCode
						+ " , " + attActualStampSource +  ", " + attStampTime + ", "
						+ attStampLocationCode + " , " + attStampSource + ", " + attNumberReflec + ", "
						 + leaveActualTime + ", " + leaveActualStampLocationCode + " , "
						+ leaveActualStampSource + " , "  + leaveStampTime + ", "
						+ leaveStampLocationCode + " , " + leaveStampSource + ", " + leaveNumberReflec + ", "
						+ atdOvertime + " , " + atdLateNightOvertime + ", " + atdBreakStart + ", " + atdBreakEnd + ", "
						+ lwkOvertime + " , " + lwkLateNightOvertime + " , " + lwkBreakStart + " , " + lwkBreakEnd+ " )";
				statementI.executeUpdate(JDBCUtil.toInsertWithCommonField(insertTimeLeaving));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void add(TimeLeavingOfDailyPerformance timeLeaving) {
		KrcdtDaiLeavingWork entity = KrcdtDaiLeavingWork.toEntity(timeLeaving);
		commandProxy().insert(entity);
		commandProxy().insertAll(entity.timeLeavingWorks);
	}
	//
	// @Override
	// public void update(TimeLeavingOfDailyPerformance timeLeaving) {
	// KrcdtDaiLeavingWork entity = KrcdtDaiLeavingWork.toEntity(timeLeaving);
	// commandProxy().update(entity);
	// commandProxy().updateAll(entity.timeLeavingWorks);
	// }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TimeLeavingOfDailyPerformance> finds(List<String> employeeIds, DatePeriod ymd) {
		List<TimeLeavingOfDailyPerformance> result = new ArrayList<>();

		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(internalQuery(ymd, empIds));
		});
		return result;
	}

	@SneakyThrows
	private List<TimeLeavingOfDailyPerformance> internalQuery(DatePeriod datePeriod, List<String> subList) {
		String subIn = NtsStatement.In.createParamsString(subList);

		Map<String, Map<GeneralDate, List<TimeLeavingWork>>> scheTimes = new HashMap<>(); 
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_TIME_LEAVING_WORK WHERE YMD >= ? AND YMD <= ? AND TIME_LEAVING_TYPE = 0 AND SID IN (" + subIn + ")")){
			stmt.setDate(1, Date.valueOf(datePeriod.start().localDate()));
			stmt.setDate(2, Date.valueOf(datePeriod.end().localDate()));
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 3, subList.get(i));
			}
			new NtsResultSet(stmt.executeQuery()).getList(c -> {
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				if(!scheTimes.containsKey(sid)){
					scheTimes.put(sid, new HashMap<>());
				}
				if(!scheTimes.get(sid).containsKey(ymd)) {
					scheTimes.get(sid).put(ymd, new ArrayList<>());
				}
				getCurrent(scheTimes, sid, ymd).add(toDomain(c));
				return null;
			});
		};
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAI_LEAVING_WORK WHERE YMD >= ? AND YMD <= ? AND SID IN (" + subIn + ")")){
			stmt.setDate(1, Date.valueOf(datePeriod.start().localDate()));
			stmt.setDate(2, Date.valueOf(datePeriod.end().localDate()));
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 3, subList.get(i));
			}
			return new NtsResultSet(stmt.executeQuery()).getList(c -> {
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				return new TimeLeavingOfDailyPerformance(sid, new WorkTimes(c.getInt("WORK_TIMES")), getCurrent(scheTimes, sid, ymd), ymd);
			});
		}
	}
	
	private TimeLeavingWork toDomain(NtsResultRecord r) {
		TimeLeavingWork domain = new TimeLeavingWork(new WorkNo(r.getInt("WORK_NO")),
				new TimeActualStamp(
						r.getInt("ATD_ACTUAL_TIME") == null ? null : 
										getWorkStamp(r.getInt("ATD_ACTUAL_TIME"), 
													r.getString("ATD_ACTUAL_PLACE_CODE"), 
													r.getInt("ATD_ACTUAL_SOURCE_INFO")),
						r.getInt("ATD_STAMP_TIME") == null ? null : 
										getWorkStamp(r.getInt("ATD_STAMP_TIME"),
													r.getString("ATD_STAMP_PLACE_CODE"), 
													r.getInt("ATD_STAMP_SOURCE_INFO")),
						r.getInt("ATD_NUMBER_STAMP"),
						r.getInt("ATD_OVERTIME") == null || r.getInt("ATD_LATE_NIGHT_OVERTIME") == null  ?null:
										getOvertimeDeclaration(r.getInt("ATD_OVERTIME"), 
															   r.getInt("ATD_LATE_NIGHT_OVERTIME")),
						r.getInt("ATD_BREAK_START") == null || r.getInt("ATD_BREAK_END") == null  ?null:
										getTimeZone(r.getInt("ATD_BREAK_START"), 
													r.getInt("ATD_BREAK_END"))
						),
				new TimeActualStamp(
						r.getInt("LWK_ACTUAL_TIME") == null ? null : 
										getWorkStamp(r.getInt("LWK_ACTUAL_TIME"), 
													r.getString("LWK_ACTUAL_PLACE_CODE"), 
													r.getInt("LWK_ACTUAL_SOURCE_INFO")),
						r.getInt("LWK_STAMP_TIME") == null ? null : 
										getWorkStamp(r.getInt("LWK_STAMP_TIME"),
													r.getString("LWK_STAMP_PLACE_CODE"), 
													r.getInt("LWK_STAMP_SOURCE_INFO")),
						r.getInt("LWK_NUMBER_STAMP"),
						r.getInt("LWK_OVERTIME") == null || r.getInt("LWK_LATE_NIGHT_OVERTIME") == null  ?null:
							getOvertimeDeclaration(r.getInt("LWK_OVERTIME"), 
												   r.getInt("LWK_LATE_NIGHT_OVERTIME")),
						r.getInt("LWK_BREAK_START") == null || r.getInt("LWK_BREAK_END") == null  ?null:
										getTimeZone(r.getInt("LWK_BREAK_START"), 
													r.getInt("LWK_BREAK_END"))
						)
				);
		return domain;
	}

	private WorkStamp getWorkStamp(Integer time, String placeCode, Integer sourceInfo) {
		return new WorkStamp(
				time == null ? null : new TimeWithDayAttr(time),
				placeCode == null ? null : new WorkLocationCD(placeCode),
				sourceInfo == null ? null : EnumAdaptor.valueOf(sourceInfo, TimeChangeMeans.class));
	}
	private OvertimeDeclaration getOvertimeDeclaration(Integer overTime, Integer overLateNightTime) {
		return new OvertimeDeclaration(
				overTime == null ? null : new AttendanceTime(overTime),
				overLateNightTime == null ? null : new AttendanceTime(overLateNightTime));
	}
	private TimeZone getTimeZone(Integer breakStart, Integer breakEnd) {
		return new TimeZone(
				breakStart == null ? null : new TimeWithDayAttr(breakStart),
				breakEnd == null ? null : new TimeWithDayAttr(breakEnd));
	}

	private <T> List<T> getCurrent(Map<String, Map<GeneralDate, List<T>>> scheTimes,
			String sid, GeneralDate ymd) {
		if(scheTimes.containsKey(sid)){
			if(scheTimes.get(sid).containsKey(ymd)){
				return scheTimes.get(sid).get(ymd);
			}
		}
		return new ArrayList<>();
	}

	@Override
	public void updateFlush(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		this.update(timeLeavingOfDailyPerformance);
		this.getEntityManager().flush();
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @SneakyThrows
	@Override
	public List<TimeLeavingOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
    	List<String> subList = param.keySet().stream().collect(Collectors.toList());
    	List<GeneralDate> subListDate = param.values().stream().flatMap(x -> x.stream()).collect(Collectors.toList());
    	List<TimeLeavingOfDailyPerformance> result = new ArrayList<>();

		CollectionUtil.split(subList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(findListEmpSid(empIds, subListDate));
		});
		return result;
	}

    @SneakyThrows
    private List<TimeLeavingOfDailyPerformance> findListEmpSid(List<String> subList, List<GeneralDate> subListDate) {
    	String subEmp = NtsStatement.In.createParamsString(subList);
    	String subInDate = NtsStatement.In.createParamsString(subListDate);

		Map<String, Map<GeneralDate, List<TimeLeavingWork>>> scheTimes = new HashMap<>(); 
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_TIME_LEAVING_WORK WHERE SID IN (" + subEmp + ")" + " AND YMD IN (" + subInDate + ")" + "AND TIME_LEAVING_TYPE = 0 ")){
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 1, subList.get(i));
			}
			
			for (int i = 0; i < subListDate.size(); i++) {
				stmt.setDate(1 + i + subList.size(),  Date.valueOf(subListDate.get(i).localDate()));
			}
			
			new NtsResultSet(stmt.executeQuery()).getList(c -> {
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				if(!scheTimes.containsKey(sid)){
					scheTimes.put(sid, new HashMap<>());
				}
				if(!scheTimes.get(sid).containsKey(ymd)) {
					scheTimes.get(sid).put(ymd, new ArrayList<>());
				}
				getCurrent(scheTimes, sid, ymd).add(toDomain(c));
				return null;
			});
		};
		try (val stmt = this.connection().prepareStatement("SELECT * FROM KRCDT_DAI_LEAVING_WORK  WHERE SID IN (" + subEmp + ")" + " AND YMD IN (" + subInDate + ")")){
			for (int i = 0; i < subList.size(); i++) {
				stmt.setString(i + 1, subList.get(i));
			}
			
			for (int i = 0; i < subListDate.size(); i++) {
				stmt.setDate(1 + i + subList.size(),  Date.valueOf(subListDate.get(i).localDate()));
			}
			return new NtsResultSet(stmt.executeQuery()).getList(c -> {
				String sid = c.getString("SID");
				GeneralDate ymd = c.getGeneralDate("YMD");
				return new TimeLeavingOfDailyPerformance(sid, new WorkTimes(c.getInt("WORK_TIMES")), getCurrent(scheTimes, sid, ymd), ymd);
			});
		}
	}
    
//	private List<TimeLeavingOfDailyPerformance> toDomainFromJoin(List<Object[]> result) {
//		return result.stream()
//				.collect(Collectors.groupingBy(c1 -> c1[0],
//						Collectors.collectingAndThen(Collectors.toList(),
//								list -> list.stream().filter(c -> c[1] != null).map(c -> (KrcdtTimeLeavingWork) c[1])
//										.collect(Collectors.toList()))))
//				.entrySet().stream()
//				.map(e -> KrcdtDaiLeavingWork.toDomain((KrcdtDaiLeavingWork) e.getKey(), e.getValue()))
//				.collect(Collectors.toList());
//	}
}
