package nts.uk.ctx.at.function.dom.holidaysremaining.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHolidayOutput;

public interface SpecialHolidayRepository {
	
	List<SpecialHolidayOutput> getProcessExecutionLogByCompanyId(String companyID);
	
	Optional<SpecialHolidayOutput> get(String code, String holidayCode, String companyID);

	void insert(SpecialHolidayOutput domain);

	void update(SpecialHolidayOutput domain);

	void remove(String code, String holidayCode, String companyID);

}
