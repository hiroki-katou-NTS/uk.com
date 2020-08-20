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
				.getSingle(OiomtOutputPeriodSet::toDomain);
	}

	@Override
	public void add(OutputPeriodSetting domain) {
		// Convert data to entity
		OiomtOutputPeriodSet entity = JpaOutputPeriodSettingRepository.toEntity(domain);
		// Insert entity
		this.commandProxy().insert(entity);
	}

	@Override
	public void update(OutputPeriodSetting domain) {
		// Convert data to entity
		OiomtOutputPeriodSet entity = JpaOutputPeriodSettingRepository.toEntity(domain);
		OiomtOutputPeriodSet oldEntity = this.queryProxy().find(entity.getPk(), OiomtOutputPeriodSet.class).get();
		oldEntity.setPeriodSet(entity.getPeriodSet());
		oldEntity.setClosuredayAtr(entity.getClosuredayAtr());
		oldEntity.setStartDateCdAtr(entity.getStartDateCdAtr());
		oldEntity.setEndDateCdAtr(entity.getEndDateCdAtr());
		oldEntity.setBaseDateAtr(entity.getBaseDateAtr());
		oldEntity.setStartDateAdjust(entity.getStartDateAdjust());
		oldEntity.setEndDateAdjust(entity.getEndDateAdjust());
		oldEntity.setSpecifyStartDate(entity.getSpecifyStartDate());
		oldEntity.setSpecifyEndDate(entity.getSpecifyEndDate());
		oldEntity.setSpecifyBaseDate(entity.getSpecifyBaseDate());
		// Update entity
		this.commandProxy().update(oldEntity);
	}
	
	private static OiomtOutputPeriodSet toEntity(OutputPeriodSetting domain) {
		OiomtOutputPeriodSetPk pk = OiomtOutputPeriodSetPk.builder()
				.cId(domain.getCid())
				.conditionSetCd(domain.getConditionSetCode().v())
				.build();
		return OiomtOutputPeriodSet.builder()
				.pk(pk)
				.periodSet(domain.getPeriodSetting().value)
				.baseDateAtr(domain.getBaseDateClassification()
						.map(v -> v.value)
						.orElse(null))
				.specifyBaseDate(domain.getBaseDateSpecify().orElse(null))
				.startDateCdAtr(domain.getStartDateClassification()
						.map(v -> v.value)
						.orElse(null))
				.specifyStartDate(domain.getStartDateSpecify().orElse(null))
				.startDateAdjust(domain.getStartDateAdjustment()
						.map(v -> v.v())
						.orElse(null))
				.endDateCdAtr(domain.getEndDateClassification()
						.map(v -> v.value)
						.orElse(null))
				.specifyEndDate(domain.getEndDateSpecify().orElse(null))
				.endDateAdjust(domain.getEndDateAdjustment()
						.map(v -> v.v())
						.orElse(null))
				.closuredayAtr(domain.getDeadlineClassification().orElse(null))
				.build();
	}

}
