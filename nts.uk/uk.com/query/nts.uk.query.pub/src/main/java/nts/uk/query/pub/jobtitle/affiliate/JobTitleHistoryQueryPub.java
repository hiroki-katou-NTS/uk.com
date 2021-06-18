package nts.uk.query.pub.jobtitle.affiliate;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface JobTitleHistoryQueryPub {

	public List<String> getEmployees(List<String> sids, GeneralDate baseDate);
}
