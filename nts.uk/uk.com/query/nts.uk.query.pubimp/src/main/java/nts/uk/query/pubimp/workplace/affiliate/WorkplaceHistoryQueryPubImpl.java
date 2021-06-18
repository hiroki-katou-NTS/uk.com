package nts.uk.query.pubimp.workplace.affiliate;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.query.app.workplace.affiliate.WorkplaceHistoryQueryProcessor;
import nts.uk.query.pub.workplace.affiliate.WorkplaceHistoryQueryPub;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class WorkplaceHistoryQueryPubImpl implements WorkplaceHistoryQueryPub {

	@Inject
	private WorkplaceHistoryQueryProcessor workplaceHistoryQueryProcessor;
	
	@Override
	public List<String> getEmployees(List<String> sids, GeneralDate baseDate) {
		return workplaceHistoryQueryProcessor.getEmployees(sids, baseDate);
	}

}
