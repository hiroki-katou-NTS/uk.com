package nts.uk.ctx.at.function.ac.monthly.agreement;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.GetAgreementPeriodAdapter;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetAgreementPeriodPub;

/**
 * @author LienPTK
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetAgreementPeriodFinder implements GetAgreementPeriodAdapter {
	
	@Inject
	private GetAgreementPeriodPub getAgreementPeriodPub;

	@Override
	public Optional<DatePeriod> byYear(String companyId, Year year) {
		return this.getAgreementPeriodPub.byYear(companyId, year);
	}

}
