
package nts.uk.ctx.at.record.infra.repository.daily.actualworktime;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDayTimeGoout;
import nts.uk.ctx.at.record.infra.entity.breakorgoout.KrcdtDayOutingTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.latetime.KrcdtDayLateTime;
import nts.uk.ctx.at.record.infra.entity.daily.latetime.KrcdtDayLateTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime.KrcdtDayLeaveEarlyTime;
import nts.uk.ctx.at.record.infra.entity.daily.leaveearlytime.KrcdtDayLeaveEarlyTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.premiumtime.KrcdtDayTimePremium;
import nts.uk.ctx.at.record.infra.entity.daily.premiumtime.KrcdtDayPremiumTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDayShorttime;
import nts.uk.ctx.at.record.infra.entity.daily.shortwork.KrcdtDayShorttimePK;
import nts.uk.ctx.at.record.infra.entity.daily.temporarytime.KrcdtDayTempFrmTime;
import nts.uk.ctx.at.record.infra.entity.daily.temporarytime.KrcdtDayTempFrmTimePK;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimeAtd;
import nts.uk.ctx.at.record.infra.entity.daily.time.KrcdtDayTimePK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryFrameTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;

@Stateless
public class JpaAttendanceTimeRepository extends JpaRepository implements AttendanceTimeRepository {

//	private static final String REMOVE_BY_EMPLOYEEID_AND_DATE;
	
	private static final String FIND_BY_LABOR_TIME;
	
//	private static final String FIND_BY_EMPLOYEEID_AND_DATES;

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
//		builderString.append("DELETE ");
//		builderString.append("FROM KrcdtDayTimeAtd a ");
//		builderString.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
//		builderString.append("AND a.krcdtDayTimePK.generalDate = :ymd ");
//		REMOVE_BY_EMPLOYEEID_AND_DATE = builderString.toString();
		
