package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.attdstatus;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.AttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.GetAttendanceStatus;

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
		public List<AttendanceTimeOfDailyPerformance> dailyAttendanceTime(String employeeId, DatePeriod datePeriod) {
			return attendanceTimeOfDailyRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
		}

		@Override
		public List<TimeLeavingOfDailyPerformance> dailyTimeLeaving(String employeeId, DatePeriod datePeriod) {
			return timeLeavingOfDailyRepo.findbyPeriodOrderByYmd(employeeId, datePeriod);
		}
	}
}
