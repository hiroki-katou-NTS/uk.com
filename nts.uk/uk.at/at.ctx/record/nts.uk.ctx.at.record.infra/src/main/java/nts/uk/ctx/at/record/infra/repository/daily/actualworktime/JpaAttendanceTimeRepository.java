package nts.uk.ctx.at.record.infra.repository.daily.actualworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Convert;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.daily.LateTimeOfDaily;
import nts.uk.ctx.at.record.dom.daily.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDayBreakTime;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDayBreakTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTime;
import nts.uk.ctx.at.record.infra.entity.daily.actualworktime.KrcdtDayAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.attendanceschedule.KrcdtDayWorkScheTime;
import nts.uk.ctx.at.record.infra.entity.daily.attendanceschedule.KrcdtDayWorkScheTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.divergencetime.KrcdtDayDivergenceTime;
import nts.uk.ctx.at.record.infra.entity.daily.divergencetime.KrcdtDayDivergenceTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.holidayworktime.KrcdtDayHolidyWork;
import nts.uk.ctx.at.record.infra.entity.daily.holidayworktime.KrcdtDayHolidyWorkPK;
import nts.uk.ctx.at.record.infra.entity.daily.holidayworktime.KrcdtDayHolidyWorkTs;
import nts.uk.ctx.at.record.infra.entity.daily.holidayworktime.KrcdtDayHolidyWorkTsPK;
import nts.uk.ctx.at.record.infra.entity.daily.latetime.KrcdtDayLateTime;
import nts.uk.ctx.at.record.infra.entity.daily.latetime.KrcdtDayLateTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime.KrcdtDayLeaveEarlyTime;
import nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime.KrcdtDayLeaveEarlyTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.legalworktime.KrcdtDayPrsIncldTime;
import nts.uk.ctx.at.record.infra.entity.daily.legalworktime.KrcdtDayPrsIncldTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.overtimework.KrcdtDayOvertimework;
import nts.uk.ctx.at.record.infra.entity.daily.overtimework.KrcdtDayOvertimeworkPK;
import nts.uk.ctx.at.record.infra.entity.daily.overtimework.KrcdtDayOvertimeworkTs;
import nts.uk.ctx.at.record.infra.entity.daily.overtimework.KrcdtDayOvertimeworkTsPK;
import nts.uk.ctx.at.record.infra.entity.daily.premiumtime.KrcdtDayPremiumTime;
import nts.uk.ctx.at.record.infra.entity.daily.premiumtime.KrcdtDayPremiumTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDayShorttime;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDayShorttimePK;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTime;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.vacation.KrcdtDayVacation;
import nts.uk.ctx.at.record.infra.entity.daily.vacation.KrcdtDayVacationPK;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaAttendanceTimeRepository extends JpaRepository implements AttendanceTimeRepository {

	private static final String REMOVE_BY_EMPLOYEEID_AND_DATE;
	
	private static final String FIND_BY_EMPLOYEEID_AND_DATES;

//	static {
//		StringBuilder builderString = new StringBuilder();
//		builderString.append("DELETE ");
//		builderString.append("FROM KrcdtDayAttendanceTime a ");
//		builderString.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
//		builderString.append("AND a.krcdtDayAttendanceTimePK.generalDate = :ymd ");
//		REMOVE_BY_EMPLOYEEID_AND_DATE = builderString.toString();
//	}
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDayTime a ");
		builderString.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
		builderString.append("AND a.krcdtDayTimePK.generalDate = :ymd ");
		REMOVE_BY_EMPLOYEEID_AND_DATE = builderString.toString();
		
		builderString = new StringBuilder("SELECT a FROM KrcdtDayAttendanceTime a ");
		builderString.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
		builderString.append("AND a.krcdtDayAttendanceTimePK.generalDate IN :date");
		FIND_BY_EMPLOYEEID_AND_DATES = builderString.toString();
	}

	@Override
	public void add(AttendanceTimeOfDailyPerformance attendanceTime) {
		/* 勤怠時間 */
//		this.commandProxy().insert(
//				KrcdtDayAttendanceTime.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), attendanceTime));
		this.commandProxy().insert(KrcdtDayTime.toEntity(attendanceTime));



		if (attendanceTime.getActualWorkingTimeOfDaily() != null) {
			if(attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance() != null) {
				/* 割増時間  */
				Optional<KrcdtDayPremiumTime> krcdtDayPremiumTime = this.queryProxy()
						.find(new KrcdtDayPremiumTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
								KrcdtDayPremiumTime.class);
				if(krcdtDayPremiumTime.isPresent()) {
					//更新
					krcdtDayPremiumTime.get().setData(attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance());
					this.commandProxy().update(krcdtDayPremiumTime.get());
				}else {
					//追加
					this.commandProxy().insert(KrcdtDayPremiumTime.totoEntity(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), 
																			  attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()));
				}
			}
			if(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {

				for (LeaveEarlyTimeOfDaily leaveEarlyTime : attendanceTime.getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getLeaveEarlyTimeOfDaily()) {
					KrcdtDayLeaveEarlyTime krcdtDayLeaveEarlyTime = this
							.queryProxy().find(
									new KrcdtDayLeaveEarlyTimePK(attendanceTime.getEmployeeId(),
										attendanceTime.getYmd(), leaveEarlyTime.getWorkNo().v()),
								KrcdtDayLeaveEarlyTime.class)
							.orElse(null);
					/* 早退時間 */
					if (krcdtDayLeaveEarlyTime == null) {
						this.commandProxy().insert(KrcdtDayLeaveEarlyTime.create(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), leaveEarlyTime));
					} else {
						krcdtDayLeaveEarlyTime.setData(leaveEarlyTime);
						this.commandProxy().update(krcdtDayLeaveEarlyTime);
					}
				}
				for (LateTimeOfDaily lateTime : attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getLateTimeOfDaily()) {
					KrcdtDayLateTime krcdtDayLateTime = this
						.queryProxy().find(new KrcdtDayLateTimePK(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), lateTime.getWorkNo().v()), KrcdtDayLateTime.class)
						.orElse(null);
					/* 遅刻時間 */
					if (krcdtDayLateTime == null) {
						this.commandProxy().insert(KrcdtDayLateTime.create(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), lateTime));
					} else {
						krcdtDayLateTime.setData(lateTime);
						this.commandProxy().update(krcdtDayLateTime);
					}
				}

			}
		}
	}

	@Override
	public void update(AttendanceTimeOfDailyPerformance attendanceTime) {
				
		Optional<KrcdtDayTime> entity = this.queryProxy()
				  .find(new KrcdtDayTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),KrcdtDayTime.class);
		
		if (entity.isPresent()) {
			/* 勤怠時間 */
			entity.get().setData(attendanceTime);
			this.commandProxy().update(entity.get());
			if (attendanceTime.getActualWorkingTimeOfDaily() != null) {
				if(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {

						String letQuery = "SELECT le FROM KrcdtDayLeaveEarlyTime le WHERE le.krcdtDayLeaveEarlyTimePK.employeeID = :employeeId AND le.krcdtDayLeaveEarlyTimePK.generalDate = :date";
						List<KrcdtDayLeaveEarlyTime> krcdtDayLeaveEarlyTimeList = this.queryProxy().query(letQuery, KrcdtDayLeaveEarlyTime.class)
								.setParameter("employeeId", attendanceTime.getEmployeeId()).setParameter("date", attendanceTime.getYmd()).getList();
						List<KrcdtDayLeaveEarlyTime> toKrcdtDayLeaveEarlyTimeDel = krcdtDayLeaveEarlyTimeList.stream().filter(c -> 
							 !attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLeaveEarlyTimeOfDaily().stream().filter(t -> t.getWorkNo().v()==c.krcdtDayLeaveEarlyTimePK.workNo).findFirst().isPresent()
						).collect(Collectors.toList());
						if(!toKrcdtDayLeaveEarlyTimeDel.isEmpty()) {
							this.commandProxy().removeAll(toKrcdtDayLeaveEarlyTimeDel);
						}	
						for (LeaveEarlyTimeOfDaily leaveEarlyTime : attendanceTime.getActualWorkingTimeOfDaily()
								.getTotalWorkingTime().getLeaveEarlyTimeOfDaily()) {
							KrcdtDayLeaveEarlyTime krcdtDayLeaveEarlyTime = krcdtDayLeaveEarlyTimeList.stream().filter(c -> leaveEarlyTime.getWorkNo().v()==c.krcdtDayLeaveEarlyTimePK.workNo).findFirst().orElse(null);
							/* 早退時間 */
							if (krcdtDayLeaveEarlyTime == null) {
								this.commandProxy().insert(KrcdtDayLeaveEarlyTime.create(attendanceTime.getEmployeeId(),
										attendanceTime.getYmd(), leaveEarlyTime));
							} else {
								krcdtDayLeaveEarlyTime.setData(leaveEarlyTime);
								this.commandProxy().update(krcdtDayLeaveEarlyTime);
							}
						}
						
						String latQuery = "SELECT le FROM KrcdtDayLateTime le WHERE le.krcdtDayLateTimePK.employeeID = :employeeId AND le.krcdtDayLateTimePK.generalDate = :date";
						List<KrcdtDayLateTime> KrcdtDayLateTimeList = this.queryProxy().query(latQuery, KrcdtDayLateTime.class)
								.setParameter("employeeId", attendanceTime.getEmployeeId()).setParameter("date", attendanceTime.getYmd()).getList();
						List<KrcdtDayLateTime> toKrcdtDayLateTimeDel = KrcdtDayLateTimeList.stream().filter(c -> 
							 !attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getLateTimeOfDaily().stream().filter(t -> t.getWorkNo().v()==c.krcdtDayLateTimePK.workNo).findFirst().isPresent()
						).collect(Collectors.toList());
						if(!toKrcdtDayLateTimeDel.isEmpty()) {
							this.commandProxy().removeAll(toKrcdtDayLateTimeDel);
						}	
						for (LateTimeOfDaily lateTime : attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
								.getLateTimeOfDaily()) {
							KrcdtDayLateTime krcdtDayLateTime = KrcdtDayLateTimeList.stream().filter(c -> lateTime.getWorkNo().v()==c.krcdtDayLateTimePK.workNo).findFirst().orElse(null);
							/* 遅刻時間 */
							if (krcdtDayLateTime == null) {
								this.commandProxy().insert(KrcdtDayLateTime.create(attendanceTime.getEmployeeId(),
										attendanceTime.getYmd(), lateTime));
							} else {
								krcdtDayLateTime.setData(lateTime);
								this.commandProxy().update(krcdtDayLateTime);
							}
						}

					}
				}
				if(attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance() != null) {
					/* 割増時間  */
					Optional<KrcdtDayPremiumTime> krcdtDayPremiumTime = this.queryProxy()
							.find(new KrcdtDayPremiumTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
									KrcdtDayPremiumTime.class);
					if(krcdtDayPremiumTime.isPresent()) {
						//更新
						krcdtDayPremiumTime.get().setData(attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance());
						this.commandProxy().update(krcdtDayPremiumTime.get());
					}else {
						//追加
						this.commandProxy().insert(KrcdtDayPremiumTime.totoEntity(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), 
																				  attendanceTime.getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()));
					}
				}
		}
		else {
			add(attendanceTime);
		}
	}

	@Override
	public Optional<AttendanceTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		//val pk = new KrcdtDayAttendanceTimePK(employeeId, ymd);
		val pk = new KrcdtDayTimePK(employeeId, ymd);
		//return this.queryProxy().find(pk, KrcdtDayAttendanceTime.class)
		return this.queryProxy().find(pk, KrcdtDayTime.class)
				// find(pk,対象テーブル)
				.map(e -> e.toDomain());
	}

	@Override
	public List<AttendanceTimeOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		StringBuilder query = new StringBuilder();
