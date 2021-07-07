package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface OuenWorkTimeSheetOfDailyRepo {

	public OuenWorkTimeSheetOfDaily find(String empId, GeneralDate ymd);
	
	public void update(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void insert(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void delete(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void remove(String sid, GeneralDate ymd);

	boolean findPK(String empId, GeneralDate ymd, int ouenNo);

	List<OuenWorkTimeSheetOfDaily> find(String sid, DatePeriod ymd);
	
	List<OuenWorkTimeSheetOfDaily> find(Map<String, List<GeneralDate>> param);

	void removePK(String sid, GeneralDate ymd, int ouenNo);
}
