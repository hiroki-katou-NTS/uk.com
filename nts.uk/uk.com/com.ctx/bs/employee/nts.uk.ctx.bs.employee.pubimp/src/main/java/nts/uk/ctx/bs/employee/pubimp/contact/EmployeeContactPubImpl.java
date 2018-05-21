package nts.uk.ctx.bs.employee.pubimp.contact;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactObject;
import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactPub;

@Stateless
public class EmployeeContactPubImpl implements EmployeeContactPub{
	
	@Inject
	private EmployeeInfoContactRepository empContactRepo;

	@Override
	public List<EmployeeContactObject> getList(List<String> employeeIds) {
		List<EmployeeInfoContact> empContact = empContactRepo.findByListEmpId(employeeIds);
		return empContact.stream()
				.map(c -> new EmployeeContactObject(c.getSid(), c.getMailAddress().v(), c.getSeatDialIn().v(),
						c.getSeatExtensionNo().v(), c.getPhoneMailAddress().v(), c.getCellPhoneNo().v()))
				.collect(Collectors.toList());
	}

}
