package nts.uk.ctx.office.infra.repository.equipment;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.office.dom.equipment.ClassificationMaster.EquipmentClassification;
import nts.uk.ctx.office.dom.equipment.ClassificationMaster.EquipmentClassificationCode;
import nts.uk.ctx.office.dom.equipment.ClassificationMaster.EquipmentClassificationName;
import nts.uk.ctx.office.dom.equipment.ClassificationMaster.EquipmentClassificationRepository;
import nts.uk.ctx.office.infra.entity.equipment.OfidtEquipmentCls;
import nts.uk.ctx.office.infra.entity.equipment.OfidtEquipmentClsPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaEquipmentClassificationRepo extends JpaRepository implements EquipmentClassificationRepository {

	private static final String FIND_ALL = "SELECT e FROM OfidtEquipmentCls e WHERE e.pk.contractCd = :contractCd";
	private static final String FIND_BY_CLS_CD = "SELECT e FROM OfidtEquipmentCls e WHERE e.pk.contractCd = :contractCd AND e.pk.code = :code";
	
	@Override
	public void insert(EquipmentClassification domain) {
		OfidtEquipmentCls entity = this.toEntity(domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(EquipmentClassification domain) {
		OfidtEquipmentCls entity = this.toEntity(domain);
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String contractCd, String equipmentClsCd) {
		OfidtEquipmentClsPK pk = new OfidtEquipmentClsPK(contractCd, equipmentClsCd);
		this.commandProxy().remove(OfidtEquipmentCls.class, pk);
	}

	@Override
	public List<EquipmentClassification> getAll(String contractCd) {
		return this.queryProxy()
			.query(FIND_ALL, OfidtEquipmentCls.class)
			.setParameter("contractCd", contractCd)
			.getList(e -> toDomain(e));
	}

	@Override
	public List<EquipmentClassification> getByClassificationCode(String contractCd, String clsCd) {
		return this.queryProxy()
				.query(FIND_BY_CLS_CD, OfidtEquipmentCls.class)
				.setParameter("contractCd", contractCd)
				.setParameter("code", clsCd)
				.getList(e -> toDomain(e));
	}
	
	private EquipmentClassification toDomain(OfidtEquipmentCls entity) {
		return new EquipmentClassification(
			new EquipmentClassificationCode(entity.getPk().getCode()),
			new EquipmentClassificationName(entity.getName())
		);
	}
	
	private OfidtEquipmentCls toEntity(EquipmentClassification domain) {
		OfidtEquipmentCls entity = new OfidtEquipmentCls();
		OfidtEquipmentClsPK pk = new OfidtEquipmentClsPK();
		pk.setContractCd(AppContexts.user().contractCode());
		pk.setCode(domain.getCode().v());
		entity.setPk(pk);
		entity.setName(domain.getName().v());
		return entity;
	}

}
