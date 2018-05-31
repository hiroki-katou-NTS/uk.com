package nts.uk.ctx.at.record.infra.repository.daily.actualworktime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
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
import nts.uk.ctx.at.record.infra.entity.daily.latetime.KrcdtDayLateTime;
import nts.uk.ctx.at.record.infra.entity.daily.latetime.KrcdtDayLateTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime.KrcdtDayLeaveEarlyTime;
import nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime.KrcdtDayLeaveEarlyTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.legalworktime.KrcdtDayPrsIncldTime;
import nts.uk.ctx.at.record.infra.entity.daily.legalworktime.KrcdtDayPrsIncldTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.overtimework.KrcdtDayOvertimework;
import nts.uk.ctx.at.record.infra.entity.daily.overtimework.KrcdtDayOvertimeworkPK;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDayShorttime;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDayShorttimePK;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTime;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.vacation.KrcdtDayVacation;
import nts.uk.ctx.at.record.infra.entity.daily.vacation.KrcdtDayVacationPK;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaAttendanceTimeRepository extends JpaRepository implements AttendanceTimeRepository {

	private static final String REMOVE_BY_EMPLOYEEID_AND_DATE;

	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("DELETE ");
		builderString.append("FROM KrcdtDayAttendanceTime a ");
		builderString.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
		builderString.append("AND a.krcdtDayAttendanceTimePK.generalDate = :ymd ");
		REMOVE_BY_EMPLOYEEID_AND_DATE = builderString.toString();
	}

	@Override
	public void add(AttendanceTimeOfDailyPerformance attendanceTime) {
		/* 勤怠時間 */
//		this.commandProxy().insert(
//				KrcdtDayAttendanceTime.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), attendanceTime));
		this.commandProxy().insert(KrcdtDayTime.toEntity(attendanceTime));


		if (attendanceTime.getActualWorkingTimeOfDaily() != null) {
			if (attendanceTime.getWorkScheduleTimeOfDaily() != null) {
//				/* 予定時間 */
//				this.commandProxy().insert(KrcdtDayWorkScheTime.create(attendanceTime.getEmployeeId(),
//						attendanceTime.getYmd(), attendanceTime.getWorkScheduleTimeOfDaily()));
//			}
//			if(attendanceTime.getActualWorkingTimeOfDaily().getDivTime() != null) {
//				//追加
//				this.commandProxy().insert(KrcdtDayDivergenceTime.toEntity(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
//																			  attendanceTime.getActualWorkingTimeOfDaily().getDivTime()));
//			}
			if(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
//				if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//						.getExcessOfStatutoryTimeOfDaily() != null) {
//					if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
//							.getOverTimeWork().isPresent()) {
//						/* 残業時間 */
//						this.commandProxy()
//							.insert(KrcdtDayOvertimework.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
//									attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//											.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get()));
//					}
//					if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
//						.getWorkHolidayTime().isPresent()) {
//						/* 休出時間 */
//						this.commandProxy()
//							.insert(KrcdtDayHolidyWork.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
//									attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//											.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get()));
//					}
//				}
//				if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily() != null) {
//					/* 休憩時間 */
//					this.commandProxy().insert(KrcdtDayBreakTime.toEntity(attendanceTime.getEmployeeId(),
//							attendanceTime.getYmd(), attendanceTime));
//				}
//				if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily() != null) {
//					/* 短時間勤務時間 */
//					this.commandProxy().insert(KrcdtDayShorttime.toEntity(attendanceTime.getEmployeeId(),
//							attendanceTime.getYmd(), attendanceTime));
//				}
				for (LeaveEarlyTimeOfDaily leaveEarlyTime : attendanceTime.getActualWorkingTimeOfDaily()
						.getTotalWorkingTime().getLeaveEarlyTimeOfDaily()) {
					/* 早退時間 */
					this.commandProxy().insert(KrcdtDayLeaveEarlyTime.create(attendanceTime.getEmployeeId(),
							attendanceTime.getYmd(), leaveEarlyTime));
				}
				for (LateTimeOfDaily lateTime : attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
						.getLateTimeOfDaily()) {
					/* 遅刻時間 */
					this.commandProxy().insert(
							KrcdtDayLateTime.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), lateTime));
				}
//				/* 所定時間内時間 */
//				this.commandProxy().insert(
//						KrcdtDayPrsIncldTime.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), attendanceTime
//								.getActualWorkingTimeOfDaily().getTotalWorkingTime().getWithinStatutoryTimeOfDaily()));
//				if(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily() != null) {
//					/*休暇時間*/
//					this.commandProxy().insert(KrcdtDayVacation.create(attendanceTime.getEmployeeId(),
//							attendanceTime.getYmd(),
//							attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily()));
				}
			}
		}
	}

	@Override
	public void update(AttendanceTimeOfDailyPerformance attendanceTime) {
		
//		KrcdtDayAttendanceTime entity = this.queryProxy()
//				.find(new KrcdtDayAttendanceTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//						KrcdtDayAttendanceTime.class)
//				.orElse(null);
		Optional<KrcdtDayTime> entity = this.queryProxy()
								  .find(new KrcdtDayTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),KrcdtDayTime.class);

		//delete -> Insert
		deleteByEmployeeIdAndDate(attendanceTime.getEmployeeId(), attendanceTime.getYmd());
		
		if (entity.isPresent()) {
			/* 勤怠時間 */
			entity.get().setData(attendanceTime);
			this.commandProxy().update(entity.get());
			if (attendanceTime.getActualWorkingTimeOfDaily() != null) {
				if(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
//					if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily() != null) {
//						/* 残業時間 */
//						KrcdtDayOvertimework krcdtDayOvertimework = this.queryProxy()
//								.find(new KrcdtDayOvertimeworkPK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//										KrcdtDayOvertimework.class)
//								.orElse(null);
//						/* 残業時間がattendanceTimeにある&&取得成功 */
//						if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//								.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()
//								&& (krcdtDayOvertimework != null)) {
//							krcdtDayOvertimework.setData(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//									.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().orElse(null));
//							this.commandProxy().update(krcdtDayOvertimework);
//						}
//						/* 残業時間がattendanceTimeにある && 取得失敗 */
//						else if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//								.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().isPresent()
//								&& (krcdtDayOvertimework == null)) {
//							/* 残業時間 */
//							this.commandProxy()
//									.insert(KrcdtDayOvertimework.create(attendanceTime.getEmployeeId(),
//											attendanceTime.getYmd(),
//											attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//													.getExcessOfStatutoryTimeOfDaily().getOverTimeWork().get()));
//						}
//
//						/* 休出時間 */
//						KrcdtDayHolidyWork krcdtDayHolidyWork = this.queryProxy()
//								.find(new KrcdtDayHolidyWorkPK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//										KrcdtDayHolidyWork.class)
//								.orElse(null);
//						if ((attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//								.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent())
//								&& (krcdtDayHolidyWork != null)) {
//							krcdtDayHolidyWork.setData(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//									.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get());
//							this.commandProxy().update(krcdtDayHolidyWork);
//						} else if ((attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//								.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().isPresent())
//								&& (krcdtDayHolidyWork == null)) {
//							this.commandProxy()
//									.insert(KrcdtDayHolidyWork.create(attendanceTime.getEmployeeId(),
//											attendanceTime.getYmd(),
//											attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//													.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get()));
//						}
//						/* 休憩時間 */
//						KrcdtDayBreakTime krcdtDayBreakTime = this.queryProxy()
//							.find(new KrcdtDayBreakTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//									KrcdtDayBreakTime.class)
//							.orElse(null);
//						if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getBreakTimeOfDaily() != null
//								&& krcdtDayBreakTime != null) {
//							krcdtDayBreakTime.setData(attendanceTime);
//							this.commandProxy().update(krcdtDayBreakTime);
//						} else if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//								.getBreakTimeOfDaily() != null && krcdtDayBreakTime == null) {
//							
//							this.commandProxy().insert(KrcdtDayBreakTime.toEntity(attendanceTime.getEmployeeId(),
//									attendanceTime.getYmd(), attendanceTime));
//						}
//
//						if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily() != null) {
//							/* 短時間勤務時間 */
//							Optional<KrcdtDayShorttime> krcdtDayShorttime = this.queryProxy().find(
//									new KrcdtDayShorttimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
//											attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//													.getShotrTimeOfDaily().getChildCareAttribute().value),
//								KrcdtDayShorttime.class);
//							if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getShotrTimeOfDaily() != null
//									&& krcdtDayShorttime.isPresent()) {
//								krcdtDayShorttime.get().setData(attendanceTime);
//								this.commandProxy().update(krcdtDayShorttime.get());
//
//							} else if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//									.getShotrTimeOfDaily() != null && !krcdtDayShorttime.isPresent()) {
//								this.commandProxy().insert(KrcdtDayShorttime.toEntity(attendanceTime.getEmployeeId(),
//										attendanceTime.getYmd(), attendanceTime));
//							}
//							
//						}
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
//						if (attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//							.getWithinStatutoryTimeOfDaily() != null) {
//						/* 所定時間内時間 */
//							KrcdtDayPrsIncldTime krcdtDayPrsIncldTime = this.queryProxy()
//								.find(new KrcdtDayPrsIncldTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//										KrcdtDayPrsIncldTime.class)
//								.orElse(null);
//							if (krcdtDayPrsIncldTime != null) {
//								krcdtDayPrsIncldTime.setData(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime()
//										.getWithinStatutoryTimeOfDaily());
//								this.commandProxy().update(krcdtDayPrsIncldTime);
//							} else {
//								this.commandProxy()
//									.insert(KrcdtDayPrsIncldTime.create(attendanceTime.getEmployeeId(),
//											attendanceTime.getYmd(), attendanceTime.getActualWorkingTimeOfDaily()
//													.getTotalWorkingTime().getWithinStatutoryTimeOfDaily()));
//							}
//						}
//						if(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily() != null) {
//							/*休暇時間*/
//							Optional<KrcdtDayVacation> krcdtDayVacation = this.queryProxy()
//																	.find(new KrcdtDayVacationPK(attendanceTime.getEmployeeId(),
//																								 attendanceTime.getYmd()),
//																		  KrcdtDayVacation.class);
//							if(krcdtDayVacation.isPresent()) {
//								krcdtDayVacation.get().setData(attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily());
//								this.commandProxy().update(krcdtDayVacation.get());
//							}
//							else {
//								this.commandProxy()
//									.insert(KrcdtDayVacation.create(attendanceTime.getEmployeeId(),
//																	attendanceTime.getYmd(),
//																	attendanceTime.getActualWorkingTimeOfDaily().getTotalWorkingTime().getHolidayOfDaily()));
//							}
//						}
//					}
//				}
//				if (attendanceTime.getWorkScheduleTimeOfDaily() != null) {
//					/* 予定時間 */
//					KrcdtDayWorkScheTime krcdtDayWorkScheTime = this.queryProxy()
//						.find(new KrcdtDayWorkScheTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//								KrcdtDayWorkScheTime.class)
//						.orElse(null);
//					if (krcdtDayWorkScheTime != null) {
//						krcdtDayWorkScheTime.setData(attendanceTime.getWorkScheduleTimeOfDaily());
//						this.commandProxy().update(krcdtDayWorkScheTime);
//					} else {
//						this.commandProxy().insert(KrcdtDayWorkScheTime.create(attendanceTime.getEmployeeId(),
//							attendanceTime.getYmd(), attendanceTime.getWorkScheduleTimeOfDaily()));
//					}
//				}
//				
//				if(attendanceTime.getActualWorkingTimeOfDaily().getDivTime() != null) {
//					/* 乖離時間  */
//					Optional<KrcdtDayDivergenceTime> krcdtDayDivergenceTime = this.queryProxy()
//							.find(new KrcdtDayDivergenceTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
//									KrcdtDayDivergenceTime.class);
//					//更新
//					if(krcdtDayDivergenceTime.isPresent()) {
//						krcdtDayDivergenceTime.get().setData(attendanceTime.getActualWorkingTimeOfDaily().getDivTime());
//						this.commandProxy().update(krcdtDayDivergenceTime.get());
//					}
//					//追加
//					else {
//						this.commandProxy().insert(KrcdtDayDivergenceTime.toEntity(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
//																				   attendanceTime.getActualWorkingTimeOfDaily().getDivTime()));
//					}
				}
			}
			
		} else {
			add(attendanceTime);
		}
	}
