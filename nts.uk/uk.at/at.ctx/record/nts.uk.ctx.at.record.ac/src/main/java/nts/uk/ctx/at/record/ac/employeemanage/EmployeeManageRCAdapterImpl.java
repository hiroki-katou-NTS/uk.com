package nts.uk.ctx.at.record.ac.employeemanage;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employeemanage.EmployeeManageRCAdapter;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;

@Stateless
public class EmployeeManageRCAdapterImpl implements EmployeeManageRCAdapter {

	@Inject
	private EmployeePublisher employeePublisher;

	@Override
	public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
		return employeePublisher.getListEmpID(companyID, referenceDate);
	}

}
