package nts.uk.ctx.at.function.ac.employeemanage;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employeemanage.EmployeeManageAdapter;
import nts.uk.ctx.sys.auth.pub.employee.EmployeePublisher;

@Stateless
public class EmployeeManageAcFn implements EmployeeManageAdapter {

	@Inject
	private EmployeePublisher employeePublisher; 
	
	@Override
	public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
		List<String>  data = employeePublisher.getListEmpID(companyID, referenceDate);
		if(data.isEmpty())
			return Collections.emptyList();
		return data;
	}

}
