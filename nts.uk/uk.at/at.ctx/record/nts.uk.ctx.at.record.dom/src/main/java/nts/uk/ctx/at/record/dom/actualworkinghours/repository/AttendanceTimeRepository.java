package nts.uk.ctx.at.record.dom.actualworkinghours.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface AttendanceTimeRepository {
	void add(AttendanceTimeOfDailyPerformance attendanceTime);
	
	void update(AttendanceTimeOfDailyPerformance attendanceTime);
	
	Optional<AttendanceTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	List<AttendanceTimeOfDailyPerformance> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);
	
	List<AttendanceTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd);
	
	List<AttendanceTimeOfDailyPerformance> finds(Map<String, List<GeneralDate>> param);
	
	void updateFlush(AttendanceTimeOfDailyPerformance attendanceTime);
	
	void deleteByEmployeeIdAndDate(String employeeId, GeneralDate ymd);
	
	List<Integer> findAtt(String employeeId, List<GeneralDate> ymd);
	
	List<AttendanceTimeOfDailyPerformance> find(String employeeId, List<GeneralDate> ymd);
	
}
