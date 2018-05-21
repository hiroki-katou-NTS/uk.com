package nts.uk.ctx.bs.employee.pubimp.contact;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactObject;
import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeContactPubImpl implements EmployeeContactPub {

	@Inject
	private EmployeeInfoContactRepository empContactRepo;

	@Override
	public List<EmployeeContactObject> getList(List<String> employeeIds) {
		List<EmployeeInfoContact> empContact = empContactRepo.findByListEmpId(employeeIds);
		return empContact.stream().map(c -> convert(c)).collect(Collectors.toList());
	}

	private EmployeeContactObject convert(EmployeeInfoContact ec) {
		EmployeeContactObject ecDto = new EmployeeContactObject();
		ecDto.setSid(ec.getSid());

		if (ec.getMailAddress().isPresent()) {
			ecDto.setMailAddress(ec.getMailAddress().get().v());
		}

		if (ec.getSeatDialIn().isPresent()) {
			ecDto.setSeatDialIn(ec.getSeatDialIn().get().v());
		}

		if (ec.getSeatExtensionNo().isPresent()) {
			ecDto.setSeatExtensionNo(ec.getSeatExtensionNo().get().v());
		}

		if (ec.getPhoneMailAddress().isPresent()) {
			ecDto.setPhoneMailAddress(ec.getPhoneMailAddress().get().v());
		}

		if (ec.getCellPhoneNo().isPresent()) {
			ecDto.setCellPhoneNo(ec.getCellPhoneNo().get().v());
		}
		return ecDto;

	}

	@Override
	public void register(String employeeId, String mailAddress, String phoneMailAddress, String cellPhoneNo) {
		
		Optional<EmployeeInfoContact> existContact = empContactRepo.findByEmpId(employeeId);
		
		EmployeeInfoContact domain = EmployeeInfoContact.createFromJavaType(AppContexts.user().companyId(), employeeId,
				phoneMailAddress, null, null, phoneMailAddress, cellPhoneNo);
		
		if (existContact.isPresent()) {
			empContactRepo.update(domain);
		} else {
			empContactRepo.add(domain);
		}
	}

}
