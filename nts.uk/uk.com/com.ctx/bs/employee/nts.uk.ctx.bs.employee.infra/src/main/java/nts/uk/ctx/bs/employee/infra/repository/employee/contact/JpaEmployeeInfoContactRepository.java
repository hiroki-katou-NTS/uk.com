package nts.uk.ctx.bs.employee.infra.repository.employee.contact;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContact;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContactPK;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPersonPk;

@Stateless
public class JpaEmployeeInfoContactRepository extends JpaRepository implements EmployeeInfoContactRepository {

	@Override
	public void add(EmployeeInfoContact domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmployeeInfoContact domain) {
		
		BsymtEmpInfoContactPK key = new BsymtEmpInfoContactPK(domain.getSid());
		
		Optional<BsymtEmpInfoContact> entity = this.queryProxy().find(key, BsymtEmpInfoContact.class);
		
		if (entity.isPresent()){
			updateEntity(domain, entity.get());
			this.commandProxy().update(entity.get());
		}
	}

	@Override
	public void delete(String sid) {
		BsymtEmpInfoContactPK key = new BsymtEmpInfoContactPK(sid);
		this.commandProxy().remove(BsymtEmpInfoContact.class, key);
	}
	
	/**
	 * Convert domain to entity
	 * @param domain
	 * @return
	 */
	private BsymtEmpInfoContact toEntity(EmployeeInfoContact domain){
		BsymtEmpInfoContactPK key = new BsymtEmpInfoContactPK(domain.getSid());
		BsymtEmpInfoContact entity = new BsymtEmpInfoContact(key, domain.getCId(), domain.getMailAddress().v(),
				domain.getSeatDialIn().v(), domain.getSeatExtensionNo().v(), domain.getPhoneMailAddress().v(),
				domain.getCellPhoneNo().v());
		return entity;
	}
	
	/**
	 * Update entity
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(EmployeeInfoContact domain, BsymtEmpInfoContact entity){
		if (domain.getMailAddress() != null && !domain.getMailAddress().v().equals("")){
			entity.mailAdress = domain.getMailAddress().v();
		}
		
		if (domain.getSeatDialIn() != null && !domain.getSeatDialIn().v().equals("")){
			entity.seatDialIn = domain.getSeatDialIn().v();
		}
		
		if (domain.getSeatExtensionNo() != null && !domain.getSeatExtensionNo().v().equals("")){
			entity.seatExtensionNo = domain.getSeatExtensionNo().v();
		}
		
		if (domain.getPhoneMailAddress() != null && !domain.getPhoneMailAddress().v().equals("")){
			entity.phoneMailAddress = domain.getPhoneMailAddress().v();
		}
		
		if (domain.getCellPhoneNo() != null && !domain.getCellPhoneNo().v().equals("")){
			entity.cellPhoneNo = domain.getCellPhoneNo().v();
		}
	}

	@Override
	public Optional<EmployeeInfoContact> findByEmpId(String sId) {
		
		Optional<BsymtEmpInfoContact> empContact = this.queryProxy().find(new BpsmtPersonPk(sId), BsymtEmpInfoContact.class);
		if (empContact.isPresent()) {
			return Optional.of(toDomain(empContact.get()));
		} else {
			return Optional.empty();
		}
	}
	
	private static EmployeeInfoContact toDomain(BsymtEmpInfoContact entity) {
		

		EmployeeInfoContact domain = EmployeeInfoContact.createFromJavaType(
				entity.cid,
				entity.bsymtEmpInfoContactPK.sid, 
				entity.mailAdress,
				entity.seatDialIn,
				entity.seatExtensionNo, 
				entity.phoneMailAddress, 
				entity.cellPhoneNo);
		return domain;
	}

}
