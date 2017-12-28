package nts.uk.ctx.sys.portal.ac.find.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.sys.portal.dom.adapter.employee.EmployeeAdapter;
import nts.uk.ctx.sys.portal.dom.adapter.employee.EmployeeDto;

@Stateless
public class EmployeePortalAdapterImpl implements EmployeeAdapter {
	
	@Inject
	private EmployeeInfoPub employeePub;
	
	@Override
	public Optional<EmployeeDto> getEmployee(String companyId, String personId) {
		return employeePub.getEmployeeInfoByCidPid(companyId, personId)
				.map(e -> new EmployeeDto(e.getCompanyId(), e.getPersonId(), e.getEmployeeId(),
						e.getEmployeeCode(), e.getDeletedStatus(), e.getDeleteDateTemporary(),
						e.getRemoveReason(), e.getExternalCode()));
	}
}
