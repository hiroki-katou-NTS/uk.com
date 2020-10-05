package nts.uk.ctx.at.record.dom.calculationattribute.repo;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calcategory.CalAttrOfDailyAttd;

public interface CalAttrOfDailyPerformanceRepository {

	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate);
	
	public List<CalAttrOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate);

	public List<CalAttrOfDailyPerformance> finds(Map<String, List<GeneralDate>> param);

	public void update(CalAttrOfDailyPerformance domain);

	public void add(CalAttrOfDailyPerformance domain);
	
	void deleteByKey(String employeeId, GeneralDate baseDate);
}
