package nts.uk.ctx.bs.employee.pubimp.employee;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employeeinfo.EmployeeRepository;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDto;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;

public class EmployeeInfoPubImp implements EmployeeInfoPub{
	
	@Inject
	private EmployeeRepository repo;

	@Override
	public EmployeeInfoDto findByCid(String companyId, String employeeCode) {
		// TODO Auto-generated method stub
		return null;
	}

}
