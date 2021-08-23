package nts.uk.ctx.office.infra.repository.equipment;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.office.dom.equipment.EquipmentInformation;
import nts.uk.ctx.office.dom.equipment.EquipmentInformationRepository;
import nts.uk.ctx.office.infra.entity.equipment.OfidtEquipment;
import nts.uk.ctx.office.infra.entity.equipment.OfidtEquipmentPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EquipmentInformationRepositoryImpl extends JpaRepository
												implements EquipmentInformationRepository {
	
	private static final String FIND_BY_CID = "SELECT t FROM OfidtEquipment t "
			+ "WHERE t.pk.cid = :cid";

	@Override
	public void insert(EquipmentInformation domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	@Override
	public void update(EquipmentInformation domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	@Override
	public void delete(String cid, String code) {
		this.commandProxy().remove(OfidtEquipment.class, new OfidtEquipmentPK(cid, code));
	}

	@Override
	public List<EquipmentInformation> findByCid(String cid) {
		return this.queryProxy().query(FIND_BY_CID, OfidtEquipment.class)
				.getList(EquipmentInformation::createFromMemento);
	}

	@Override
	public Optional<EquipmentInformation> findByPk(String cid, String code) {
		return this.queryProxy().find(new OfidtEquipmentPK(cid, code), OfidtEquipment.class)
				.map(EquipmentInformation::createFromMemento);
	}
	
	public OfidtEquipment toEntity(EquipmentInformation domain) {
		OfidtEquipment entity = new OfidtEquipment();
		domain.setMemento(entity);
		entity.getPk().setCid(AppContexts.user().companyId());
		return entity;
	}

}
