package nts.uk.ctx.at.function.dom.adapter.alarm;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface BasicScheduleAdapter {
	
	List<BasicScheduleImport> getByEmpIdsAndPeriod(List<String> empIds, DatePeriod period);
	
	Optional<GeneralDate> acquireMaxDateBasicSchedule(List<String> empIds);

}
