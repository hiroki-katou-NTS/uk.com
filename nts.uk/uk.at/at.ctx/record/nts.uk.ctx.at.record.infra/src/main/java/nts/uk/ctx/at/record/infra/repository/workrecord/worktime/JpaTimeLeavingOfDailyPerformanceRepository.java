package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiLeavingWork;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiTemporaryTime;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWork;

@Stateless
public class JpaTimeLeavingOfDailyPerformanceRepository extends JpaRepository
		implements TimeLeavingOfDailyPerformanceRepository {

	private static final String REMOVE_BY_EMPLOYEE;
	
	private static final String REMOVE_TIME_LEAVING_WORK;

	private static final String DEL_BY_LIST_KEY;

	private static final String FIND_BY_LIST_SID;

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
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDaiLeavingWork a ");
		builderString.append("WHERE a.krcdtDaiLeavingWorkPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiLeavingWorkPK.ymd IN :ymds ");
		FIND_BY_LIST_SID = builderString.toString();

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
	public List<TimeLeavingOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds) {
		return this.queryProxy().query(FIND_BY_LIST_SID, KrcdtDaiLeavingWork.class)
				.setParameter("employeeIds", employeeIds).setParameter("ymds", ymds).getList(f -> f.toDomain());
	}

	@Override
	public Optional<TimeLeavingOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtDaiLeavingWork.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(f -> f.toDomain());
	}
	@Override
	public void update(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		if(timeLeavingOfDailyPerformance==null){
			return;
		}
		
	 Optional<KrcdtDaiTemporaryTime> krcdtDaiTemporaryTimeOptional = this.queryProxy().query(FIND_BY_KEY, KrcdtDaiTemporaryTime.class).setParameter("employeeId", timeLeavingOfDailyPerformance.getEmployeeId())
		.setParameter("ymd", timeLeavingOfDailyPerformance.getYmd()).getSingle();
	 	if(krcdtDaiTemporaryTimeOptional.isPresent()){
	 		KrcdtDaiTemporaryTime krcdtDaiTemporaryTime = krcdtDaiTemporaryTimeOptional.get();
	 		krcdtDaiTemporaryTime.workTimes = timeLeavingOfDailyPerformance.getWorkTimes().v();
	 		List<KrcdtTimeLeavingWork> timeLeavingWorks = krcdtDaiTemporaryTime.timeLeavingWorks;
	 		int size = timeLeavingWorks.size();
	 		for (int i = 0; i < size; i++) {
	 			KrcdtTimeLeavingWork krcdtTimeLeavingWork = timeLeavingWorks.get(i);
	 			List<TimeLeavingWork> timeLeavingWorks2 = timeLeavingOfDailyPerformance.getTimeLeavingWorks();
	 			for (TimeLeavingWork timeLeavingWork : timeLeavingWorks2) {
					if(krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK.workNo == timeLeavingWork.getWorkNo().v().intValue()){
						krcdtTimeLeavingWork.attendanceActualRoudingTime = (timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getActualStamp()!=null && timeLeavingWork.getAttendanceStamp().get().getActualStamp().getAfterRoundingTime()!=null) ?  timeLeavingWork.getAttendanceStamp().get().getActualStamp().getAfterRoundingTime().v().intValue():null;
						krcdtTimeLeavingWork.attendanceActualTime = (timeLeavingWork.getAttendanceStamp().isPresent()&&timeLeavingWork.getAttendanceStamp().get().getActualStamp()!=null&&timeLeavingWork.getAttendanceStamp().get().getActualStamp().getTimeWithDay()!=null) ?timeLeavingWork.getAttendanceStamp().get().getActualStamp().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.attendanceActualPlaceCode = (timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getActualStamp()!=null && timeLeavingWork.getAttendanceStamp().get().getActualStamp().getLocationCode()!=null)?timeLeavingWork.getAttendanceStamp().get().getActualStamp().getLocationCode().v():null;
						krcdtTimeLeavingWork.attendanceActualSourceInfo =(timeLeavingWork.getAttendanceStamp().isPresent()&&timeLeavingWork.getAttendanceStamp().get().getActualStamp()!=null&&timeLeavingWork.getAttendanceStamp().get().getActualStamp().getStampSourceInfo()!=null)? timeLeavingWork.getAttendanceStamp().get().getActualStamp().getStampSourceInfo().value:null;
						krcdtTimeLeavingWork.attendanceStampRoudingTime = (timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()&& timeLeavingWork.getAttendanceStamp().get().getStamp().get().getAfterRoundingTime()!=null)?timeLeavingWork.getAttendanceStamp().get().getStamp().get().getAfterRoundingTime().v().intValue():null;
						krcdtTimeLeavingWork.attendanceStampTime= (timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay()!=null) ? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.attendanceStampPlaceCode = (timeLeavingWork.getAttendanceStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent() && timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode()!=null)? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getLocationCode().v():null;
						krcdtTimeLeavingWork.attendanceStampSourceInfo = (timeLeavingWork.getAttendanceStamp().isPresent()&&timeLeavingWork.getAttendanceStamp().get().getStamp().isPresent()&& timeLeavingWork.getAttendanceStamp().get().getStamp().get().getStampSourceInfo()!=null) ? timeLeavingWork.getAttendanceStamp().get().getStamp().get().getStampSourceInfo().value :null;
						krcdtTimeLeavingWork.attendanceNumberStamp = (timeLeavingWork.getAttendanceStamp().isPresent())? timeLeavingWork.getAttendanceStamp().get().getNumberOfReflectionStamp():null;
						krcdtTimeLeavingWork.leaveWorkActualRoundingTime = ( timeLeavingWork.getLeaveStamp().isPresent() && timeLeavingWork.getLeaveStamp().get().getActualStamp()!=null) ? timeLeavingWork.getLeaveStamp().get().getActualStamp().getAfterRoundingTime().v().intValue(): null;
						krcdtTimeLeavingWork.leaveWorkActualTime = (timeLeavingWork.getLeaveStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getActualStamp()!=null&& timeLeavingWork.getLeaveStamp().get().getActualStamp().getTimeWithDay()!=null)? timeLeavingWork.getLeaveStamp().get().getActualStamp().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.leaveWorkActualPlaceCode =(timeLeavingWork.getLeaveStamp().isPresent()&& timeLeavingWork.getLeaveStamp().get().getActualStamp()!=null&&timeLeavingWork.getLeaveStamp().get().getActualStamp().getLocationCode()!=null)? timeLeavingWork.getLeaveStamp().get().getActualStamp().getLocationCode().v():null;
						krcdtTimeLeavingWork.leaveActualSourceInfo = (timeLeavingWork.getLeaveStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getActualStamp()!=null&&timeLeavingWork.getLeaveStamp().get().getActualStamp().getStampSourceInfo()!=null)?timeLeavingWork.getLeaveStamp().get().getActualStamp().getStampSourceInfo().value:null;
						krcdtTimeLeavingWork.leaveWorkStampRoundingTime = (timeLeavingWork.getLeaveStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().get().getAfterRoundingTime()!=null )? timeLeavingWork.getLeaveStamp().get().getStamp().get().getAfterRoundingTime().v().intValue():null;
						krcdtTimeLeavingWork.leaveWorkStampTime = (timeLeavingWork.getLeaveStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay()!=null)? timeLeavingWork.getLeaveStamp().get().getStamp().get().getTimeWithDay().v().intValue():null;
						krcdtTimeLeavingWork.leaveWorkStampPlaceCode = (timeLeavingWork.getLeaveStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode()!=null)? timeLeavingWork.getLeaveStamp().get().getStamp().get().getLocationCode().v():null;
						krcdtTimeLeavingWork.leaveWorkStampSourceInfo = (timeLeavingWork.getLeaveStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().isPresent()&&timeLeavingWork.getLeaveStamp().get().getStamp().get().getStampSourceInfo()!=null)?timeLeavingWork.getLeaveStamp().get().getStamp().get().getStampSourceInfo().value:null;
						krcdtTimeLeavingWork.leaveWorkNumberStamp = (timeLeavingWork.getLeaveStamp().isPresent())? timeLeavingWork.getLeaveStamp().get().getNumberOfReflectionStamp():null;
					}
				}
			}
	 		this.commandProxy().update(krcdtDaiTemporaryTime);
			this.getEntityManager().flush();
	 	}
	}

	@Override
	public void insert(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance) {
		if(timeLeavingOfDailyPerformance==null){
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
//	@Override
//	public void update(TimeLeavingOfDailyPerformance timeLeaving) {
//		KrcdtDaiLeavingWork entity = KrcdtDaiLeavingWork.toEntity(timeLeaving);
//		commandProxy().update(entity);
//		commandProxy().updateAll(entity.timeLeavingWorks);
//	}

}
