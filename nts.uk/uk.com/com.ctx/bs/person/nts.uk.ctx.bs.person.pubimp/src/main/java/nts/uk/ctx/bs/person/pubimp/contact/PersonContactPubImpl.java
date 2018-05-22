package nts.uk.ctx.bs.person.pubimp.contact;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.ctx.bs.person.pub.contact.PersonContactObject;
import nts.uk.ctx.bs.person.pub.contact.PersonContactPub;

@Stateless
public class PersonContactPubImpl implements PersonContactPub{
	
	@Inject
	private PersonContactRepository personContactRepo;

	@Override
	public List<PersonContactObject> getList(List<String> personIds) {
		List<PersonContact> personContactList = personContactRepo.getByPersonIdList(personIds);
		return personContactList.stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}

	private PersonContactObject convertToDto(PersonContact p) {
		EmergencyContact emerContact1 = p.getEmergencyContact1();
		EmergencyContact emerContact2 = p.getEmergencyContact2();
		return new PersonContactObject(p.getPersonId(), p.getCellPhoneNumber().v(), p.getMailAdress().v(),
				p.getMobileMailAdress().v(), emerContact1.getMemo().v(), emerContact1.getContactName().v(),
				emerContact1.getPhoneNumber().v(), emerContact2.getMemo().v(), emerContact2.getContactName().v(),
				emerContact2.getPhoneNumber().v());
	}

}
