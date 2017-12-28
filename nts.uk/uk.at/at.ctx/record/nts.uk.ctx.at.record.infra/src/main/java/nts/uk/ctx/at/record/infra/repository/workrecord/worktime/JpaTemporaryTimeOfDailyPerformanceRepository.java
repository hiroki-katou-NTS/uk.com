package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiTemporaryTime;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWork;

@Stateless
public class JpaTemporaryTimeOfDailyPerformanceRepository extends JpaRepository
		implements TemporaryTimeOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;
	
	private static final String DEL_BY_LIST_KEY;

	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiTemporaryTime a ");
		builderString.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiTemporaryTimePK.ymd = :ymd ");
		REMOVE_BY_EMPLOYEE = builderString.toString();
		
		builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiTemporaryTime a ");
		builderString.append("WHERE WHERE a.krcdtDaiTemporaryTimePK.employeeId IN :employeeIds ");
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
		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).executeUpdate();
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
	public void update(TemporaryTimeOfDailyPerformance temporaryTimeOfDailyPerformance) {
		if(temporaryTimeOfDailyPerformance==null){
			return;
		}
		
	 Optional<KrcdtDaiTemporaryTime> krcdtDaiTemporaryTimeOptional = this.queryProxy().query(FIND_BY_KEY, KrcdtDaiTemporaryTime.class).setParameter("employeeId", temporaryTimeOfDailyPerformance.getEmployeeId())
		.setParameter("ymd", temporaryTimeOfDailyPerformance.getYmd()).getSingle();
	 	if(krcdtDaiTemporaryTimeOptional.isPresent()){
	 		KrcdtDaiTemporaryTime krcdtDaiTemporaryTime = krcdtDaiTemporaryTimeOptional.get();
	 		krcdtDaiTemporaryTime.workTimes = temporaryTimeOfDailyPerformance.getWorkTimes().v();
	 		List<KrcdtTimeLeavingWork> timeLeavingWorks = krcdtDaiTemporaryTime.timeLeavingWorks;
	 		int size = timeLeavingWorks.size();
	 		for (int i = 0; i < size; i++) {
	 			KrcdtTimeLeavingWork krcdtTimeLeavingWork = timeLeavingWorks.get(i);
	 			List<TimeLeavingWork> timeLeavingWorks2 = temporaryTimeOfDailyPerformance.getTimeLeavingWorks();
	 			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks2) {
					if(krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK.workNo == timeLeavingWork.getWorkNo().v().intValue()){
						krcdtTimeLeavingWork.attendanceActualRoudingTime = (timeLeavingWork.getAttendanceStamp()!=null && timeLeavingWork.getAttendanceStamp().getActualStamp()!=null && timeLeavingWork.getAttendanceStamp().getActualStamp().getAfterRoundingTime()!=null) ?  timeLeavingWork.getAttendanceStamp().getActualStamp().getAfterRoundingTime().v().intValue():null;
						krcdtTimeLeavingWork.attendanceActualTime = (timeLeavingWork.getAttendanceStamp()!=null&&timeLeavingWork.getAttendanceStamp().getActualStamp()!=null&&timeLeavingWork.getAttendanceStamp().getActualStamp().getTimeWithDay()!=null) ?timeLeavingWork.getAttendanceStamp().getActualStamp().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.attendanceActualPlaceCode = (timeLeavingWork.getAttendanceStamp()!=null && timeLeavingWork.getAttendanceStamp().getActualStamp()!=null && timeLeavingWork.getAttendanceStamp().getActualStamp().getLocationCode()!=null)?timeLeavingWork.getAttendanceStamp().getActualStamp().getLocationCode().v():null;
						krcdtTimeLeavingWork.attendanceActualSourceInfo =(timeLeavingWork.getAttendanceStamp()!=null&&timeLeavingWork.getAttendanceStamp().getActualStamp()!=null&&timeLeavingWork.getAttendanceStamp().getActualStamp().getStampSourceInfo()!=null)? timeLeavingWork.getAttendanceStamp().getActualStamp().getStampSourceInfo().value:null;
						krcdtTimeLeavingWork.attendanceStampRoudingTime = (timeLeavingWork.getAttendanceStamp()!=null && timeLeavingWork.getAttendanceStamp().getStamp().isPresent()&& timeLeavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime()!=null)?timeLeavingWork.getAttendanceStamp().getStamp().get().getAfterRoundingTime().v().intValue():null;
						krcdtTimeLeavingWork.attendanceStampTime= (timeLeavingWork.getAttendanceStamp()!=null && timeLeavingWork.getAttendanceStamp().getStamp().isPresent() && timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay()!=null) ? timeLeavingWork.getAttendanceStamp().getStamp().get().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.attendanceStampPlaceCode = (timeLeavingWork.getAttendanceStamp()!=null && timeLeavingWork.getAttendanceStamp().getStamp().isPresent() && timeLeavingWork.getAttendanceStamp().getStamp().get().getLocationCode()!=null)? timeLeavingWork.getAttendanceStamp().getStamp().get().getLocationCode().v():null;
						krcdtTimeLeavingWork.attendanceStampSourceInfo = (timeLeavingWork.getAttendanceStamp()!=null&&timeLeavingWork.getAttendanceStamp().getStamp().isPresent()&& timeLeavingWork.getAttendanceStamp().getStamp().get().getStampSourceInfo()!=null) ? timeLeavingWork.getAttendanceStamp().getStamp().get().getStampSourceInfo().value :null;
						krcdtTimeLeavingWork.attendanceNumberStamp = (timeLeavingWork.getAttendanceStamp()!=null)? timeLeavingWork.getAttendanceStamp().getNumberOfReflectionStamp():null;
						krcdtTimeLeavingWork.leaveWorkActualRoundingTime = timeLeavingWork.getLeaveStamp().getActualStamp().getAfterRoundingTime().v().intValue();
						krcdtTimeLeavingWork.leaveWorkActualTime = (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getActualStamp()!=null&& timeLeavingWork.getLeaveStamp().getActualStamp().getTimeWithDay()!=null)? timeLeavingWork.getLeaveStamp().getActualStamp().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.leaveWorkActualPlaceCode =(timeLeavingWork.getLeaveStamp()!=null&& timeLeavingWork.getLeaveStamp().getActualStamp()!=null&&timeLeavingWork.getLeaveStamp().getActualStamp().getLocationCode()!=null)? timeLeavingWork.getLeaveStamp().getActualStamp().getLocationCode().v():null;
						krcdtTimeLeavingWork.leaveActualSourceInfo = (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getActualStamp()!=null&&timeLeavingWork.getLeaveStamp().getActualStamp().getStampSourceInfo()!=null)?timeLeavingWork.getLeaveStamp().getActualStamp().getStampSourceInfo().value:null;
						krcdtTimeLeavingWork.leaveWorkStampRoundingTime = (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().getStamp().get().getAfterRoundingTime()!=null )? timeLeavingWork.getLeaveStamp().getStamp().get().getAfterRoundingTime().v().intValue():null;
						krcdtTimeLeavingWork.leaveWorkStampTime = (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().getStamp().get().getTimeWithDay()!=null)? timeLeavingWork.getLeaveStamp().getStamp().get().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.leaveWorkStampPlaceCode = (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().getStamp().get().getLocationCode()!=null)? timeLeavingWork.getLeaveStamp().getStamp().get().getLocationCode().v():null;
						krcdtTimeLeavingWork.leaveWorkStampSourceInfo = (timeLeavingWork.getLeaveStamp()!=null&&timeLeavingWork.getLeaveStamp().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().getStamp().get().getStampSourceInfo()!=null)?timeLeavingWork.getLeaveStamp().getStamp().get().getStampSourceInfo().value:null;
						krcdtTimeLeavingWork.leaveWorkNumberStamp = (timeLeavingWork.getLeaveStamp()!=null)? timeLeavingWork.getLeaveStamp().getNumberOfReflectionStamp():null;
					}
				}
			}
	 		this.commandProxy().update(krcdtDaiTemporaryTime);
			this.getEntityManager().flush();
	 	}
		
		
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

//	@Override
//	public void update(TemporaryTimeOfDailyPerformance temporaryTime) {
//		KrcdtDaiTemporaryTime entity = KrcdtDaiTemporaryTime.toEntity(temporaryTime);
//		commandProxy().update(entity);
//		commandProxy().updateAll(entity.timeLeavingWorks);
//	}

}
