package nts.uk.ctx.bs.person.infra.repository.person.contact;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContactRepository;
import nts.uk.ctx.bs.person.infra.entity.person.contact.BpsmtPersonContact;
import nts.uk.ctx.bs.person.infra.entity.person.contact.BpsmtPersonContactPK;

@Stateless
public class JpaPersonContactRepository  extends JpaRepository implements PersonContactRepository {
	
	private static final String GET_BY_LIST = "SELECT pc FROM BpsmtPersonContact pc WHERE pc.bpsmtPersonContactPK.pid IN :personIdList";

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
	
	private PersonContact toDomain(BpsmtPersonContact entity) {
		String personId = entity.bpsmtPersonContactPK.pid;
		String phoneNumber = entity.cellPhoneNumber != null ? entity.cellPhoneNumber : null;
		String mailAddress = entity.mailAdress != null ? entity.mailAdress : null;
		String mobileMailAdd = entity.mobileMailAdress != null ? entity.mobileMailAdress : null;
		String memo1 = entity.memo1 != null ? entity.memo1 : null;
		String contactName1 = entity.contactName1 != null ? entity.contactName1 : null;
		String phoneNumber1 = entity.phoneNo1 != null? entity.phoneNo1 : null;
		String memo2 = entity.memo2 != null ? entity.memo2 : null;
		String contactName2 = entity.contactName2 != null ? entity.contactName2 : null;
		String phoneNumber2 = entity.phoneNo2 != null? entity.phoneNo2 : null;
		return new PersonContact(personId, phoneNumber, mailAddress, mobileMailAdd, memo1, contactName1, phoneNumber1, memo2, contactName2, phoneNumber2);
	}
	
	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private BpsmtPersonContact toEntity(PersonContact domain){
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(domain.getPersonId());
		BpsmtPersonContact entity = new BpsmtPersonContact(key,domain.getCellPhoneNumber().v(), domain.getMailAdress().v(),
				domain.getMobileMailAdress().v(), 
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
		entity.mailAdress = domain.getMailAdress().v();
		entity.mobileMailAdress = domain.getMobileMailAdress().v();
		entity.cellPhoneNumber = domain.getCellPhoneNumber().v();
		entity.memo1 = domain.getEmergencyContact1().getMemo().v();
		entity.contactName1 = domain.getEmergencyContact1().getContactName().v();
		entity.phoneNo1 = domain.getEmergencyContact1().getPhoneNumber().v();
		entity.memo2 = domain.getEmergencyContact2().getMemo().v();
		entity.contactName2 = domain.getEmergencyContact2().getContactName().v();
		entity.phoneNo2 = domain.getEmergencyContact2().getPhoneNumber().v();
			
	}

	/**
	 * get domain PersonContact by person id
	 * @param perId -- person id
	 */
	@Override
	public Optional<PersonContact> getByPId(String perId) {
		BpsmtPersonContactPK key = new BpsmtPersonContactPK(perId);
		Optional<BpsmtPersonContact> entity = this.queryProxy().find(key, BpsmtPersonContact.class);
		if(entity.isPresent())
			return Optional.of(toDomain(entity.get()));
		else return Optional.empty();
	}

	@Override
	public List<PersonContact> getByPersonIdList(List<String> personIds) {
		List<BpsmtPersonContact> entities = this.queryProxy().query(GET_BY_LIST, BpsmtPersonContact.class)
				.setParameter("personIdList", personIds).getList();
		return entities.stream().map(ent -> toDomain(ent)).collect(Collectors.toList());
	}

}
