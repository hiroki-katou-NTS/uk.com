package nts.uk.ctx.at.request.ac.employeemanage;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.employeemanage.EmployeeManageRQAdapter;

@Stateless
public class EmployeeManageRQAdapterImpl implements EmployeeManageRQAdapter {

	@Inject
	private EmployeeManageAdapter employeePublisher;
	
	@Override
	public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
		return employeePublisher.getListEmpID(companyID, referenceDate);
	}

}
