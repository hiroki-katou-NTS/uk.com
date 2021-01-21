package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.AttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.GetAttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.export.attdstatus.AttendanceStatusList;

/**
 * 実装：出勤状態を取得する
 * @author shuichu_ishida
 */
@Stateless
public class GetAttendanceStatusImpl implements GetAttendanceStatus {

	/** 日別実績の勤怠時間の取得 */
	@Inject
	private AttendanceTimeRepository attendanceTimeOfDailyRepo;
	/** 日別実績の出退勤の取得 */
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyRepo;
	
	/** 取得 */
	@Override
	public Optional<AttendanceStatus> get(CacheCarrier cacheCarrier, String employeeId, GeneralDate targetDate) {
		
		AttendanceStatusList attendanceStatusList = initAttendanceStatusList(cacheCarrier, employeeId, 
														new DatePeriod(targetDate, targetDate));
		if (attendanceStatusList.getMap().containsKey(targetDate)){
			return Optional.of(attendanceStatusList.getMap().get(targetDate));
		}
		return Optional.empty();
	}
	
	/** 取得 */
	@Override
	public Map<GeneralDate, AttendanceStatus> getMap(CacheCarrier cacheCarrier, String employeeId, DatePeriod period) {


		return initAttendanceStatusList(cacheCarrier, employeeId, period).getMap();
	}
	
	private AttendanceStatusList initAttendanceStatusList(CacheCarrier cacheCarrier, 
			String employeeId, DatePeriod period) {
		
		return new AttendanceStatusList(new RequireM1(cacheCarrier), 
				employeeId, period);
	}
	
	@AllArgsConstructor
	class RequireM1 implements AttendanceStatusList.RequireM1 {
		
		CacheCarrier cacheCarrier;

		@Override
		public Map<GeneralDate, AttendanceTimeOfDailyAttendance> dailyAttendanceTime(String employeeId, DatePeriod datePeriod) {
			return attendanceTimeOfDailyRepo.findByPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getTime()));
		}

		@Override
		public Map<GeneralDate, TimeLeavingOfDailyAttd> dailyTimeLeaving(String employeeId, DatePeriod datePeriod) {
			return timeLeavingOfDailyRepo.findbyPeriodOrderByYmd(employeeId, datePeriod)
					.stream().collect(Collectors.toMap(c -> c.getYmd(), c -> c.getAttendance()));
		}
	}
}
