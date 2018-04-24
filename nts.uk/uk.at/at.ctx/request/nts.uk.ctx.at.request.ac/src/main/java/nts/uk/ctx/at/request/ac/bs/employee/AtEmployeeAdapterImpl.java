package nts.uk.ctx.at.request.ac.bs.employee;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.bs.employee.AtEmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.employee.dto.EmployeeInfoImport;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;

@Stateless
public class AtEmployeeAdapterImpl implements AtEmployeeRequestAdapter{

	@Inject
	private SyEmployeePub employeePub;
	@Override
	public List<EmployeeInfoImport> getByListSID(List<String> sIds) {
		return employeePub.getByListSid(sIds).stream().map(x -> {
			return new EmployeeInfoImport(x.getSid(), x.getScd(), x.getBussinessName());
		}).collect(Collectors.toList());
	}

}
