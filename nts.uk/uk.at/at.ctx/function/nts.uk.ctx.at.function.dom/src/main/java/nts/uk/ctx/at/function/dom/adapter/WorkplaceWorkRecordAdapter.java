package nts.uk.ctx.at.function.dom.adapter;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface WorkplaceWorkRecordAdapter {
	List<WorkPlaceHistImport> getWplByListSidAndPeriod(List<String> sids,  DatePeriod datePeriod);
}
