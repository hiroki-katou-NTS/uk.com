package storage;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.sys.assist.dom.storage.BusinessName;
import nts.uk.ctx.sys.assist.dom.storage.EmployeeCode;
import nts.uk.ctx.sys.assist.dom.storage.SysEmployeeStorageAdapter;
import nts.uk.ctx.sys.assist.dom.storage.TargetEmployees;

@Stateless
public class SysEmployeeStorageAdapterImpl implements SysEmployeeStorageAdapter{
	
	/** The SyEmployeePub pub. */
	@Inject
	private SyEmployeePub syEmployeePub;
	
	@Override
	public List<TargetEmployees> getListEmployeeByCompanyId(String cid) {
		return syEmployeePub.getListEmpOfLoginCompany(cid).stream().map(x -> {
			return new TargetEmployees(x.getSid(),new BusinessName(x.getBussinesName()),new EmployeeCode(x.getScd()) );
		}).collect(Collectors.toList());
	}

	@Override
	public List<TargetEmployees> getByListSid(List<String> sIds) {
		return syEmployeePub.getByListSid(sIds).stream()
				.map(e -> new TargetEmployees(e.getSid(), new BusinessName(e.getBussinessName()), new EmployeeCode(e.getScd())))
				.collect(Collectors.toList());
	}
}
