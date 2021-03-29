package nts.uk.ctx.at.request.ac.record.agreement;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.pub.monthly.agreement.GetExcessTimesYearPub;
import nts.uk.ctx.at.record.pub.monthly.agreement.export.AgreementExcessInfoExport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementExcessInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.ExcessTimesYearAdapter;

@Stateless
public class ExcessTimesYearAcFinder implements ExcessTimesYearAdapter {
	@Inject
	GetExcessTimesYearPub getExcessTimesYearPub;

	@Override
	public AgreementExcessInfoImport getExcessTimesYear(String employeeId, Year year) {
		AgreementExcessInfoExport data = getExcessTimesYearPub.algorithm(employeeId, year);
		return new AgreementExcessInfoImport(data.getExcessTimes(), data.getYearMonths());
	}

}
