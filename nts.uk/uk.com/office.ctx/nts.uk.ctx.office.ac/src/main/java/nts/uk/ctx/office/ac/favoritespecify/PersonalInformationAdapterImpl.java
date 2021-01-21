package nts.uk.ctx.office.ac.favoritespecify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.EmpInfoExport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.PersonalInformationAdapter;

@Stateless
public class PersonalInformationAdapterImpl implements PersonalInformationAdapter {

	@Inject
	public SyEmployeePub pub;

	@Override
	public Map<String, EmployeeBasicImport> getPersonalInformation(List<String> lstSid) {
		Map<String, EmployeeBasicImport> result = new HashMap<String, EmployeeBasicImport>();
		List<EmpInfoExport> lstEmployee = pub.getEmpInfo(lstSid);
		for (EmpInfoExport x : lstEmployee) {
			result.put(x.getEmployeeId(), new EmployeeBasicImport(x.getEmployeeId(), x.getPId(), x.getBusinessName(), x.getEmployeeCode()));
		}
		return result;
	}
}
