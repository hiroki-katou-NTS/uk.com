package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
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
	public void update(TimeLeavingOfDailyPerformance domain) {
		if (domain == null) {
			return;
		}
		KrcdtDaiLeavingWork entity = getDailyLeaving(domain.getEmployeeId(), domain.getYmd());
		List<KrcdtTimeLeavingWork> timeWorks = entity.timeLeavingWorks;
		entity.workTimes = domain.getWorkTimes().v();
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
				if (attendanceStamp.getActualStamp() != null) {
					krcdtTimeLeavingWork.attendanceActualRoudingTime = attendanceStamp.getActualStamp()
							.getAfterRoundingTime() == null ? null
									: attendanceStamp.getActualStamp().getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceActualTime = attendanceStamp.getActualStamp()
							.getTimeWithDay() == null ? null
									: attendanceStamp.getActualStamp().getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceActualPlaceCode = attendanceStamp.getActualStamp()
							.getLocationCode() == null ? null : attendanceStamp.getActualStamp().getLocationCode().v();
					krcdtTimeLeavingWork.attendanceActualSourceInfo = attendanceStamp.getActualStamp()
							.getStampSourceInfo() == null ? 0
									: attendanceStamp.getActualStamp().getStampSourceInfo().value;
				}
				if (attendanceStamp.getStamp().isPresent()) {
					krcdtTimeLeavingWork.attendanceStampRoudingTime = attendanceStamp.getStamp().get()
							.getAfterRoundingTime() == null ? null
									: attendanceStamp.getStamp().get().getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceStampTime = attendanceStamp.getStamp().get().getTimeWithDay() == null
							? null : attendanceStamp.getStamp().get().getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.attendanceStampPlaceCode = attendanceStamp.getStamp().get()
							.getLocationCode() == null ? null : attendanceStamp.getStamp().get().getLocationCode().v();
					krcdtTimeLeavingWork.attendanceStampSourceInfo = attendanceStamp.getStamp().get()
							.getStampSourceInfo() == null ? 0
									: attendanceStamp.getStamp().get().getStampSourceInfo().value;
				}
				krcdtTimeLeavingWork.attendanceNumberStamp = attendanceStamp.getNumberOfReflectionStamp();
			}
			if (c.getLeaveStamp().isPresent()) {
				TimeActualStamp leaveStamp = c.getLeaveStamp().get();
				if (leaveStamp.getActualStamp() != null) {
					krcdtTimeLeavingWork.leaveWorkActualRoundingTime = leaveStamp.getActualStamp()
							.getAfterRoundingTime() == null ? null
									: leaveStamp.getActualStamp().getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkActualTime = leaveStamp.getActualStamp().getTimeWithDay() == null
							? null : leaveStamp.getActualStamp().getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkActualPlaceCode = leaveStamp.getActualStamp()
							.getLocationCode() == null ? null : leaveStamp.getActualStamp().getLocationCode().v();
					krcdtTimeLeavingWork.leaveActualSourceInfo = leaveStamp.getActualStamp()
							.getStampSourceInfo() == null ? 0 : leaveStamp.getActualStamp().getStampSourceInfo().value;
				}
				if (leaveStamp.getStamp().isPresent()) {
					krcdtTimeLeavingWork.leaveWorkStampRoundingTime = leaveStamp.getStamp().get()
							.getAfterRoundingTime() == null ? null
									: leaveStamp.getStamp().get().getAfterRoundingTime().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkStampTime = leaveStamp.getStamp().get().getTimeWithDay() == null
							? null : leaveStamp.getStamp().get().getTimeWithDay().valueAsMinutes();
					krcdtTimeLeavingWork.leaveWorkStampPlaceCode = leaveStamp.getStamp().get().getLocationCode() == null
							? null : leaveStamp.getStamp().get().getLocationCode().v();
					krcdtTimeLeavingWork.leaveWorkStampSourceInfo = leaveStamp.getStamp().get()
							.getStampSourceInfo() == null ? 0 : leaveStamp.getStamp().get().getStampSourceInfo().value;
				}
				krcdtTimeLeavingWork.leaveWorkNumberStamp = leaveStamp.getNumberOfReflectionStamp();
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
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDaiLeavingWork a ");
		query.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiLeavingWorkPK.ymd <= :end AND a.krcdtDaiLeavingWorkPK.ymd >= :start");
		return queryProxy().query(query.toString(), KrcdtDaiLeavingWork.class).setParameter("employeeId", employeeIds)
				.setParameter("start", ymd.start()).setParameter("end", ymd.end()).getList().stream()
				.collect(Collectors.groupingBy(c -> c.krcdtDaiLeavingWorkPK.employeeId + c.krcdtDaiLeavingWorkPK.ymd.toString()))
				.entrySet().stream().map(c -> c.getValue().stream().map(f -> f.toDomain()).collect(Collectors.toList()))
				.flatMap(List::stream).collect(Collectors.toList());
	}

}