// ORIGINAL
//	@Override
//	public Optional<AttendanceTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
//		val pk = new KrcdtDayAttendanceTimePK(employeeId, ymd);
//		return this.queryProxy().find(pk, KrcdtDayAttendanceTime.class)
//				// find(pk,対象テーブル)
//				.map(e -> e.toDomain());
//	}
	
	@Override
	public Optional<AttendanceTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd) {
		val pk = new KrcdtDayTimePK(employeeId, ymd);
		return this.queryProxy().find(pk, KrcdtDayTime.class)
				// find(pk,対象テーブル)
				.map(e -> e.toDomain());
	}

//	ORIGINAL
//	@Override
//	public List<AttendanceTimeOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
//		StringBuilder query = new StringBuilder();
//		query.append("SELECT a FROM KrcdtDayAttendanceTime a ");
//		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate >= :start ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate <= :end ");
//		query.append("ORDER BY a.krcdtDayAttendanceTimePK.generalDate ");
//		return queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class).setParameter("employeeId", employeeId)
//				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end())
//				.getList(e -> e.toDomain());
//	}
	
	@Override
	public List<AttendanceTimeOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
		StringBuilder query = new StringBuilder();
		query.append("SELECT a FROM KrcdtDayTime a ");
		query.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate >= :start ");
		query.append("AND a.krcdtDayTimePK.generalDate <= :end ");
		query.append("ORDER BY a.krcdtDayTimePK.generalDate ");
		return queryProxy().query(query.toString(), KrcdtDayTime.class).setParameter("employeeId", employeeId)
				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end())
				.getList(e -> e.toDomain());
	}

