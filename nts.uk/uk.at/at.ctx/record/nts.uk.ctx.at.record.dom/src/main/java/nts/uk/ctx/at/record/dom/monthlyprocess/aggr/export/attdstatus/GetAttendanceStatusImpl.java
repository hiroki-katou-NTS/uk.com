package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export.attdstatus;

import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.AttendanceStatus;
import nts.uk.ctx.at.shared.dom.scherec.attdstatus.GetAttendanceStatus;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	public Optional<AttendanceStatus> get(String employeeId, GeneralDate targetDate) {
		
		AttendanceStatusList attendanceStatusList = new AttendanceStatusList(
				employeeId, new DatePeriod(targetDate, targetDate),
				this.attendanceTimeOfDailyRepo,
				this.timeLeavingOfDailyRepo);
		if (attendanceStatusList.getMap().containsKey(targetDate)){
			return Optional.of(attendanceStatusList.getMap().get(targetDate));
		}
		return Optional.empty();
	}
	
	/** 取得 */
	@Override
	public Map<GeneralDate, AttendanceStatus> getMap(String employeeId, DatePeriod period) {
		
		AttendanceStatusList attendanceStatusList = new AttendanceStatusList(
				employeeId, period,
				this.attendanceTimeOfDailyRepo,
				this.timeLeavingOfDailyRepo);
		return attendanceStatusList.getMap();
	}
}
