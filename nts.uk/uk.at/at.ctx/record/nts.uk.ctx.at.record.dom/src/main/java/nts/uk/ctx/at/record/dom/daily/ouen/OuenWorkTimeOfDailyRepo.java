package nts.uk.ctx.at.record.dom.daily.ouen;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface OuenWorkTimeOfDailyRepo {

	public OuenWorkTimeOfDaily find(String empId, GeneralDate ymd);
	
	public void update(List<OuenWorkTimeOfDaily> domain);
	
	public void insert(List<OuenWorkTimeOfDaily> domain);
	
	public void delete(List<OuenWorkTimeOfDaily> domain);
	
	public void remove(String sid, GeneralDate ymd);
}
