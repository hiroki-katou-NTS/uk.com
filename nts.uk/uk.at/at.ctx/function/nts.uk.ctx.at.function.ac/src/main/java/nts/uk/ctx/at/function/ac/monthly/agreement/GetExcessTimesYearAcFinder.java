package nts.uk.ctx.at.function.ac.monthly.agreement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.monthly.agreement.GetExcessTimesYearAdapter;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetExcessTimesYearPub;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * @author dat.lh
 *
 */
@Stateless
public class GetExcessTimesYearAcFinder implements GetExcessTimesYearAdapter {
	@Inject
	GetExcessTimesYearPub getExcessTimesYearPub;

	@Override
	public int algorithm(String employeeId, Year year) {
		return getExcessTimesYearPub.algorithm(employeeId, year).getExcessTimes();
	}

}
