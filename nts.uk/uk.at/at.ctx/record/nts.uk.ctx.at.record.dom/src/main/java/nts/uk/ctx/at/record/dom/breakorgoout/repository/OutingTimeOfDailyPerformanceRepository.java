package nts.uk.ctx.at.record.dom.breakorgoout.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.breakorgoout.OutingTimeSheet;
import nts.uk.ctx.at.record.dom.breakorgoout.primitivevalue.OutingFrameNo;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface OutingTimeOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	Optional<OutingTimeOfDailyPerformance> findByEmployeeIdAndDate(String employeeId, GeneralDate ymd);
	
	List<OutingTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd);
	
	List<OutingTimeOfDailyPerformance> finds(Map<String, GeneralDate> param);
	
	void add(OutingTimeOfDailyPerformance outing);
	
	void insert(OutingTimeOfDailyPerformance outingTimeOfDailyPerformance);
	
	void update(OutingTimeOfDailyPerformance outingTimeOfDailyPerformance);
	
	void updateOneDataInlist(String employeeId, GeneralDate ymd, OutingTimeSheet outingTimeSheet);
	
	boolean checkExistData(String employeeId, GeneralDate ymd, OutingFrameNo outingFrameNo);

}
