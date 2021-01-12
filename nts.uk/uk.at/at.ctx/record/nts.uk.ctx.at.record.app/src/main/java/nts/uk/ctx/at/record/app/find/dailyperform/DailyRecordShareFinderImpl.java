package nts.uk.ctx.at.record.app.find.dailyperform;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordShareFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

@Stateless
public class DailyRecordShareFinderImpl implements DailyRecordShareFinder {

	@Inject
	private DailyRecordWorkFinder finder;

	@Override
	public IntegrationOfDaily find(String employeeId, GeneralDate date) {
		return finder.find(employeeId, date).toDomain(employeeId, date);
	}

}
