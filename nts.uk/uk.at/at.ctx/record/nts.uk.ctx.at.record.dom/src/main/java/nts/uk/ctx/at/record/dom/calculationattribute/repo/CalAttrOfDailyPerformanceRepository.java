package nts.uk.ctx.at.record.dom.calculationattribute.repo;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;

public interface CalAttrOfDailyPerformanceRepository {

	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate);
	
	public List<CalAttrOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate);

	public List<CalAttrOfDailyPerformance> finds(Map<String, List<GeneralDate>> param);

	public void update(CalAttrOfDailyAttd domain, String employeeId, GeneralDate day);

	public void add(CalAttrOfDailyAttd domain, String employeeId, GeneralDate day);
	
	void deleteByKey(String employeeId, GeneralDate baseDate);
}
