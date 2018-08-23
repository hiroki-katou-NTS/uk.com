package nts.uk.ctx.at.request.ac.record.agreement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.monthly.agreement.GetExcessTimesYearPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.ExcessTimesYearAdapter;
import nts.uk.ctx.at.shared.dom.common.Year;

@Stateless
public class ExcessTimesYearAcFinder implements ExcessTimesYearAdapter {
	@Inject
	GetExcessTimesYearPub getExcessTimesYearPub;

	@Override
	public int getExcessTimesYear(String employeeId, Year year) {
		return getExcessTimesYearPub.algorithm(employeeId, year);
	}

}
