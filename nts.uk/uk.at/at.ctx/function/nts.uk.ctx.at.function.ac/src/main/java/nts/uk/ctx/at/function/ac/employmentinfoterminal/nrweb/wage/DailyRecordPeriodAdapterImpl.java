package nts.uk.ctx.at.function.ac.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.DailyRecordPeriodAdapter;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage.DailyPeriodRecordPub;

/**
* @author sakuratani
*
*			日別実績データが存在する期間を取得するAdapterImpl
*
*/
@Stateless
public class DailyRecordPeriodAdapterImpl implements DailyRecordPeriodAdapter {

	@Inject
	DailyPeriodRecordPub dailyPeriodRecordPub;

	@Override
	public Optional<DatePeriod> get(String employeeId, DatePeriod period) {
		return dailyPeriodRecordPub.get(employeeId, period);
	}

}
