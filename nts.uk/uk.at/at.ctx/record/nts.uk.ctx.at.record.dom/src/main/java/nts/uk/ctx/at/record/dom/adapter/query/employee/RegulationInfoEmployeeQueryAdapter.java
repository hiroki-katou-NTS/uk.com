package nts.uk.ctx.at.record.dom.adapter.query.employee;

import java.util.Collection;
import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface RegulationInfoEmployeeQueryAdapter {

	public List<RegulationInfoEmployeeQueryR> search(RegulationInfoEmployeeQuery query);
	
	public List<EmployeeSearchInfoDto> search(Collection<String> employeeIds, DatePeriod date);
}
