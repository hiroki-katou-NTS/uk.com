package nts.uk.ctx.at.record.dom.raisesalarytime.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface SpecificDateAttrOfDailyPerforRepo {

	public Optional<SpecificDateAttrOfDailyPerfor> find(String employeeId, GeneralDate baseDate);
	
	public List<SpecificDateAttrOfDailyPerfor> findByPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);
	
	public List<SpecificDateAttrOfDailyPerfor> finds(List<String> employeeId, DatePeriod baseDate);
	
	public List<SpecificDateAttrOfDailyPerfor> finds(Map<String, List<GeneralDate>> param);
	
	public void update(SpecificDateAttrOfDailyPerfor domain);

	public void add(SpecificDateAttrOfDailyPerfor domain);
	
	public void deleteByEmployeeIdAndDate(String employeeId, GeneralDate baseDate);
}
