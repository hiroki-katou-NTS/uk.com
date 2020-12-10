package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface PayoutSubofHDManaRepository {
	
	void add(PayoutSubofHDManagement domain);
	
	void update(PayoutSubofHDManagement domain);
	
	void delete(String sid, GeneralDate occDate, GeneralDate digestDate);
	
	void delete(String sid1, String sid2, GeneralDate occDate, GeneralDate digestDate);
	
	List<PayoutSubofHDManagement> getByPayoutId(String sid, GeneralDate occDate);
	
	List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate);
	
	List<PayoutSubofHDManagement> getByListPayoutID(String sid, DatePeriod date);
	
	List<PayoutSubofHDManagement> getByListDate(String sid, List<GeneralDate> lstDate);
	
	List<PayoutSubofHDManagement> getByListOccDate(String sid, List<GeneralDate> lstDate);
	
	List<PayoutSubofHDManagement> getByListSubID(String sid, DatePeriod date);
	
	void delete(String sid, GeneralDate occDate);
	
	void deleteBySubID(String sid, GeneralDate digestDate);
}
