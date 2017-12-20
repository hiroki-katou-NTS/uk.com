package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;

public interface AffiliationInforOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate ymd);
	
	void add(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	Optional<AffiliationInforOfDailyPerfor> findByKey(String employeeId, GeneralDate ymd);
	
	void updateByKey(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor);
}
