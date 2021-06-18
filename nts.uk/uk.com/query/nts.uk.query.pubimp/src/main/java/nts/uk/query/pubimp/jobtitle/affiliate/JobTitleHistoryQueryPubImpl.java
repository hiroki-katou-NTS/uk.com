package nts.uk.query.pubimp.jobtitle.affiliate;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.query.app.jobtitle.affiliate.JobTitleHistoryQueryProcessor;
import nts.uk.query.pub.jobtitle.affiliate.JobTitleHistoryQueryPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JobTitleHistoryQueryPubImpl implements JobTitleHistoryQueryPub {

	@Inject JobTitleHistoryQueryProcessor jobTitleHistoryQueryProcessor;
	
	@Override
	public List<String> getEmployees(List<String> sids, GeneralDate baseDate) {
		return this.jobTitleHistoryQueryProcessor.getEmployees(sids, baseDate);
	}

}
