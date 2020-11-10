package nts.uk.ctx.bs.employee.pubimp.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.command.employee.data.management.EmployeeContactDto;
import nts.uk.ctx.bs.employee.dom.employee.data.management.contact.EmployeeContact;
import nts.uk.ctx.bs.employee.dom.employee.data.management.contact.EmployeeContactRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactObject;
import nts.uk.ctx.bs.employee.pub.contact.EmployeeContactPub;
import nts.uk.ctx.bs.employee.pub.contact.PersonContactObjectOfEmployee;
import nts.uk.ctx.bs.person.dom.person.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;

@Stateless
public class EmployeeContactPubImpl implements EmployeeContactPub {

	@Inject
	private EmployeeContactRepository empContactRepo;
	
	@Inject
	private EmployeeDataMngInfoRepository empDataMngInfoRepo;
	
	@Inject
	private PersonContactRepository personContactRepo;

	@Override
	public List<EmployeeContactObject> getList(List<String> employeeIds) {
		return empContactRepo.getByEmployeeIds(employeeIds).stream()
		.map(item -> convert(item))
		.collect(Collectors.toList());
	}

	private EmployeeContactObject convert(EmployeeContact item) {
		return new EmployeeContactObject(
				item.getEmployeeId(),
				item.getMailAddress().map(m -> m.v()).orElse(null),
				item.getSeatDialIn().map(m -> m.v()).orElse(null),
				item.getSeatExtensionNumber().map(m -> m.v()).orElse(null),
				item.getMobileMailAddress().map(m -> m.v()).orElse(null),
				item.getCellPhoneNumber().map(m -> m.v()).orElse(null)
				);
	}

	@Override
	public void register(String employeeId, String mailAddress, String phoneMailAddress, String cellPhoneNo) {
		
		Optional<EmployeeContact> existContact = empContactRepo.getByEmployeeId(employeeId);
		EmployeeContactDto dto = EmployeeContactDto.builder()
				.employeeId(employeeId)
				.mailAddress(mailAddress)
				.mobileMailAddress(phoneMailAddress)
				.cellPhoneNumber(cellPhoneNo)
				.build();

		EmployeeContact domain = EmployeeContact.createFromMemento(dto);
		if (existContact.isPresent()) {
			empContactRepo.update(domain);
		} else {
			empContactRepo.insert(domain);
		}
	}

	@Override
	public List<PersonContactObjectOfEmployee> getListOfEmployees(List<String> employeeIds) {
		Map<String, String> employeeIdPersonIdMap = empDataMngInfoRepo.findByListEmployeeId(employeeIds).stream()
				.collect(Collectors.toMap(x -> x.getEmployeeId(), x -> x.getPersonId()));

		Map<String, PersonContact> personContacts = personContactRepo
				.getByPersonIdList(new ArrayList<>(employeeIdPersonIdMap.values())).stream()
				.collect(Collectors.toMap(x -> x.getPersonId(), x -> x));
		List<PersonContactObjectOfEmployee> resultList = new ArrayList<>();
		employeeIdPersonIdMap.forEach((empId, perId) -> {
			PersonContact personContact = personContacts.get(perId);
			if(personContact != null) {
				PersonContactObjectOfEmployee personContactOfEmp = convert(empId, personContact);
				resultList.add(personContactOfEmp);
			}
		});
		return resultList;
	}
	
	private PersonContactObjectOfEmployee convert(String employeeId, PersonContact p) {
		PersonContactObjectOfEmployee pcObject = new PersonContactObjectOfEmployee();
		pcObject.setEmployeeId(employeeId);
		pcObject.setPersonId(p.getPersonId());
		pcObject.setCellPhoneNumber(p.getCellPhoneNumber().isPresent() ? p.getCellPhoneNumber().get().v() : null);
		pcObject.setMailAdress(p.getMailAdress().isPresent() ? p.getMailAdress().get().v() : null);
		pcObject.setMobileMailAdress(p.getMobileMailAdress().isPresent() ? p.getMobileMailAdress().get().v() : null);

		Optional<EmergencyContact> emerContact1 = p.getEmergencyContact1();
		if (emerContact1.isPresent()) {
			pcObject.setMemo1(emerContact1.get().getMemo().map(i->i.v()).orElse(null));
			pcObject.setContactName1(emerContact1.get().getContactName().map(i->i.v()).orElse(null));
			pcObject.setPhoneNumber1(emerContact1.get().getPhoneNumber().map(i->i.v()).orElse(null));
		}
		Optional<EmergencyContact> emerContact2 = p.getEmergencyContact2();
		if (emerContact2.isPresent()) {
			pcObject.setMemo2(emerContact2.get().getMemo().map(i->i.v()).orElse(null));
			pcObject.setContactName2(emerContact2.get().getContactName().map(i->i.v()).orElse(null));
			pcObject.setPhoneNumber2(emerContact2.get().getPhoneNumber().map(i->i.v()).orElse(null));
		}
		return pcObject;

	}

}
