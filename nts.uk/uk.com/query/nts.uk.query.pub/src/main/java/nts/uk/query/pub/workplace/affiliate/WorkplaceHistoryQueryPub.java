package nts.uk.query.pub.workplace.affiliate;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface WorkplaceHistoryQueryPub {
	
	public List<String> getEmployees(List<String> sids, GeneralDate baseDate);

}
