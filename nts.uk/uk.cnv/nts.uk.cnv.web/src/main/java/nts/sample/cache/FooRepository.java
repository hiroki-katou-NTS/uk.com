package nts.sample.cache;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface FooRepository {

	List<Foo> find(String employeeId, DatePeriod period);
	
	List<Foo> find(List<String> employeeId, DatePeriod period);
}
