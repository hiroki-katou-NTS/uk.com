package nts.uk.screen.com.app.find.cmm030.c;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetApprovalRootLastStartDateFinder {

	@Inject
	private Cmm030CScreenQuery screenQuery;
	
	public GeneralDate findData(String sid) {
		return this.screenQuery.getApprovalRootLastStartDate(sid).orElse(null);
	}
}
