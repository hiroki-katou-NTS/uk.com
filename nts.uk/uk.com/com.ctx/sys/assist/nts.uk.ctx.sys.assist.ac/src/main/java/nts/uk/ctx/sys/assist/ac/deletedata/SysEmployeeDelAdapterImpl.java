package nts.uk.ctx.sys.assist.ac.deletedata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.deletedata.EmployeeDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.SysEmployeeDelAdapter;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;

@Stateless
public class SysEmployeeDelAdapterImpl implements SysEmployeeDelAdapter {

	/** The SyEmployeePub pub. */
	@Inject
	private SyEmployeePub syEmployeePub;

	@Override
	public List<EmployeeDeletion> getListEmployeeByCompanyId(String cid) {
		return syEmployeePub.getListEmpOfLoginCompany(cid).stream().map(x -> {
			return new EmployeeDeletion(x.getSid(), x.getScd(), x.getBussinesName());
		}).collect(Collectors.toList());
	}
}