//  ORIGINAL
//	@Override
//	public List<AttendanceTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
//		StringBuilder query = new StringBuilder();
//		query.append("SELECT a FROM KrcdtDayAttendanceTime a ");
//		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID IN :employeeId ");
//		query.append(
//				"AND a.krcdtDayAttendanceTimePK.generalDate <= :end AND a.krcdtDayAttendanceTimePK.generalDate >= :start");
//		return queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class).setParameter("employeeId", employeeId)
//				.setParameter("start", ymd.start()).setParameter("end", ymd.end()).getList().stream()
//				.map(x -> x.toDomain()).collect(Collectors.toList());
//	}
	
	@Override
	public List<AttendanceTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		List<AttendanceTimeOfDailyPerformance> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDayAttendanceTime a ");
		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID IN :employeeId ");
		query.append("AND a.krcdtDayAttendanceTimePK.generalDate <= :end AND a.krcdtDayAttendanceTimePK.generalDate >= :start");
		TypedQueryWrapper<KrcdtDayAttendanceTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
							.setParameter("start", ymd.start())
							.setParameter("end", ymd.end())
							.getList().stream().map(x -> x.toDomain()).collect(Collectors.toList()));
		});
		return result;
	}
	
//	ORIGINAL
//	@Override
//	public void updateFlush(AttendanceTimeOfDailyPerformance attendanceTime) {
//		this.update(attendanceTime);
//		this.getEntityManager().flush();
//	}
	
	@Override
	public void updateFlush(AttendanceTimeOfDailyPerformance attendanceTime) {
		this.update(attendanceTime);
		this.getEntityManager().flush();
	}

//  ORIGINAL
//	@Override
//	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate ymd) {
//		this.queryProxy().find(new KrcdtDayAttendanceTimePK(employeeId, ymd), KrcdtDayAttendanceTime.class).ifPresent(c -> {
//			this.commandProxy().remove(c);
//			this.getEntityManager().flush();
//		});
//		
////		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEEID_AND_DATE).setParameter("employeeId", employeeId)
////				.setParameter("ymd", ymd).executeUpdate();
//	}
	
	@Override
	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate ymd) {
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
		StringBuilder query = new StringBuilder("SELECT a FROM KrcdtDayAttendanceTime a ");
		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID IN :employeeId ");
		query.append("AND a.krcdtDayAttendanceTimePK.generalDate IN :date");
		TypedQueryWrapper<KrcdtDayAttendanceTime> tQuery=  this.queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
							.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
							.getList().stream()
							.filter(c -> p.get(c.krcdtDayAttendanceTimePK.employeeID).contains(c.krcdtDayAttendanceTimePK.generalDate))
							.map(x -> x.toDomain()).collect(Collectors.toList()));
		});
		return result;
	}
}
