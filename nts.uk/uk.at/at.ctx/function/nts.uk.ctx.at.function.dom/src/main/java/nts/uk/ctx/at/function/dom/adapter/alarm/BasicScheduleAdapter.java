package nts.uk.ctx.at.function.dom.adapter.alarm;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface BasicScheduleAdapter {
	
	List<BasicScheduleImport> getByEmpIdsAndPeriod(List<String> empIds, DatePeriod period);

}
