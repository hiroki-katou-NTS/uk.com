package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import nts.arc.time.GeneralDate;

public interface AffiliationInforOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate ymd);
}
