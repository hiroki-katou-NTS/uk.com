package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;

public interface AffiliationInforOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate ymd);
	
	void add(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor);
	
	Optional<AffiliationInforOfDailyPerfor> findByKey(String employeeId, GeneralDate ymd);
	
	List<AffiliationInforOfDailyPerfor> finds(List<String> employeeId, DatePeriod ymd);
	
	List<AffiliationInforOfDailyPerfor> finds(Map<String, List<GeneralDate>> param);
	
	void updateByKey(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor);
}
