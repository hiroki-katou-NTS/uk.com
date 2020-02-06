package nts.uk.ctx.at.function.ac.multipemonth;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.ActualMultipleMonthAdapter;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.record.pub.monthly.GetMonthlyRecordPub;
import nts.uk.ctx.at.record.pub.monthly.MonthlyRecordValuesExport;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

@Stateless
public class ActualMultipleMonthAdapterImpl implements ActualMultipleMonthAdapter {

	@Inject
	private GetMonthlyRecordPub getMonthlyRecord;

	@Override
	public Map<String, List<MonthlyRecordValueImport>> getActualMultipleMonth(List<String> employeeIds, YearMonthPeriod period,
			List<Integer> itemIds) {
		Map<String, List<MonthlyRecordValuesExport>> x = getMonthlyRecord.getRecordValues(employeeIds, period, itemIds);
		Map<String, List<MonthlyRecordValueImport>> y =
		    x.entrySet().stream()
		        .collect(Collectors.toMap(
		            e -> e.getKey(),
		            e -> (e.getValue().stream().map(m -> {
		            	List<ItemValue> itemValues = m.getItemValues();
		    			return MonthlyRecordValueImport.of( m.getYearMonth(),itemValues);
		    		}).collect(Collectors.toList())
		            )
		        ));
		return y;
	}
}
