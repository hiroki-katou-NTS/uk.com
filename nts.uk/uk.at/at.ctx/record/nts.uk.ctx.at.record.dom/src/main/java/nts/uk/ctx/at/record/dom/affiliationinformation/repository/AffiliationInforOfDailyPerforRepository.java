package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.AffiliationInforOfDailyPerfor;

public interface AffiliationInforOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate ymd);
	
	void addEmploymentCode(AffiliationInforOfDailyPerfor affiliationInforOfDailyPerfor);
}
