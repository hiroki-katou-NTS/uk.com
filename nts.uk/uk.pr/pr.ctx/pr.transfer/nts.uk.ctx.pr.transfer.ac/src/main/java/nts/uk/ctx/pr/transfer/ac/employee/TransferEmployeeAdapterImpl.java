package nts.uk.ctx.pr.transfer.ac.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.pr.transfer.dom.adapter.employee.TransferEmployeeAdapter;

@Stateless
public class TransferEmployeeAdapterImpl implements TransferEmployeeAdapter {

	@Inject
	private SyEmployeePub employeePub;

	@Override
	public List<String> getListEmpIdOfLoginCompany(String companyId) {
		return employeePub.getListEmpOfLoginCompany(companyId).stream().map(i -> i.getSid())
				.collect(Collectors.toList());
	}

}
