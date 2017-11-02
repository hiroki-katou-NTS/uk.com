package repository.person.emergencycontact;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import entity.person.emergencycontact.BpsmtEmergencyContact;
import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyContact;
import nts.uk.ctx.bs.person.dom.person.emergencycontact.PersonEmergencyCtRepository;

@Stateless
public class JpaPerEmergencyCtRepository extends JpaRepository implements PersonEmergencyCtRepository {

	public final String GET_ALL_BY_PID = "SELECT c FROM BpsmtEmergencyContact c WHERE c.pid = :pid";

	private List<PersonEmergencyContact> toListEmergencyContacts(List<BpsmtEmergencyContact> listEntity) {

		List<PersonEmergencyContact> lstEmergencyContact = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				PersonEmergencyContact emergencyContact = toDomainEmergencyContact(c);

				lstEmergencyContact.add(emergencyContact);
			});
		}
		return lstEmergencyContact;
	}

	
	private PersonEmergencyContact toDomainEmergencyContact(BpsmtEmergencyContact entity) {
		val domain = PersonEmergencyContact.createFromJavaType(entity.bpsmtEmergencyContactPK.emergencyCtId, entity.pid,
				entity.personName, entity.mailAddress, entity.streetAddress, entity.phone, entity.priority,
				entity.relationShip);
		return domain;
	}

	@Override
	public PersonEmergencyContact getByid(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonEmergencyContact> getListbyPid(String pid) {
		List<BpsmtEmergencyContact> listEntity = this.queryProxy().query(GET_ALL_BY_PID, BpsmtEmergencyContact.class)
				.setParameter("pid", pid).getList();
		return toListEmergencyContacts(listEntity);
	}

}