//		query.append("SELECT a FROM KrcdtDayAttendanceTime a ");
//		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate >= :start ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate <= :end ");
//		query.append("ORDER BY a.krcdtDayAttendanceTimePK.generalDate ");
//		return queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class).setParameter("employeeId", employeeId)
//				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end())
//				.getList(e -> e.toDomain());
		query.append("SELECT a FROM KrcdtDayTime a ");
		query.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate >= :start ");
		query.append("AND a.krcdtDayTimePK.generalDate <= :end ");
		query.append("ORDER BY a.krcdtDayTimePK.generalDate ");
		return queryProxy().query(query.toString(), KrcdtDayTime.class).setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end())
				.getList(e -> e.toDomain());
	}

	@Override
	public List<AttendanceTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		List<AttendanceTimeOfDailyPerformance> result = new ArrayList<>();
//		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDayAttendanceTime a ");
//		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID IN :employeeId ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate <= :end AND a.krcdtDayAttendanceTimePK.generalDate >= :start");
//		TypedQueryWrapper<KrcdtDayAttendanceTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class);
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDayTime a ");
		query.append("WHERE a.krcdtDayTimePK.employeeID IN :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate <= :end AND a.krcdtDayTimePK.generalDate >= :start");
		TypedQueryWrapper<KrcdtDayTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayTime.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
							.setParameter("start", ymd.start())
							.setParameter("end", ymd.end())
							.getList().stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		});
		
		return result;
	}

	@Override
	public void updateFlush(AttendanceTimeOfDailyPerformance attendanceTime) {
		this.update(attendanceTime);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate ymd) {
		//this.queryProxy().find(new KrcdtDayAttendanceTimePK(employeeId, ymd), KrcdtDayAttendanceTime.class).ifPresent(c -> {
		this.queryProxy().find(new KrcdtDayTimePK(employeeId, ymd), KrcdtDayTime.class).ifPresent(c -> {
			this.commandProxy().remove(c);
			this.getEntityManager().flush();
		});
		
//		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEEID_AND_DATE).setParameter("employeeId", employeeId)
//				.setParameter("ymd", ymd).executeUpdate();
	}

	@Override
	public List<AttendanceTimeOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<AttendanceTimeOfDailyPerformance> result = new ArrayList<>();
