package nts.uk.ctx.sys.env.ac.contact;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactPub;
import nts.uk.ctx.sys.env.dom.contact.EmployeeContactAdapter;
import nts.uk.ctx.sys.env.dom.contact.EmployeeContactObjectImport;

@Stateless
public class SyEmployeeContactImpl implements EmployeeContactAdapter {

	@Inject
	private EmployeeContactPub employeeContactPub;

	@Override
	public List<EmployeeContactObjectImport> getList(List<String> sIds) {
		return employeeContactPub.getList(sIds).stream()
				.map(x -> new EmployeeContactObjectImport(x.getSid(), x.getMailAddress(), x.getSeatDialIn(),
						x.getSeatExtensionNo(), x.getPhoneMailAddress(), x.getCellPhoneNo()))
				.collect(Collectors.toList());
	}

}
