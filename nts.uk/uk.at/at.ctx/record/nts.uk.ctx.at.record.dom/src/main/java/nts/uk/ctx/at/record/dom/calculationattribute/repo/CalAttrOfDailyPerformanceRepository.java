package nts.uk.ctx.at.record.dom.calculationattribute.repo;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface CalAttrOfDailyPerformanceRepository {

	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate);
	
	public List<CalAttrOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate);

	public void update(CalAttrOfDailyPerformance domain);

	public void add(CalAttrOfDailyPerformance domain);
	
	void deleteByKey(String employeeId, GeneralDate baseDate);
}
