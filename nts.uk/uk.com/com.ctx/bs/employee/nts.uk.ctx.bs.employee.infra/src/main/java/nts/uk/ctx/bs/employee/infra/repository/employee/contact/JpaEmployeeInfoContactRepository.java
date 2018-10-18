package nts.uk.ctx.bs.employee.infra.repository.employee.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employee.contact.EmployeeInfoContactRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContact;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContactPK;

@Stateless
public class JpaEmployeeInfoContactRepository extends JpaRepository implements EmployeeInfoContactRepository {

	private static final String GET_BY_LIST = "SELECT ec FROM BsymtEmpInfoContact ec WHERE ec.bsymtEmpInfoContactPK.sid IN :employeeIds";

	@Override
	public void add(EmployeeInfoContact domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmployeeInfoContact domain) {

		BsymtEmpInfoContactPK key = new BsymtEmpInfoContactPK(domain.getSid());

		Optional<BsymtEmpInfoContact> entity = this.queryProxy().find(key, BsymtEmpInfoContact.class);

		if (entity.isPresent()) {
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
	 * 
	 * @param domain
	 * @return
	 */
	private BsymtEmpInfoContact toEntity(EmployeeInfoContact domain) {
		BsymtEmpInfoContact entity = new BsymtEmpInfoContact();
		entity.bsymtEmpInfoContactPK = new BsymtEmpInfoContactPK(domain.getSid());
		entity.cid = domain.getCId();
		entity.cellPhoneNo = domain.getCellPhoneNo().isPresent() ? domain.getCellPhoneNo().get().v() : null;
		entity.mailAdress = domain.getMailAddress().isPresent() ? domain.getMailAddress().get().v() : null;
		entity.phoneMailAddress = domain.getPhoneMailAddress().isPresent() ? domain.getPhoneMailAddress().get().v()
				: null;
		entity.seatDialIn = domain.getSeatDialIn().isPresent() ? domain.getSeatDialIn().get().v() : null;
		entity.seatExtensionNo = domain.getSeatExtensionNo().isPresent() ? domain.getSeatExtensionNo().get().v() : null;
		return entity;
	}

	/**
	 * Update entity
	 * 
	 * @param domain
	 * @param entity
	 */
	private void updateEntity(EmployeeInfoContact domain, BsymtEmpInfoContact entity) {
		entity.cellPhoneNo = domain.getCellPhoneNo().isPresent() ? domain.getCellPhoneNo().get().v() : null;
		entity.mailAdress = domain.getMailAddress().isPresent() ? domain.getMailAddress().get().v() : null;
		entity.phoneMailAddress = domain.getPhoneMailAddress().isPresent() ? domain.getPhoneMailAddress().get().v()
				: null;
		entity.seatDialIn = domain.getSeatDialIn().isPresent() ? domain.getSeatDialIn().get().v() : null;
		entity.seatExtensionNo = domain.getSeatExtensionNo().isPresent() ? domain.getSeatExtensionNo().get().v() : null;
	}

	@Override
	public Optional<EmployeeInfoContact> findByEmpId(String sId) {

		Optional<BsymtEmpInfoContact> empContact = this.queryProxy().find(new BsymtEmpInfoContactPK(sId),
				BsymtEmpInfoContact.class);
		if (empContact.isPresent()) {
			return Optional.of(toDomain(empContact.get()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public List<EmployeeInfoContact> findByListEmpId(List<String> employeeIds) {
		if (employeeIds.isEmpty()) {
			return new ArrayList<>();
		}
		
		List<BsymtEmpInfoContact> entities = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(GET_BY_LIST, BsymtEmpInfoContact.class)
								.setParameter("employeeIds", subList)
								.getList());
		});
		return entities.stream().map(ent -> toDomain(ent)).collect(Collectors.toList());
	}

	private static EmployeeInfoContact toDomain(BsymtEmpInfoContact entity) {

		EmployeeInfoContact domain = EmployeeInfoContact.createFromJavaType(entity.cid,
				entity.bsymtEmpInfoContactPK.sid, entity.mailAdress, entity.seatDialIn, entity.seatExtensionNo,
				entity.phoneMailAddress, entity.cellPhoneNo);
		return domain;
	}

}
