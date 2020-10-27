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
import javax.inject.Inject;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TemporaryTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDayTsTemporary;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDayTsTemporaryPK;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDayTsAtdStmp;
import nts.uk.ctx.at.record.infra.entity.worktime.KrcdtDayTsAtdStmpPK;
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
		builderString.append("WHERE a.krcdtDayTsTemporaryPK.employeeId IN :employeeIds ");
		builderString.append("AND a.krcdtDayTsTemporaryPK.ymd IN :ymds ");
		DEL_BY_LIST_KEY = builderString.toString();

		builderString = new StringBuilder();
		builderString.append("SELECT a ");
		builderString.append("FROM KrcdtDayTsTemporary a ");
		builderString.append("WHERE a.krcdtDayTsTemporaryPK.employeeId = :employeeId ");
		builderString.append("AND a.krcdtDayTsTemporaryPK.ymd = :ymd ");
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
		
		KrcdtDayTsTemporary krcdtDayTsTemporary = getDailyTemporary(domain.getEmployeeId(), domain.getYmd());
		
		List<KrcdtDayTsAtdStmp> timeWorks = krcdtDayTsTemporary.timeLeavingWorks;
 		krcdtDayTsTemporary.workTimes = domain.getAttendance().getWorkTimes().v();
 		domain.getAttendance().getTimeLeavingWorks().stream().forEach(c -> {
 			KrcdtDayTsAtdStmp krcdtDayTsAtdStmp = timeWorks.stream()
 					.filter(x -> x.krcdtDayTsAtdStmpPK.workNo == c.getWorkNo().v()
 								&& x.krcdtDayTsAtdStmpPK.timeLeavingType == 1).findFirst().orElse(null);
 			boolean isNew = krcdtDayTsAtdStmp == null;
 			if(isNew){
 				krcdtDayTsAtdStmp = new KrcdtDayTsAtdStmp();
 				krcdtDayTsAtdStmp.krcdtDayTsAtdStmpPK = new KrcdtDayTsAtdStmpPK(domain.getEmployeeId(), c.getWorkNo().v(), domain.getYmd(), 1);
 			}
 			if(c.getAttendanceStamp().isPresent()){
 				TimeActualStamp attendanceStamp = c.getAttendanceStamp().get();
 				WorkStamp attendActualStamp = attendanceStamp.getActualStamp().orElse(null);
 				WorkStamp attendStamp = attendanceStamp.getStamp().orElse(null);
 				if(attendActualStamp != null){
 					krcdtDayTsAtdStmp.attendanceActualRoudingTime = attendActualStamp.getAfterRoundingTime() == null 
 							? null : attendActualStamp.getAfterRoundingTime().valueAsMinutes();
 	 				krcdtDayTsAtdStmp.attendanceActualTime = attendActualStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? attendActualStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtDayTsAtdStmp.attendanceActualPlaceCode = !attendActualStamp.getLocationCode().isPresent() ? null 
 	 						: attendActualStamp.getLocationCode().get().v();
 	 				krcdtDayTsAtdStmp.attendanceActualSourceInfo = attendActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: attendActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				if(attendStamp != null){
 					krcdtDayTsAtdStmp.attendanceStampRoudingTime = attendStamp.getAfterRoundingTime() == null 
 							? null : attendStamp.getAfterRoundingTime().valueAsMinutes();
 	 				krcdtDayTsAtdStmp.attendanceStampTime= attendStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? attendStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtDayTsAtdStmp.attendanceStampPlaceCode = !attendStamp.getLocationCode().isPresent() ? null 
 	 						: attendStamp.getLocationCode().get().v();
 	 				krcdtDayTsAtdStmp.attendanceStampSourceInfo = attendStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: attendStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				krcdtDayTsAtdStmp.attendanceNumberStamp = attendanceStamp.getNumberOfReflectionStamp();
 			}
 			if(c.getLeaveStamp().isPresent()){
 				WorkStamp leaveActualStamp = c.getLeaveStamp().get().getActualStamp().orElse(null);
 				WorkStamp leaveStamp = c.getLeaveStamp().get().getStamp().orElse(null);
 				if(leaveActualStamp != null){
 					krcdtDayTsAtdStmp.leaveWorkActualRoundingTime = leaveActualStamp.getAfterRoundingTime() == null 
 							? null : leaveActualStamp.getAfterRoundingTime().valueAsMinutes();
 	 				krcdtDayTsAtdStmp.leaveWorkActualTime = leaveActualStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? leaveActualStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtDayTsAtdStmp.leaveWorkActualPlaceCode = !leaveActualStamp.getLocationCode().isPresent() ? null 
 	 						: leaveActualStamp.getLocationCode().get().v();
 	 				krcdtDayTsAtdStmp.leaveActualSourceInfo = leaveActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: leaveActualStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				if(leaveStamp != null){
 					krcdtDayTsAtdStmp.leaveWorkStampRoundingTime = leaveStamp.getAfterRoundingTime() == null 
 							? null : leaveStamp.getAfterRoundingTime().valueAsMinutes();
 	 				krcdtDayTsAtdStmp.leaveWorkStampTime= leaveStamp.getTimeDay().getTimeWithDay().isPresent() 
 	 						? leaveStamp.getTimeDay().getTimeWithDay().get().valueAsMinutes() : null;
 	 				krcdtDayTsAtdStmp.leaveWorkStampPlaceCode = !leaveStamp.getLocationCode().isPresent() ? null 
 	 						: leaveStamp.getLocationCode().get().v();
 	 				krcdtDayTsAtdStmp.leaveWorkStampSourceInfo = leaveStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans() == null ? 0 
 	 						: leaveStamp.getTimeDay().getReasonTimeChange().getTimeChangeMeans().value;
 				}
 				krcdtDayTsAtdStmp.leaveWorkNumberStamp = c.getLeaveStamp().get().getNumberOfReflectionStamp();
 			}
 			krcdtDayTsAtdStmp.daiTemporaryTime = krcdtDayTsTemporary;
// 			krcdtDayTsAtdStmp.krcdtDayTsAtdStmpPK.timeLeavingType = 1;
 			if(isNew){
 				timeWorks.add(krcdtDayTsAtdStmp);
 			}
 		});
 		krcdtDayTsTemporary.timeLeavingWorks = timeWorks.isEmpty() ? null : timeWorks;
 		this.commandProxy().update(krcdtDayTsTemporary);
 		if(!timeWorks.isEmpty()){
 			this.commandProxy().updateAll(krcdtDayTsTemporary.timeLeavingWorks);
 		}
		this.getEntityManager().flush();
	 	
	}

	private KrcdtDayTsTemporary getDailyTemporary(String employee, GeneralDate date) {
		KrcdtDayTsTemporary krcdtDayTsTemporary = this.queryProxy().query(FIND_BY_KEY, KrcdtDayTsTemporary.class)
				.setParameter("employeeId", employee)
			.setParameter("ymd", date).getSingle().orElse(null);
		if(krcdtDayTsTemporary == null){
			krcdtDayTsTemporary = new KrcdtDayTsTemporary();
			krcdtDayTsTemporary.krcdtDayTsTemporaryPK = new KrcdtDayTsTemporaryPK(employee, date);
			krcdtDayTsTemporary.timeLeavingWorks = new ArrayList<>();
		}
		return krcdtDayTsTemporary;
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
		query.append("WHERE a.krcdtDayTsTemporaryPK.employeeId = :employeeId ");
		query.append("AND a.krcdtDayTsTemporaryPK.ymd >= :start ");
		query.append("AND a.krcdtDayTsTemporaryPK.ymd <= :end ");
		query.append("ORDER BY a.krcdtDayTsTemporaryPK.ymd ");
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
		query.append(" WHERE a.krcdtDayTsTemporaryPK.employeeId IN :employeeId ");
		query.append(" AND a.krcdtDayTsTemporaryPK.ymd <= :end AND a.krcdtDayTsTemporaryPK.ymd >= :start");
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
		query.append(" WHERE a.krcdtDayTsTemporaryPK.employeeId IN :employeeId ");
		query.append(" AND a.krcdtDayTsTemporaryPK.ymd IN :date");
		TypedQueryWrapper<Object[]> tQuery=  this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
					.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
					.getList().stream()
					.filter(c -> {
						KrcdtDayTsTemporary af = (KrcdtDayTsTemporary) c[0];
						return p.get(af.krcdtDayTsTemporaryPK.employeeId).contains(af.krcdtDayTsTemporaryPK.ymd);
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
