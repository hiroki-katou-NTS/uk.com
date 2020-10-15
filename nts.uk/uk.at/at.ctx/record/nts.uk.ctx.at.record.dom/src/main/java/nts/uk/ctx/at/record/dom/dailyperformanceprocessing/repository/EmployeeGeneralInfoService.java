package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.generalinfo.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class EmployeeGeneralInfoService {

	@Inject
	private EmployeeGeneralInfoAdapter employeeGeneralInfoAdapter;

	public EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> emloyeeIds, DatePeriod periodTime) {

		EmployeeGeneralInfoImport employeeGeneralInfoImport = this.employeeGeneralInfoAdapter
				.getEmployeeGeneralInfo(emloyeeIds, periodTime, true, true, true, true, false);

		return employeeGeneralInfoImport;
	}

}
