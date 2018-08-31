package nts.uk.ctx.bs.person.pubimp.contact;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.person.dom.person.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.ctx.bs.person.pub.contact.PersonContactObject;
import nts.uk.ctx.bs.person.pub.contact.PersonContactPub;

@Stateless
public class PersonContactPubImpl implements PersonContactPub {

	@Inject
	private PersonContactRepository personContactRepo;

	@Override
	public List<PersonContactObject> getList(List<String> personIds) {
		List<PersonContact> personContactList = personContactRepo.getByPersonIdList(personIds);
		return personContactList.stream().map(p -> convertToDto(p)).collect(Collectors.toList());
	}

	private PersonContactObject convertToDto(PersonContact p) {

		PersonContactObject pcObject = new PersonContactObject();
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

	@Override
	public void register(String personId, String cellPhoneNumber, String mailAddress, String mobileMailAdress) {
		Optional<PersonContact> personContactOpt = personContactRepo.getByPId(personId);
		PersonContact domain = PersonContact.createFromJavaType(personId, cellPhoneNumber, mailAddress,
				mobileMailAdress);
		if (personContactOpt.isPresent()) {
			personContactRepo.update(domain);
		} else {
			personContactRepo.add(domain);
		}
	}

}
