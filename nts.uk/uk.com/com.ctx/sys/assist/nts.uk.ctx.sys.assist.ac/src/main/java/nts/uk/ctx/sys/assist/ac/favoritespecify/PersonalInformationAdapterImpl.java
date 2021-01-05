package nts.uk.ctx.sys.assist.ac.favoritespecify;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.EmpInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.sys.assist.dom.favorite.adapter.PersonalInformationAdapter;

public class PersonalInformationAdapterImpl implements PersonalInformationAdapter {

	@Inject
	public SyEmployeePub pub;

	@Override
	public HashMap<String, EmployeeBasicImport> getPersonalInformation(List<String> lstSid) {
		HashMap<String, EmployeeBasicImport> result = new HashMap<String, EmployeeBasicImport>();
		List<EmpInfoExport> lstEmployee = pub.getEmpInfo(lstSid);
		for (EmpInfoExport x : lstEmployee) {
			result.put(x.getEmployeeId(), new EmployeeBasicImport(x.getEmployeeId(), x.getPId(), x.getBusinessName()));
		}
		return result;
	}
}
