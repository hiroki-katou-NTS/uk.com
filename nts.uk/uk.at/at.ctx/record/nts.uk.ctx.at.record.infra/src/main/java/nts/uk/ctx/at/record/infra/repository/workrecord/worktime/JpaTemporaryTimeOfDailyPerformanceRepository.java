package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiTemporaryTime;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiTemporaryTimePK;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWork;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWorkPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaTemporaryTimeOfDailyPerformanceRepository extends JpaRepository
		implements TemporaryTimeOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;
	
	private static final String REMOVE_TIME_LEAVING_WORK;
	
	private static final String DEL_BY_LIST_KEY;

	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiTemporaryTime a ");
		builderString.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiTemporaryTimePK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtTimeLeavingWork a ");
		builderString.append("WHERE a.krcdtTimeLeavingWorkPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtTimeLeavingWorkPK.ymd = :ymd ");
		builderString.append("AND a.krcdtTimeLeavingWorkPK.timeLeavingType = :timeLeavingType ");
		REMOVE_TIME_LEAVING_WORK = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiTemporaryTime a ");
		builderString.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiTemporaryTimePK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiTemporaryTime a ");
		builderString.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiTemporaryTimePK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();
	}

	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		this.getEntityManager().createQuery(REMOVE_TIME_LEAVING_WORK).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).setParameter("timeLeavingType", 1).executeUpdate();
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		this.getEntityManager().createQuery(DEL_BY_LIST_KEY).setParameter("employeeIds", employeeIds)
		.setParameter("processingYmds", ymds).executeUpdate();	
	}

	@Override
	public Optional<TemporaryTimeOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtDaiTemporaryTime.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(f -> f.toDomain());
	}
	
	@Override
	public void update(TemporaryTimeOfDailyPerformance domain) {
		if(domain==null){
			return;
		}
		
		KrcdtDaiTemporaryTime krcdtDaiTemporaryTime = getDailyTemporary(domain.getEmployeeId(), domain.getYmd());
		
		List<KrcdtTimeLeavingWork> timeWorks = krcdtDaiTemporaryTime.timeLeavingWorks;
 		krcdtDaiTemporaryTime.workTimes = domain.getWorkTimes().v();
 		domain.getTimeLeavingWorks().stream().forEach(c -> {
 			KrcdtTimeLeavingWork krcdtTimeLeavingWork = timeWorks.stream()
 					.filter(x -> x.krcdtTimeLeavingWorkPK.workNo == c.getWorkNo().v()).findFirst().orElse(null);
 			boolean isNew = krcdtTimeLeavingWork == null;
 			if(isNew){
 				krcdtTimeLeavingWork = new KrcdtTimeLeavingWork();
 				krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK = new KrcdtTimeLeavingWorkPK(domain.getEmployeeId(), c.getWorkNo().v(), domain.getYmd(), 0);
 			}
 			if(c.getAttendanceStamp().isPresent()){
 				TimeActualStamp attendanceStamp = c.getAttendanceStamp().get();
 				if(attendanceStamp.getActualStamp() != null){
 					krcdtTimeLeavingWork.attendanceActualRoudingTime = attendanceStamp.getActualStamp().getAfterRoundingTime() == null 
 							? null : attendanceStamp.getActualStamp().getAfterRoundingTime().valueAsMinutes();
 	 				krcdtTimeLeavingWork.attendanceActualTime = attendanceStamp.getActualStamp().getTimeWithDay() == null 
 	 						? null : attendanceStamp.getActualStamp().getTimeWithDay().valueAsMinutes();
 	 				krcdtTimeLeavingWork.attendanceActualPlaceCode = attendanceStamp.getActualStamp().getLocationCode() == null ? null 
 	 						: attendanceStamp.getActualStamp().getLocationCode().v();
 	 				krcdtTimeLeavingWork.attendanceActualSourceInfo = attendanceStamp.getActualStamp().getStampSourceInfo() == null ? 0 
 	 						: attendanceStamp.getActualStamp().getStampSourceInfo().value;
 				}
 				if(attendanceStamp.getStamp().isPresent()){
 					krcdtTimeLeavingWork.attendanceStampRoudingTime = attendanceStamp.getStamp().get().getAfterRoundingTime() == null 
 							? null : attendanceStamp.getStamp().get().getAfterRoundingTime().valueAsMinutes();
 	 				krcdtTimeLeavingWork.attendanceStampTime= attendanceStamp.getStamp().get().getTimeWithDay() == null 
 	 						? null : attendanceStamp.getStamp().get().getTimeWithDay().valueAsMinutes();
 	 				krcdtTimeLeavingWork.attendanceStampPlaceCode = attendanceStamp.getStamp().get().getLocationCode() == null ? null 
 	 						: attendanceStamp.getStamp().get().getLocationCode().v();
 	 				krcdtTimeLeavingWork.attendanceStampSourceInfo = attendanceStamp.getStamp().get().getStampSourceInfo() == null ? 0 
 	 						: attendanceStamp.getStamp().get().getStampSourceInfo().value;
 				}
 				krcdtTimeLeavingWork.attendanceNumberStamp = attendanceStamp.getNumberOfReflectionStamp();
 			}
 			if(c.getLeaveStamp().isPresent()){
 				TimeActualStamp leaveStamp = c.getLeaveStamp().get();
 				if(leaveStamp.getActualStamp() != null){
 					krcdtTimeLeavingWork.leaveWorkActualRoundingTime = leaveStamp.getActualStamp().getAfterRoundingTime() == null 
 							? null : leaveStamp.getActualStamp().getAfterRoundingTime().valueAsMinutes();
 	 				krcdtTimeLeavingWork.leaveWorkActualTime = leaveStamp.getActualStamp().getTimeWithDay() == null 
 	 						? null : leaveStamp.getActualStamp().getTimeWithDay().valueAsMinutes();
 	 				krcdtTimeLeavingWork.leaveWorkActualPlaceCode = leaveStamp.getActualStamp().getLocationCode() == null ? null 
 	 						: leaveStamp.getActualStamp().getLocationCode().v();
 	 				krcdtTimeLeavingWork.leaveActualSourceInfo = leaveStamp.getActualStamp().getStampSourceInfo() == null ? 0 
 	 						: leaveStamp.getActualStamp().getStampSourceInfo().value;
 				}
 				if(leaveStamp.getStamp().isPresent()){
 					krcdtTimeLeavingWork.leaveWorkStampRoundingTime = leaveStamp.getStamp().get().getAfterRoundingTime() == null 
 							? null : leaveStamp.getStamp().get().getAfterRoundingTime().valueAsMinutes();
 	 				krcdtTimeLeavingWork.leaveWorkStampTime= leaveStamp.getStamp().get().getTimeWithDay() == null 
 	 						? null : leaveStamp.getStamp().get().getTimeWithDay().valueAsMinutes();
 	 				krcdtTimeLeavingWork.leaveWorkStampPlaceCode = leaveStamp.getStamp().get().getLocationCode() == null ? null 
 	 						: leaveStamp.getStamp().get().getLocationCode().v();
 	 				krcdtTimeLeavingWork.leaveWorkStampSourceInfo = leaveStamp.getStamp().get().getStampSourceInfo() == null ? 0 
 	 						: leaveStamp.getStamp().get().getStampSourceInfo().value;
 				}
 				krcdtTimeLeavingWork.leaveWorkNumberStamp = leaveStamp.getNumberOfReflectionStamp();
 			}
 			krcdtTimeLeavingWork.daiTemporaryTime = krcdtDaiTemporaryTime;
 			krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK.timeLeavingType = 1;
 			if(isNew){
 				timeWorks.add(krcdtTimeLeavingWork);
 			}
 		});
 		krcdtDaiTemporaryTime.timeLeavingWorks = timeWorks.isEmpty() ? null : timeWorks;
 		this.commandProxy().update(krcdtDaiTemporaryTime);
 		if(!timeWorks.isEmpty()){
 			this.commandProxy().updateAll(krcdtDaiTemporaryTime.timeLeavingWorks);
 		}
		this.getEntityManager().flush();
	 	
	}

	private KrcdtDaiTemporaryTime getDailyTemporary(String employee, GeneralDate date) {
		KrcdtDaiTemporaryTime krcdtDaiTemporaryTime = this.queryProxy().query(FIND_BY_KEY, KrcdtDaiTemporaryTime.class)
				.setParameter("employeeId", employee)
			.setParameter("ymd", date).getSingle().orElse(null);
		if(krcdtDaiTemporaryTime == null){
			krcdtDaiTemporaryTime = new KrcdtDaiTemporaryTime();
			krcdtDaiTemporaryTime.krcdtDaiTemporaryTimePK = new KrcdtDaiTemporaryTimePK(employee, date);
			krcdtDaiTemporaryTime.timeLeavingWorks = new ArrayList<>();
		}
		return krcdtDaiTemporaryTime;
	}

	@Override
	public void insert(TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {
		if(temporaryTimeOfDailyPerformance==null){
			return;
		}
		this.commandProxy().insert(KrcdtDaiTemporaryTime.toEntity(temporaryTimeOfDailyPerformance));
		this.getEntityManager().flush();
	}

	@Override
	public void add(TemporaryTimeOfDailyPerformance temporaryTime) {
		KrcdtDaiTemporaryTime entity = KrcdtDaiTemporaryTime.toEntity(temporaryTime);
		commandProxy().insert(entity);
		commandProxy().insertAll(entity.timeLeavingWorks);
	}

	@Override
	public List<TemporaryTimeOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDaiTemporaryTime a ");
		query.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId = :employeeId ");
		query.append("AND a.krcdtDaiTemporaryTimePK.ymd >= :start ");
		query.append("AND a.krcdtDaiTemporaryTimePK.ymd <= :end ");
		query.append("ORDER BY a.krcdtDaiTemporaryTimePK.ymd ");
		return queryProxy().query(query.toString(), KrcdtDaiTemporaryTime.class)
				.setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start())
				.setParameter("end", datePeriod.end())
				.getList().stream()
				.map(f -> f.toDomain()).collect(Collectors.toList());
	}
	
	@Override
	public List<TemporaryTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDaiTemporaryTime a ");
		query.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId IN :employeeId ");
		query.append("AND a.krcdtDaiTemporaryTimePK.ymd <= :end AND a.krcdtDaiTemporaryTimePK.ymd >= :start");
		return queryProxy().query(query.toString(), KrcdtDaiTemporaryTime.class).setParameter("employeeId", employeeId)
				.setParameter("start", ymd.start()).setParameter("end", ymd.end()).getList().stream()
				.map(f -> f.toDomain()).collect(Collectors.toList());
	}

//	@Override
//	public void update(TemporaryTimeOfDailyPerformance temporaryTime) {
//		KrcdtDaiTemporaryTime entity = KrcdtDaiTemporaryTime.toEntity(temporaryTime);
//		commandProxy().update(entity);
//		commandProxy().updateAll(entity.timeLeavingWorks);
//	}

}
