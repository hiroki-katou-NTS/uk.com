package nts.uk.ctx.at.function.ac.multipemonth;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.ActualMultipleMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.record.pub.monthly.GetMonthlyRecordPub;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class ActualMultipleMonthAdapterImpl implements ActualMultipleMonthAdapter {

	@Inject
	private GetMonthlyRecordPub getMonthlyRecord;

	@Override
	public List<MonthlyRecordValueImport> getActualMultipleMonth(String employeeId, YearMonthPeriod period,
			List<Integer> itemIds) {

		return getMonthlyRecord.algorithm(employeeId, period, itemIds).stream().map(m -> {
			return new MonthlyRecordValueImport( m.getYearMonth());
		}).collect(Collectors.toList());
	}
}
