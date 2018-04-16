package nts.uk.ctx.at.function.dom.holidaysremaining.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHoliday;

public interface SpecialHolidayRepository {
	
	List<SpecialHoliday> getProcessExecutionLogByCompanyId(String companyID);
	
	Optional<SpecialHoliday> get(String code, String holidayCode, String companyID);

	void insert(SpecialHoliday domain);

	void update(SpecialHoliday domain);

	void remove(String code, String holidayCode, String companyID);

}
