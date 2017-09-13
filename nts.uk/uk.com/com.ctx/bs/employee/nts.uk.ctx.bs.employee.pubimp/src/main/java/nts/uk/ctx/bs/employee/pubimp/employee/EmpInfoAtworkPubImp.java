package nts.uk.ctx.bs.employee.pubimp.employee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.atworkreferdate.EmployeeAtworkReferDate;

@Stateless
public class EmpInfoAtworkPubImp implements EmployeeAtworkReferDate {

	@Inject
	private EmployeeRepository repo;

	@Override
	public List<EmployeeInfoDtoExport> getListEmployee(String companyId, String standardDate) {
		
		return null;
	}

}
