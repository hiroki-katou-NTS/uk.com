package nts.uk.ctx.sys.auth.ac.employee.employeeinfo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoDtoExport;
import nts.uk.ctx.bs.employee.pub.employee.employeeInfo.EmployeeInfoPub;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInfoAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInfoImport;

@Stateless
public class AuthEmployeeInfoAdapterImpl implements EmployeeInfoAdapter {

	@Inject
	private EmployeeInfoPub employeeInfoPub;
	
	@Override
	public List<EmployeeInfoImport> getEmployeesAtWorkByBaseDate(String companyId, GeneralDate baseDate) {
		val listEmployeeInfoExport = employeeInfoPub.getEmployeesAtWorkByBaseDate(companyId, baseDate);
		
		List<EmployeeInfoImport> result = new ArrayList<EmployeeInfoImport>();
		for (EmployeeInfoDtoExport exportData : listEmployeeInfoExport) {
			result.add(new EmployeeInfoImport(exportData.getCompanyId(), exportData.getEmployeeCode(), exportData.getEmployeeId(), exportData.getPerName(), exportData.getPersonId()));
		}
		
		return result;
	}

}
