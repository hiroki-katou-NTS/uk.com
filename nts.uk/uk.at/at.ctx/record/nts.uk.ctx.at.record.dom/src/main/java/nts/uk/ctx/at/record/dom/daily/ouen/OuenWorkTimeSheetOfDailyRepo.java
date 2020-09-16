package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface OuenWorkTimeSheetOfDailyRepo {

	public List<OuenWorkTimeSheetOfDaily> find(String empId, GeneralDate ymd);
	
	public void update(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void insert(List<OuenWorkTimeSheetOfDaily> domain);
	
	public void delete(List<OuenWorkTimeSheetOfDaily> domain);
}
