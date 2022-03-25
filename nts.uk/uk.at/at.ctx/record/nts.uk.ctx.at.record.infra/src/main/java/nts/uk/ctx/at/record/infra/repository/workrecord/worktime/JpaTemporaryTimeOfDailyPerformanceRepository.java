package nts.uk.ctx.at.record.infra.repository.workrecord.worktime;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDayTsTemporary;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDaiTemporaryTimePK;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDayTsAtdStmp;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtTimeLeavingWorkPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.arc.time.calendar.period.DatePeriod;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class JpaTemporaryTimeOfDailyPerformanceRepository extends JpaRepository
		implements TemporaryTimeOfDailyPerformanceRepository {
	
	private static final String DEL_BY_LIST_KEY;

	private static final String FIND_BY_KEY;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDayTsTemporary a ");
		builderString.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDaiTemporaryTimePK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDayTsTemporary a ");
		builderString.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDaiTemporaryTimePK.ymd = :ymd ");
		FIND_BY_KEY = builderString.toString();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void delete(String employeeId, GeneralDate ymd) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAY_TS_ATD_STMP Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + ymd + "'" + "and TIME_LEAVING_TYPE = 1" ;
		String daiLeavingWorkQuery = "Delete From KRCDT_DAY_TS_TEMPORARY Where SID = " + "'" + employeeId + "'" + " and YMD = " + "'" + ymd + "'" ;
		try {
			con.createStatement().executeUpdate(sqlQuery);
			con.createStatement().executeUpdate(daiLeavingWorkQuery);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
//		this.getEntityManager().createQuery(REMOVE_TIME_LEAVING_WORK).setParameter("employeeId", employeeId)
//				.setParameter("ymd", ymd).setParameter("timeLeavingType", 1).executeUpdate();
//		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEE).setParameter("employeeId", employeeId)
//				.setParameter("ymd", ymd).executeUpdate();
//		this.getEntityManager().flush();
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<TemporaryTimeOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd) {
		return this.queryProxy().query(FIND_BY_KEY, KrcdtDayTsTemporary.class).setParameter("employeeId", employeeId)
				.setParameter("ymd", ymd).getSingle(f -> f.toDomain());
	}
	
	@Override
	public void update(TemporaryTimeOfDailyPerformance domain) {
		if(domain==null){
			return;
		}
		
		KrcdtDayTsTemporary krcdtDaiTemporaryTime = getDailyTemporary(domain.getEmployeeId(), domain.getYmd());
		
		List<KrcdtDayTsAtdStmp> timeWorks = krcdtDaiTemporaryTime.timeLeavingWorks;
 		domain.getAttendance().getTimeLeavingWorks().stream().forEach(c -> {
 			KrcdtDayTsAtdStmp krcdtTimeLeavingWork = timeWorks.stream()
 					.filter(x -> x.krcdtTimeLeavingWorkPK.workNo == c.getWorkNo().v()
 								&& x.krcdtTimeLeavingWorkPK.timeLeavingType == 1).findFirst().orElse(null);
 			boolean isNew = krcdtTimeLeavingWork == null;
 			if(isNew){
 				krcdtTimeLeavingWork = new KrcdtDayTsAtdStmp();
 				krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK = new KrcdtTimeLeavingWorkPK(domain.getEmployeeId(), c.getWorkNo().v(), domain.getYmd(), 1);
 			}
 			if(c.getAttendanceStamp().isPresent()){
 				TimeActualStamp attendanceStamp = c.getAttendanceStamp().get();
 				WorkStamp attendActualStamp = attendanceStamp.getActualStamp().orElse(null);
 				WorkStamp attendStamp = attendanceStamp.getStamp().orElse(null);
 				if(attendActualStamp != null){
 	 				krcdtTimeLeavingWork.attendanceActualTime = attendActualStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? attendActualStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtTimeLeavingWork.attendanceActualPlaceCode = !attendActualStamp.getLocationCode().isPresent() ? null 
 	 						: attendActualStamp.getLocationCode().get().v();
 	 				krcdtTimeLeavingWork.attendanceActualSourceInfo = attendActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: attendActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				if(attendStamp != null){
 	 				krcdtTimeLeavingWork.attendanceStampTime= attendStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? attendStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtTimeLeavingWork.attendanceStampPlaceCode = !attendStamp.getLocationCode().isPresent() ? null 
 	 						: attendStamp.getLocationCode().get().v();
 	 				krcdtTimeLeavingWork.attendanceStampSourceInfo = attendStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: attendStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				krcdtTimeLeavingWork.attendanceNumberStamp = attendanceStamp.getNumberOfReflectionStamp();
 			}
 			if(c.getLeaveStamp().isPresent()){
 				WorkStamp leaveActualStamp = c.getLeaveStamp().get().getActualStamp().orElse(null);
 				WorkStamp leaveStamp = c.getLeaveStamp().get().getStamp().orElse(null);
 				if(leaveActualStamp != null){
 	 				krcdtTimeLeavingWork.leaveWorkActualTime = leaveActualStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? leaveActualStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtTimeLeavingWork.leaveWorkActualPlaceCode = !leaveActualStamp.getLocationCode().isPresent() ? null 
 	 						: leaveActualStamp.getLocationCode().get().v();
 	 				krcdtTimeLeavingWork.leaveActualSourceInfo = leaveActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: leaveActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				if(leaveStamp != null){
 	 				krcdtTimeLeavingWork.leaveWorkStampTime= leaveStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? leaveStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtTimeLeavingWork.leaveWorkStampPlaceCode = !leaveStamp.getLocationCode().isPresent() ? null 
 	 						: leaveStamp.getLocationCode().get().v();
 	 				krcdtTimeLeavingWork.leaveWorkStampSourceInfo = leaveStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: leaveStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				krcdtTimeLeavingWork.leaveWorkNumberStamp = c.getLeaveStamp().get().getNumberOfReflectionStamp();
 			}
 			krcdtTimeLeavingWork.daiTemporaryTime = krcdtDaiTemporaryTime;
// 			krcdtTimeLeavingWork.krcdtTimeLeavingWorkPK.timeLeavingType = 1;
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

	private KrcdtDayTsTemporary getDailyTemporary(String employee, GeneralDate date) {
		KrcdtDayTsTemporary krcdtDaiTemporaryTime = this.queryProxy().query(FIND_BY_KEY, KrcdtDayTsTemporary.class)
				.setParameter("employeeId", employee)
			.setParameter("ymd", date).getSingle().orElse(null);
		if(krcdtDaiTemporaryTime == null){
			krcdtDaiTemporaryTime = new KrcdtDayTsTemporary();
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
		this.commandProxy().insert(KrcdtDayTsTemporary.toEntity(temporaryTimeOfDailyPerformance));
		this.getEntityManager().flush();
	}

	@Override
	public void add(TemporaryTimeOfDailyPerformance temporaryTime) {
		KrcdtDayTsTemporary entity = KrcdtDayTsTemporary.toEntity(temporaryTime);
		commandProxy().insert(entity);
		commandProxy().insertAll(entity.timeLeavingWorks);
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TemporaryTimeOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDayTsTemporary a ");
		query.append("WHERE a.krcdtDaiTemporaryTimePK.employeeId = :employeeId ");
		query.append("AND a.krcdtDaiTemporaryTimePK.ymd >= :start ");
		query.append("AND a.krcdtDaiTemporaryTimePK.ymd <= :end ");
		query.append("ORDER BY a.krcdtDaiTemporaryTimePK.ymd ");
		return queryProxy().query(query.toString(), KrcdtDayTsTemporary.class)
				.setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start())
				.setParameter("end", datePeriod.end())
				.getList().stream()
				.map(f -> f.toDomain()).collect(Collectors.toList());
	}
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TemporaryTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		List<Object[]> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a, c from KrcdtDayTsTemporary a LEFT JOIN a.timeLeavingWorks c ");
		query.append(" WHERE a.krcdtDaiTemporaryTimePK.employeeId IN :employeeId ");
		query.append(" AND a.krcdtDaiTemporaryTimePK.ymd <= :end AND a.krcdtDaiTemporaryTimePK.ymd >= :start");
		TypedQueryWrapper<Object[]> tQuery=  this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
					.setParameter("start", ymd.start())
					.setParameter("end", ymd.end())
					.getList());
		});
		return toDomainFromJoin(result);
	}
	
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<TemporaryTimeOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<Object[]> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a, c from KrcdtDayTsTemporary a LEFT JOIN a.timeLeavingWorks c ");
		query.append(" WHERE a.krcdtDaiTemporaryTimePK.employeeId IN :employeeId ");
		query.append(" AND a.krcdtDaiTemporaryTimePK.ymd IN :date");
		TypedQueryWrapper<Object[]> tQuery=  this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
					.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
					.getList().stream()
					.filter(c -> {
						KrcdtDayTsTemporary af = (KrcdtDayTsTemporary) c[0];
						return p.get(af.krcdtDaiTemporaryTimePK.employeeId).contains(af.krcdtDaiTemporaryTimePK.ymd);
					}).collect(Collectors.toList()));
		});
		return toDomainFromJoin(result);
	}

	private List<TemporaryTimeOfDailyPerformance> toDomainFromJoin(List<Object[]> result) {
		return result.stream().collect(Collectors.groupingBy(c1 -> c1[0], Collectors.collectingAndThen(Collectors.toList(), 
					list -> list.stream().filter(c -> c[1] != null).map(c -> (KrcdtDayTsAtdStmp) c[1]).collect(Collectors.toList()))))
				.entrySet().stream().map(e -> KrcdtDayTsTemporary.toDomain((KrcdtDayTsTemporary) e.getKey(), e.getValue()))
				.collect(Collectors.toList());
	}

//	@Override
//	public void update(TemporaryTimeOfDailyPerformance temporaryTime) {
//		KrcdtDayTsTemporary entity = KrcdtDayTsTemporary.toEntity(temporaryTime);
//		commandProxy().update(entity);
//		commandProxy().updateAll(entity.timeLeavingWorks);
//	}

}
