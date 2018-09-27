package nts.uk.ctx.at.function.ac.alarm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.alarm.EmployeePubAlarmAdapter;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;

@Stateless
public class EmployeePubAlarmAdapterImpl implements EmployeePubAlarmAdapter {
	@Inject
	private EmployeePublisher employeePublisher;

	@Override
	public List<String> getListEmployeeId(String workplaceId, GeneralDate executeDate) {
		return employeePublisher.getListEmployeeId(workplaceId, executeDate);
	}

}
