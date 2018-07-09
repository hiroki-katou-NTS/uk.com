package nts.uk.ctx.at.record.dom.calculationattribute.repo;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.calculationattribute.CalAttrOfDailyPerformance;

public interface NCalAttrOfDailyPerformanceRepository {
	public CalAttrOfDailyPerformance find(String employeeId, GeneralDate baseDate);
	
	public void update(CalAttrOfDailyPerformance domain);

	public void add(CalAttrOfDailyPerformance domain);
	
	void deleteByKey(String employeeId, GeneralDate baseDate);
}
