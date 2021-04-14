package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface OuenWorkTimeSheetOfDailyRepo {

	public List<OuenWorkTimeSheetOfDaily> find(String empId, GeneralDate ymd);
	
	public void update(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void insert(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void delete(List<OuenWorkTimeSheetOfDaily> domain);
	
	//	日別実績の応援作業別勤怠時間帯Repository.get(社員,期間)	
	public List<OuenWorkTimeSheetOfDaily> findByDatePeriod(String empId, DatePeriod datePeriod);
}