//		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDayAttendanceTime a ");
//		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID IN :employeeId ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate IN :date");
//		TypedQueryWrapper<KrcdtDayAttendanceTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class);
//		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
//			result.addAll(tQuery.setParameter("employeeId", p.keySet())
//							.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
//							.getList().stream()
//							.filter(c -> p.get(c.krcdtDayAttendanceTimePK.employeeID).contains(c.krcdtDayAttendanceTimePK.generalDate))
//							.map(x -> x.toDomain()).collect(Collectors.toList()));
//		});
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDayTime a ");
		query.append("WHERE a.krcdtDayTimePK.employeeID IN :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate IN :date");
		TypedQueryWrapper<KrcdtDayTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayTime.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
							.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
							.getList().stream()
							.filter(c -> p.get(c.krcdtDayTimePK.employeeID).contains(c.krcdtDayTimePK.generalDate))
							.map(x -> x.toDomain()).collect(Collectors.toList()));
		});
		return result;
	}

	@Override
	public List<AttendanceTimeOfDailyPerformance> find(String employeeId, List<GeneralDate> ymd) {
		return this.queryProxy().query(FIND_BY_EMPLOYEEID_AND_DATES, KrcdtDayAttendanceTime.class)
				.setParameter("employeeId", employeeId).setParameter("date", ymd).getList(x -> x.toDomain());
	}
}
