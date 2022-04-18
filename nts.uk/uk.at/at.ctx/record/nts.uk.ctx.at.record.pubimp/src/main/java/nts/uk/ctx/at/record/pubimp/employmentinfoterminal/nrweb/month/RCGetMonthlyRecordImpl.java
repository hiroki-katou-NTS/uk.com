package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.nrweb.month;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm.MonthlyRecordValues;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm.RCGetMonthlyRecord;
import nts.uk.ctx.at.record.pub.monthly.GetMonthlyRecordPub;

@Stateless
public class RCGetMonthlyRecordImpl implements RCGetMonthlyRecord {

	@Inject
	private GetMonthlyRecordPub pub;

	@Override
	public Map<String, List<MonthlyRecordValues>> getRecordValues(List<String> employeeIds, YearMonthPeriod period,
			List<Integer> itemIds) {
		return pub.getRecordValues(employeeIds, period, itemIds).entrySet().stream()
				.collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().stream().map(y -> {
					return MonthlyRecordValues.of(y.getYearMonth(), y.getClosureId(), y.getClosureDate(),
							y.getItemValues());
				}).collect(Collectors.toList())));
	}
}
