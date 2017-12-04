package nts.uk.ctx.at.record.dom.actualworkinghours.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;

public interface AttendanceTimeRepository {
	void add(AttendanceTimeOfDailyPerformance attendanceTime);
	
	void update(AttendanceTimeOfDailyPerformance attendanceTime);
	
	Optional<AttendanceTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	List<AttendanceTimeOfDailyPerformance> findAllOf(String employeeId, List<GeneralDate> ymd);
	
	
}
