package nts.uk.ctx.at.function.ac.standardtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.standardtime.GetAgreementPeriodFromYearAdapter;
import nts.uk.ctx.at.record.pub.standardtime.GetAgreementPeriodFromYearPub;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;


/**
 * @author dat.lh
 *
 */
@Stateless
public class GetAgreementPeriodFromYearAcFinder implements GetAgreementPeriodFromYearAdapter {
	@Inject
	GetAgreementPeriodFromYearPub getAgreementPeriodFromYearPub;

	@Override
	public Optional<DatePeriod> algorithm(Year year, Closure closure) {
		return getAgreementPeriodFromYearPub.algorithm(year);
	}

}
