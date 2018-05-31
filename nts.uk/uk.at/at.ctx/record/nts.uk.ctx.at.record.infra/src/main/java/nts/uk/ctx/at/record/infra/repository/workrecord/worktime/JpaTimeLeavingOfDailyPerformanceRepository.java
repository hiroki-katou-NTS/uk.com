package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiLeavingWork;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiLeavingWorkPK;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWork;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWorkPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaTimeLeavingOfDailyPerformanceRepository extends JpaRepository
		implements TimeLeavingOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;

	private static final String REMOVE_TIME_LEAVING_WORK;

	private static final String FIND_BY_KEY;

	private static final String FIND_BY_PERIOD_ORDER_BY_YMD;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtTimeLeavingWork a ");
		builderString.append("WHERE a.krcdtTimeLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtTimeLeavingWorkPK.ymd = :ymd ");
		builderString.append("AND a.krcdtTimeLeavingWorkPK.timeLeavingType = :timeLeavingType ");
		REMOVE_TIME_LEAVING_WORK = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd >= :start ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd <= :end ");
		builderString.append("ORDER BY a.krcdtDaiLeavingWorkPK.ymd ");
		FIND_BY_PERIOD_ORDER_BY_YMD = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_TIME_LEAVING_WORK).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("timeLeavingType", 0).executeUpdate();
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtDaiLeavingWork.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(f -> f.toDomain());
	}

	@Override
	public List<TimeLeavingOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		return this.queryProxy().query(FIND_BY_PERIOD_ORDER_BY_YMD, KrcdtDaiLeavingWork.class)
				.setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start())
				.setParameter("end", datePeriod.end())
				.getList(f -> f.toDomain());
	}
	
	@Override
	public void update(TimeLeavingOfDailyPerformance domain) {
		if (domain == null) {
			return;
		}
		KrcdtDaiLeavingWork entity = getDailyLeaving(domain.getEmployeeId(), domain.getYmd());
		List<KrcdtTimeLeavingWork> timeWorks = entity.timeLeavingWorks;
		entity.workTimes = domain.getWorkTimes() == null ? null : domain.getWorkTimes().v();
		domain.getTimeLeavingWorks().stream().forEach(c -> {
			KrcdtTimeLeavingWork krcdtTimeLeavingWork = timeWorks.stream()
					.filter(x -> x.krcdtTimeLeavingWorkPK.workNo == c.getWorkNo().v()).findFirst().orElse(null);
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
				if (attendanceActualS != null) {
					krcdtTimeLeavingWork.attendanceActualRoudingTime = attendanceActualS.getAfterRoundingTime() == null ? null
									: attendanceActualS.getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceActualTime = attendanceActualS.getTimeWithDay() == null ? null
									: attendanceActualS.getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceActualPlaceCode = !attendanceActualS.getLocationCode().isPresent() 
							? null : attendanceActualS.getLocationCode().get().v();
					krcdtTimeLeavingWork.attendanceActualSourceInfo = attendanceActualS.getStampSourceInfo() == null 
							? 0 : attendanceActualS.getStampSourceInfo().value;
				} else {
					krcdtTimeLeavingWork.attendanceActualRoudingTime = null;
					krcdtTimeLeavingWork.attendanceActualTime = null;
					krcdtTimeLeavingWork.attendanceActualPlaceCode = null;
					krcdtTimeLeavingWork.attendanceActualSourceInfo = null;
				}
				if (attendanceS != null) {
					krcdtTimeLeavingWork.attendanceStampRoudingTime = attendanceS.getAfterRoundingTime() == null ? null
									: attendanceS.getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceStampTime = attendanceS.getTimeWithDay() == null
							? null : attendanceS.getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceStampPlaceCode = !attendanceS.getLocationCode().isPresent() 
							? null : attendanceS.getLocationCode().get().v();
					krcdtTimeLeavingWork.attendanceStampSourceInfo = attendanceS.getStampSourceInfo() == null 
							? 0 : attendanceS.getStampSourceInfo().value;
				}else {
					krcdtTimeLeavingWork.attendanceStampRoudingTime = null;
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
				if (as != null) {
					krcdtTimeLeavingWork.leaveWorkActualRoundingTime = as
							.getAfterRoundingTime() == null ? null
									: as.getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkActualTime = as.getTimeWithDay() == null
							? null : as.getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkActualPlaceCode = !as
							.getLocationCode().isPresent() ? null : as.getLocationCode().get().v();
					krcdtTimeLeavingWork.leaveActualSourceInfo = as
							.getStampSourceInfo() == null ? 0 : as.getStampSourceInfo().value;
				}else {
					krcdtTimeLeavingWork.leaveWorkActualRoundingTime = null;
					krcdtTimeLeavingWork.leaveWorkActualTime = null;
					krcdtTimeLeavingWork.leaveWorkActualPlaceCode = null;
					krcdtTimeLeavingWork.leaveActualSourceInfo = null;
				}
				if (s != null) {
					krcdtTimeLeavingWork.leaveWorkStampRoundingTime = s
							.getAfterRoundingTime() == null ? null
									: s.getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkStampTime = s.getTimeWithDay() == null
							? null : s.getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkStampPlaceCode = !s
							.getLocationCode().isPresent() ? null : s.getLocationCode().get().v();
					krcdtTimeLeavingWork.leaveWorkStampSourceInfo = s
							.getStampSourceInfo() == null ? 0 : s.getStampSourceInfo().value;
				}else {
					krcdtTimeLeavingWork.leaveWorkStampRoundingTime = null;
					krcdtTimeLeavingWork.leaveWorkStampTime = null;
					krcdtTimeLeavingWork.leaveWorkStampPlaceCode = null;
					krcdtTimeLeavingWork.leaveWorkStampSourceInfo = null;
				}
				krcdtTimeLeavingWork.leaveWorkNumberStamp = ls.getNumberOfReflectionStamp();
				
			}
			krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK.timeLeavingType = 0;
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
		this.getEntityManager().flush();
	}

	private KrcdtDaiLeavingWork getDailyLeaving(String employee, GeneralDate date) {
		KrcdtDaiLeavingWork krcdtDaiTemporaryTime = this.queryProxy().query(FIND_BY_KEY, KrcdtDaiLeavingWork.class)
				.setParameter("employeeId", employee).setParameter("ymd", date).getSingle().orElse(null);
		if (krcdtDaiTemporaryTime == null) {
			krcdtDaiTemporaryTime = new KrcdtDaiLeavingWork();
			krcdtDaiTemporaryTime.krcdtDaiLeavingWorkPK = new KrcdtDaiLeavingWorkPK(employee, date);
			krcdtDaiTemporaryTime.timeLeavingWorks = new ArrayList<>();
		}
		return krcdtDaiTemporaryTime;
	}

	@Override
	public void insert(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		if (timeLeavingOfDailyPerformance == null) {
			return;
		}
		this.commandProxy().insert(KrcdtDaiLeavingWork.toEntity(timeLeavingOfDailyPerformance));
		this.getEntityManager().flush();
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

	@Override
	public List<TimeLeavingOfDailyPerformance> finds(List<String> employeeIds, DatePeriod ymd) {
		List<TimeLeavingOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDaiLeavingWork a ");
		query.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiLeavingWorkPK.ymd <= :end AND a.krcdtDaiLeavingWorkPK.ymd >= :start");
		TypedQueryWrapper<KrcdtDaiLeavingWork> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiLeavingWork.class);
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
							.setParameter("start", ymd.start())
							.setParameter("end", ymd.end())
							.getList().stream().map(f -> f.toDomain()).collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public void updateFlush(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		this.update(timeLeavingOfDailyPerformance);
		this.getEntityManager().flush();		
	}

	@Override
	public List<TimeLeavingOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<TimeLeavingOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDaiLeavingWork a ");
		query.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiLeavingWorkPK.ymd IN :date");
		TypedQueryWrapper<KrcdtDaiLeavingWork> tQuery=  this.queryProxy().query(query.toString(), KrcdtDaiLeavingWork.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
							.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
							.getList().stream()
							.filter(c -> p.get(c.krcdtDaiLeavingWorkPK.employeeId).contains(c.krcdtDaiLeavingWorkPK.ymd))
							.map(f -> f.toDomain()).collect(Collectors.toList()));
		});
		return result;
	}

}
