package nts.uk.ctx.sys.assist.ac.deletedata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.SyEmployeeAdapter;
import nts.uk.ctx.bs.employee.pubimp.employee.SyEmployeePub;

@Stateless
public class SyEmployeeAdapterImpl implements SyEmployeeAdapter {

	/** The RoleExportRepo pub. */
	@Inject
	private SyEmployeePub syEmployeePub;

	@Override
	public List<EmployeeDeletion> getListEmployeeByCompanyId(String cid) {
		return syEmployeePub.getListEmpOfLoginCompany(cid).stream().map(x -> {
			return new EmployeeDeletion(null, x.sid(), x.scode(), x.bussinesName());
		}).collect(Collectors.toList());
		return null;
		
	}

}
