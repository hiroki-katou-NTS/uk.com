package nts.sample.cache;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public interface FooHistoryRepository {

	Optional<FooHistoryItem> find(String employeeId, GeneralDate date);

	List<FooHistoryItem> find(String employeeId, DatePeriod period);
	
	List<FooHistoryItem> find(List<String> employeeIds, DatePeriod period);
}
