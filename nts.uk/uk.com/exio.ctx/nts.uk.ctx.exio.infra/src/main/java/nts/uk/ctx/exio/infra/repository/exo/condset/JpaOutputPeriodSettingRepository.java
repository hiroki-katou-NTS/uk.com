package nts.uk.ctx.exio.infra.repository.exo.condset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSetting;
import nts.uk.ctx.exio.dom.exo.condset.OutputPeriodSettingRepository;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtOutputPeriodSet;
import nts.uk.ctx.exio.infra.entity.exi.condset.OiomtOutputPeriodSetPk;

@Stateless
public class JpaOutputPeriodSettingRepository extends JpaRepository implements OutputPeriodSettingRepository {
	// Select all
	private static final String QUERY_SELECT_ALL = "SELECT f FROM OiomtOutputPeriodSet f";
	// Select one
	private static final String QUERY_SELECT_BY_ID = QUERY_SELECT_ALL
			+ " WHERE f.pk.cId = :cId AND f.pk.conditionSetCd = :conditionSetCd";
	
	@Override
	public Optional<OutputPeriodSetting> findById(String cid, String conditionSetCd) {
		return this.queryProxy()
				.query(QUERY_SELECT_BY_ID, OiomtOutputPeriodSet.class)
				.setParameter("cId", cid)
				.setParameter("conditionSetCd", conditionSetCd)
				.getSingle(OutputPeriodSetting::createFromMemento);
	}

	@Override
	public void add(OutputPeriodSetting domain) {
		// Convert data to entity
		OiomtOutputPeriodSet entity = JpaOutputPeriodSettingRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(String cid, String conditionSetCd, OutputPeriodSetting domain) {
		// Convert data to entity
		OiomtOutputPeriodSet entity = JpaOutputPeriodSettingRepository.toEntity(domain);
		OiomtOutputPeriodSetPk pk = OiomtOutputPeriodSetPk.builder()
				.cId(cid)
				.conditionSetCd(conditionSetCd)
				.build();
		OiomtOutputPeriodSet oldEntity = this.queryProxy().find(pk, OiomtOutputPeriodSet.class).get();
		oldEntity.setPeriodSetting(entity.getPeriodSetting());
		oldEntity.setClosureDayAtr(entity.getClosureDayAtr());
		oldEntity.setBaseDateClassification(entity.getBaseDateClassification());
		oldEntity.setBaseDateSpecify(entity.getBaseDateSpecify());
		oldEntity.setStartDateClassification(entity.getStartDateClassification());
		oldEntity.setStartDateSpecify(entity.getStartDateSpecify());
		oldEntity.setStartDateAdjustment(entity.getStartDateAdjustment());
		oldEntity.setEndDateClassification(entity.getEndDateClassification());
		oldEntity.setEndDateSpecify(entity.getEndDateSpecify());
		oldEntity.setEndDateAdjustment(entity.getEndDateAdjustment());
		// Update entity
		this.commandProxy().update(oldEntity);
	}
	
	private static OiomtOutputPeriodSet toEntity(OutputPeriodSetting domain) {
		OiomtOutputPeriodSet entity = new OiomtOutputPeriodSet();
		domain.setMemento(entity);
		return entity;
	}

}
