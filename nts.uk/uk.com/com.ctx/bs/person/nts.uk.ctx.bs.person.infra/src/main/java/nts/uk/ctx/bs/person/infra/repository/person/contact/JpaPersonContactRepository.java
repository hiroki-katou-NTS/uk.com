package nts.uk.ctx.bs.person.infra.repository.person.contact;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.ctx.bs.person.infra.entity.person.contact.BpsmtPersonContact;
import nts.uk.ctx.bs.person.infra.entity.person.contact.BpsmtPersonContactPK;

@Stateless
public class JpaPersonContactRepository  extends JpaRepository implements PersonContactRepository {

	@Override
	public void add(PersonContact domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PersonContact domain) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(domain.getPersonId());
		Optional<BpsmtPersonContact> entity = this.queryProxy().find(key, BpsmtPersonContact.class);
		
		if (entity.isPresent()){
			updateEntity(domain, entity.get());
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void delete(String pID) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(pID);
		this.commandProxy().remove(BpsmtPersonContact.class, key);
	}
	
	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private BpsmtPersonContact toEntity(PersonContact domain){
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(domain.getPersonId());
		BpsmtPersonContact entity = new BpsmtPersonContact(key, domain.getMailAdress().v(),
				domain.getMobileMailAdress().v(), domain.getCellPhoneNumber().v(),
				domain.getEmergencyContact1() != null ? domain.getEmergencyContact1().getMemo().v() : null,
				domain.getEmergencyContact1() != null ? domain.getEmergencyContact1().getContactName().v() : null,
				domain.getEmergencyContact1() != null ? domain.getEmergencyContact1().getPhoneNumber().v() : null,
				domain.getEmergencyContact2() != null ? domain.getEmergencyContact2().getMemo().v() : null,
				domain.getEmergencyContact2() != null ? domain.getEmergencyContact2().getContactName().v() : null,
				domain.getEmergencyContact2() != null ? domain.getEmergencyContact2().getPhoneNumber().v() : null);
		return entity;
	}
	
	/**
	 * Update entity
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(PersonContact domain, BpsmtPersonContact entity){
		
		if (domain.getMailAdress() != null && !domain.getMailAdress().equals("")){
			entity.mailAdress = domain.getMailAdress().v();
		}
		
		if (domain.getMobileMailAdress() != null && !domain.getMobileMailAdress().v().equals("")){
			entity.mobileMailAdress = domain.getMobileMailAdress().v();
		}
		
		if (domain.getCellPhoneNumber() != null && !domain.getCellPhoneNumber().v().equals("")){
			entity.cellPhoneNumber = domain.getCellPhoneNumber().v();
		}
		
		if (domain.getEmergencyContact1() != null && !domain.getEmergencyContact1().getMemo().v().equals("")){
			entity.memo1 = domain.getEmergencyContact1().getMemo().v();
		}
		
		if (domain.getEmergencyContact1() != null && !domain.getEmergencyContact1().getContactName().v().equals("")){
			entity.contactName1 = domain.getEmergencyContact1().getContactName().v();
		}
		
		if (domain.getEmergencyContact1() != null && !domain.getEmergencyContact1().getPhoneNumber().v().equals("")){
			entity.phoneNo1 = domain.getEmergencyContact1().getPhoneNumber().v();
		}
		
		if (domain.getEmergencyContact2() != null && !domain.getEmergencyContact2().getMemo().v().equals("")){
			entity.memo1 = domain.getEmergencyContact2().getMemo().v();
		}
		
		if (domain.getEmergencyContact2() != null && !domain.getEmergencyContact2().getContactName().v().equals("")){
			entity.contactName1 = domain.getEmergencyContact2().getContactName().v();
		}
		
		if (domain.getEmergencyContact2() != null && !domain.getEmergencyContact2().getPhoneNumber().v().equals("")){
			entity.phoneNo1 = domain.getEmergencyContact2().getPhoneNumber().v();
		}
			
	}

}