		builderString = new StringBuilder("SELECT a.schedulePreLaborTime FROM KrcdtDayTimeAtd a ");
//		builderString.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
//		builderString.append("AND a.krcdtDayAttendanceTimePK.generalDate IN :date");
		builderString.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
		builderString.append("AND a.krcdtDayTimePK.generalDate IN :date");
		FIND_BY_LABOR_TIME = builderString.toString();
		
//		builderString.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
//		builderString.append("AND a.krcdtDayAttendanceTimePK.generalDate IN :date");
//		builderString.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
//		builderString.append("AND a.krcdtDayTimePK.generalDate IN :date");
//		FIND_BY_EMPLOYEEID_AND_DATES = builderString.toString();
	}

	@Override
	public void add(AttendanceTimeOfDailyPerformance attendanceTime) {
		/* 勤怠時間 */
//		this.commandProxy().insert(
//				KrcdtDayAttendanceTime.create(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), attendanceTime));
		this.commandProxy().insert(KrcdtDayTimeAtd.toEntity(attendanceTime));

		if (attendanceTime.getTime().getActualWorkingTimeOfDaily() != null) {
			if(attendanceTime.getTime().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance() != null) {
				/* 割増時間  */
				Optional<KrcdtDayTimePremium> krcdtDayPremiumTime = this.queryProxy()
						.find(new KrcdtDayPremiumTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
								KrcdtDayTimePremium.class);
				if(krcdtDayPremiumTime.isPresent()) {
					//更新
					krcdtDayPremiumTime.get().setData(attendanceTime.getTime().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance());
					this.commandProxy().update(krcdtDayPremiumTime.get());
				}else {
					//追加
					this.commandProxy().insert(KrcdtDayTimePremium.totoEntity(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), 
																			  attendanceTime.getTime().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()));
				}
			}
			if(attendanceTime.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
				TotalWorkingTime totalWorkingTime = attendanceTime.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime();
				
				for (LeaveEarlyTimeOfDaily leaveEarlyTime : totalWorkingTime.getLeaveEarlyTimeOfDaily()) {
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
				for (LateTimeOfDaily lateTime : totalWorkingTime.getLateTimeOfDaily()) {
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
				//短時間
				KrcdtDayShorttime krcdtDayShorttime = this.queryProxy().find(
						new KrcdtDayShorttimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
								totalWorkingTime.getShotrTimeOfDaily().getChildCareAttribute().value),
						KrcdtDayShorttime.class).orElse(null);
				if(krcdtDayShorttime != null) {
					
					krcdtDayShorttime.setData(attendanceTime);
					this.commandProxy().update(krcdtDayShorttime);
				}
				else {
					this.commandProxy().insert(KrcdtDayShorttime.toEntity(attendanceTime.getEmployeeId(),
							attendanceTime.getYmd(), attendanceTime));
				}
				KrcdtDayShorttime otherAtrkrcdtDayShorttime = this.queryProxy().find(new KrcdtDayShorttimePK(
						attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
						totalWorkingTime.getShotrTimeOfDaily().getChildCareAttribute().value == 0?1:0),
						KrcdtDayShorttime.class).orElse(null);
				if(otherAtrkrcdtDayShorttime != null) {
					this.commandProxy().remove(otherAtrkrcdtDayShorttime);
				}
				
				for(OutingTimeOfDaily outing : totalWorkingTime.getOutingTimeOfDailyPerformance()) {
					//外出時間
					KrcdtDayTimeGoout krcdtDayOutingTime = this.queryProxy().find(new KrcdtDayOutingTimePK(
							attendanceTime.getEmployeeId(),attendanceTime.getYmd(),outing.getReason().value),
							KrcdtDayTimeGoout.class).orElse(null);
					if(krcdtDayOutingTime != null) {
						krcdtDayOutingTime.setData(outing);
						this.commandProxy().update(krcdtDayOutingTime);
					}
					else {
						this.commandProxy().insert(KrcdtDayTimeGoout.toEntity(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), outing));
					}
				}

				// 所定外時間
				if (totalWorkingTime.getExcessOfStatutoryTimeOfDaily() != null){
					ExcessOfStatutoryTimeOfDaily excessOfStatutoryTime = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
					// 臨時時間
					if (excessOfStatutoryTime.getTemporaryTime() != null){
						TemporaryTimeOfDaily temporaryTime = excessOfStatutoryTime.getTemporaryTime();
						// 臨時枠時間
						for (TemporaryFrameTimeOfDaily temporaryFrameTime : temporaryTime.getTemporaryTime()){
							KrcdtDayTempFrmTime krcdtDayTempFrmTime = this.queryProxy()
									.find(new KrcdtDayTempFrmTimePK(
											attendanceTime.getEmployeeId(),
											attendanceTime.getYmd(),
											temporaryFrameTime.getWorkNo().v()), KrcdtDayTempFrmTime.class)
									.orElse(null);
							if (krcdtDayTempFrmTime != null){
								krcdtDayTempFrmTime.setData(temporaryFrameTime);
								this.commandProxy().update(krcdtDayTempFrmTime);
							}
							else {
								this.commandProxy().insert(KrcdtDayTempFrmTime.toEntity(
										attendanceTime.getEmployeeId(), attendanceTime.getYmd(), temporaryFrameTime));
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void update(AttendanceTimeOfDailyPerformance attendanceTime) {//
				
		Optional<KrcdtDayTimeAtd> entity = this.queryProxy()
				  .find(new KrcdtDayTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),KrcdtDayTimeAtd.class);
		
		if (entity.isPresent()) {
			/* 勤怠時間 */
			entity.get().setData(attendanceTime);
			this.commandProxy().update(entity.get());
			
			if (attendanceTime.getTime().getActualWorkingTimeOfDaily() != null) {
				if(attendanceTime.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime() != null) {
					TotalWorkingTime totalWorkingTime = attendanceTime.getTime().getActualWorkingTimeOfDaily().getTotalWorkingTime();

					/* 早退時間 */
					try (val statement = this.connection().prepareStatement(
								"delete from KRCDT_DAY_TIME_LEAVEEARLY where SID = ? and YMD = ?")) {
						statement.setString(1, attendanceTime.getEmployeeId());
						statement.setDate(2, Date.valueOf(attendanceTime.getYmd().toLocalDate()));
						statement.execute();
						this.getEntityManager().flush();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					for (LeaveEarlyTimeOfDaily leaveEarlyTime : totalWorkingTime.getLeaveEarlyTimeOfDaily()) {
						this.commandProxy().insert(KrcdtDayLeaveEarlyTime.create(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), leaveEarlyTime));
					}
						
					/* 遅刻時間 */
					try (val statement = this.connection().prepareStatement(
								"delete from KRCDT_DAY_LATETIME where SID = ? and YMD = ?")) {
						statement.setString(1, attendanceTime.getEmployeeId());
						statement.setDate(2, Date.valueOf(attendanceTime.getYmd().toLocalDate()));
						statement.execute();
						this.getEntityManager().flush();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					for (LateTimeOfDaily lateTime : totalWorkingTime.getLateTimeOfDaily()) {
							this.commandProxy().insert(KrcdtDayLateTime.create(attendanceTime.getEmployeeId(),
									attendanceTime.getYmd(), lateTime));
					}
					
					//短時間
					KrcdtDayShorttime krcdtDayShorttime = this.queryProxy().find(new KrcdtDayShorttimePK(
							attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
							totalWorkingTime.getShotrTimeOfDaily().getChildCareAttribute().value),
							KrcdtDayShorttime.class).orElse(null);
					if(krcdtDayShorttime != null) {
						krcdtDayShorttime.setData(attendanceTime);
						this.commandProxy().update(krcdtDayShorttime);
					}
					else {
						this.commandProxy().insert(KrcdtDayShorttime.toEntity(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), attendanceTime));
					}
					KrcdtDayShorttime otherAtrkrcdtDayShorttime = this.queryProxy()
							.find(new KrcdtDayShorttimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd(),
									totalWorkingTime.getShotrTimeOfDaily().getChildCareAttribute().value == 0?1:0),
									KrcdtDayShorttime.class).orElse(null);
					if(otherAtrkrcdtDayShorttime != null) {
						this.commandProxy().remove(otherAtrkrcdtDayShorttime);
					}
					
					//外出時間
					try (val statement = this.connection().prepareStatement(
							"delete from KRCDT_DAY_TIME_GOOUT where SID = ? and YMD = ?")) {
						statement.setString(1, attendanceTime.getEmployeeId());
						statement.setDate(2, Date.valueOf(attendanceTime.getYmd().toLocalDate()));
						statement.execute();
						this.getEntityManager().flush();
					} catch (SQLException e) {
						throw new RuntimeException(e);
					}
					for(OutingTimeOfDaily outing : totalWorkingTime.getOutingTimeOfDailyPerformance()) {
						//外出時間
						this.commandProxy().insert(KrcdtDayTimeGoout.toEntity(attendanceTime.getEmployeeId(),
								attendanceTime.getYmd(), outing));
					}
					
					// 所定外時間
					if (totalWorkingTime.getExcessOfStatutoryTimeOfDaily() != null){
						ExcessOfStatutoryTimeOfDaily excessOfStatutoryTime = totalWorkingTime.getExcessOfStatutoryTimeOfDaily();
						// 臨時時間
						if (excessOfStatutoryTime.getTemporaryTime() != null){
							TemporaryTimeOfDaily temporaryTime = excessOfStatutoryTime.getTemporaryTime();
							// 臨時枠時間
							try (val statement = this.connection().prepareStatement(
									"delete from KRCDT_DAY_TEMP_FRM_TIME where SID = ? and YMD = ?")) {
								statement.setString(1, attendanceTime.getEmployeeId());
								statement.setDate(2, Date.valueOf(attendanceTime.getYmd().toLocalDate()));
								statement.execute();
								this.getEntityManager().flush();
							} catch (SQLException e) {
								throw new RuntimeException(e);
							}
							for (TemporaryFrameTimeOfDaily temporaryFrameTime : temporaryTime.getTemporaryTime()){
								this.commandProxy().insert(KrcdtDayTempFrmTime.toEntity(
										attendanceTime.getEmployeeId(), attendanceTime.getYmd(), temporaryFrameTime));
							}
						}
					}
				}
			}
			if(attendanceTime.getTime().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance() != null) {
				/* 割増時間  */
				Optional<KrcdtDayTimePremium> krcdtDayPremiumTime = this.queryProxy()
						.find(new KrcdtDayPremiumTimePK(attendanceTime.getEmployeeId(), attendanceTime.getYmd()),
								KrcdtDayTimePremium.class);
				if(krcdtDayPremiumTime.isPresent()) {
					//更新
					krcdtDayPremiumTime.get().setData(attendanceTime.getTime().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance());
					this.commandProxy().update(krcdtDayPremiumTime.get());
				}else {
					//追加
					this.commandProxy().insert(KrcdtDayTimePremium.totoEntity(attendanceTime.getEmployeeId(), attendanceTime.getYmd(), 
																			  attendanceTime.getTime().getActualWorkingTimeOfDaily().getPremiumTimeOfDailyPerformance()));
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
		return this.queryProxy().find(pk, KrcdtDayTimeAtd.class)
				// find(pk,対象テーブル)
				.map(e -> e.toDomain());
//		StringBuilder query = new StringBuilder();
//		query.append("SELECT a FROM KrcdtDayTimeAtd a ");
//		query.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
//		query.append("AND a.krcdtDayTimePK.generalDate  = :ymd ");
//		return Optional.of(queryProxy().query(query.toString(), KrcdtDayTimeAtd.class).setParameter("employeeId", employeeId)
//							.setParameter("ymd", ymd).getSingleOrNull().toDomain());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<AttendanceTimeOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod) {
//		StringBuilder query = new StringBuilder();
//		query.append("SELECT a FROM KrcdtDayAttendanceTime a ");
//		query.append("WHERE a.krcdtDayAttendanceTimePK.employeeID = :employeeId ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate >= :start ");
//		query.append("AND a.krcdtDayAttendanceTimePK.generalDate <= :end ");
//		query.append("ORDER BY a.krcdtDayAttendanceTimePK.generalDate ");
//		return queryProxy().query(query.toString(), KrcdtDayAttendanceTime.class).setParameter("employeeId", employeeId)
//				.setParameter("start", datePeriod.start()).setParameter("end", datePeriod.end())
//				.getList(e -> e.toDomain());
		StringBuilder query = new StringBuilder("SELECT a, c, d, e, g, h, i FROM KrcdtDayTimeAtd a LEFT JOIN a.krcdtDayLeaveEarlyTime c ");
		query.append("LEFT JOIN a.krcdtDayPremiumTime d ");
		query.append("LEFT JOIN a.krcdtDayLateTime e ");
		query.append("LEFT JOIN a.krcdtDayShorttime g ");
		query.append("LEFT JOIN a.krcdtDayOutingTime h ");	
		query.append("LEFT JOIN a.krcdtDayTempFrmTime i ");
		query.append("WHERE a.krcdtDayTimePK.employeeID = :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate >= :start ");
		query.append("AND a.krcdtDayTimePK.generalDate <= :end ");
		TypedQueryWrapper<Object[]> tQuery=  this.queryProxy().query(query.toString(),  Object[].class);
		
		List<Object[]> result = new ArrayList<>();
		result.addAll(tQuery.setParameter("employeeId", employeeId)
							.setParameter("start", datePeriod.start())
							.setParameter("end", datePeriod.end())
							.getList());
		return toDomainFromJoin(result);
	}

	@Override
	public void updateFlush(AttendanceTimeOfDailyPerformance attendanceTime) {
		this.update(attendanceTime);
		this.getEntityManager().flush();
	}

	@Override
	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate ymd) {
		this.queryProxy().find(new KrcdtDayTimePK(employeeId, ymd), KrcdtDayTimeAtd.class).ifPresent(c -> {
			this.commandProxy().remove(c);
		});
		
	}

	@Override
	public List<AttendanceTimeOfDailyPerformance> finds(Map<String, List<GeneralDate>> param) {
		List<Object[]> result = new ArrayList<>();
//		});
		StringBuilder query = new StringBuilder("SELECT a, c, d, e, g, h, i FROM KrcdtDayTimeAtd a LEFT JOIN a.krcdtDayLeaveEarlyTime c ");
		query.append("LEFT JOIN a.krcdtDayPremiumTime d ");
		query.append("LEFT JOIN a.krcdtDayLateTime e ");
		query.append("LEFT JOIN a.krcdtDayShorttime g ");
		query.append("LEFT JOIN a.krcdtDayOutingTime h ");	
		query.append("LEFT JOIN a.krcdtDayTempFrmTime i ");
		query.append("WHERE a.krcdtDayTimePK.employeeID IN :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate IN :date");
		TypedQueryWrapper<Object[]> tQuery = this.queryProxy().query(query.toString(), Object[].class);
		CollectionUtil.split(param, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, p -> {
			result.addAll(tQuery.setParameter("employeeId", p.keySet())
							.setParameter("date", p.values().stream().flatMap(List::stream).collect(Collectors.toSet()))
							.getList().stream()
							.filter(c -> {
								KrcdtDayTimeAtd af = (KrcdtDayTimeAtd) c[0];
								return p.get(af.krcdtDayTimePK.employeeID).contains(af.krcdtDayTimePK.generalDate);
							}).collect(Collectors.toList()));
		});
		return toDomainFromJoin(result);
	}
	
	@Override
	public List<AttendanceTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd) {
		List<Object[]> result = new ArrayList<>();
		StringBuilder query = new StringBuilder("SELECT a, c, d, e, g, h, i FROM KrcdtDayTimeAtd a LEFT JOIN a.krcdtDayLeaveEarlyTime c ");
		query.append("LEFT JOIN a.krcdtDayPremiumTime d ");
		query.append("LEFT JOIN a.krcdtDayLateTime e ");
		query.append("LEFT JOIN a.krcdtDayShorttime g ");	
		query.append("LEFT JOIN a.krcdtDayOutingTime h ");	
		query.append("LEFT JOIN a.krcdtDayTempFrmTime i ");
		query.append("WHERE a.krcdtDayTimePK.employeeID IN :employeeId ");
		query.append("AND a.krcdtDayTimePK.generalDate <= :end AND a.krcdtDayTimePK.generalDate >= :start");
		TypedQueryWrapper<Object[]> tQuery=  this.queryProxy().query(query.toString(),  Object[].class);
		CollectionUtil.split(employeeId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, empIds -> {
			result.addAll(tQuery.setParameter("employeeId", empIds)
							.setParameter("start", ymd.start())
							.setParameter("end", ymd.end())
							.getList());
		});
		
		return toDomainFromJoin(result);
	}
	
	private List<AttendanceTimeOfDailyPerformance> toDomainFromJoin(List<Object[]> result) {
		return result.stream().collect(Collectors.groupingBy(c1 -> c1[0], Collectors.toList()))
				.entrySet().stream().map(e -> {
					KrcdtDayTimeAtd krcdtDayTime = (KrcdtDayTimeAtd) e.getKey();
					List<KrcdtDayLeaveEarlyTime> krcdtDayLeaveEarlyTime = e.getValue().stream().filter(c -> c[1] != null).map(c -> (KrcdtDayLeaveEarlyTime) c[1]).distinct().collect(Collectors.toList());
					KrcdtDayTimePremium krcdtDayPremiumTime = e.getValue().stream().filter(c -> c[2] != null).map(c -> (KrcdtDayTimePremium) c[2]).distinct().findFirst().orElse(null);
					List<KrcdtDayLateTime> krcdtDayLateTime = e.getValue().stream().filter(c -> c[3] != null).map(c -> (KrcdtDayLateTime) c[3]).distinct().collect(Collectors.toList());
					List<KrcdtDayShorttime> KrcdtDayShorttime =  e.getValue().stream().filter(c -> c[4] != null).map(c -> (KrcdtDayShorttime) c[4]).distinct().collect(Collectors.toList());
					List<KrcdtDayTimeGoout> krcdtDayOutingTime =  e.getValue().stream().filter(c -> c[5] != null).map(c -> (KrcdtDayTimeGoout) c[5]).distinct().collect(Collectors.toList());
					List<KrcdtDayTempFrmTime> krcdtDayTempFrmTime = e.getValue().stream().filter(c -> c[6] != null).map(c -> (KrcdtDayTempFrmTime) c[6]).distinct().collect(Collectors.toList());
					return KrcdtDayTimeAtd.toDomain(krcdtDayTime, krcdtDayPremiumTime, krcdtDayLeaveEarlyTime, krcdtDayLateTime, KrcdtDayShorttime, krcdtDayOutingTime, krcdtDayTempFrmTime);
				})
				.collect(Collectors.toList());		
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<Integer> findAtt(String employeeId, List<GeneralDate> ymd) {
		List<Integer> resultList = new ArrayList<>();
		CollectionUtil.split(ymd, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(FIND_BY_LABOR_TIME, Integer.class)
				.setParameter("employeeId", employeeId)
				.setParameter("date", subList)
				.getList());
		});
		return resultList;
	}
	
	@Override
	public List<AttendanceTimeOfDailyPerformance> find(String employeeId, List<GeneralDate> ymd) {
		Map<String, List<GeneralDate>> map = new HashMap<>();
		map.put(employeeId, ymd);
		return finds(map);

	}
}
