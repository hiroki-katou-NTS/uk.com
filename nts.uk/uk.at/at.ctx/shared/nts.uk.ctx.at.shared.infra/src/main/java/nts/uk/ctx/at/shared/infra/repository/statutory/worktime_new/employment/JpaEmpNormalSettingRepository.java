/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSetPK;

/**
 * The Class JpaEmpNormalSettingRepository.
 */
@Stateless
public class JpaEmpNormalSettingRepository extends JpaRepository implements EmpNormalSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting)
	 */
	@Override
	public void add(EmpNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting)
	 */
	@Override
	public void update(EmpNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting)
	 */
	@Override
	public void delete(String cid, String emplCode, int year) {
		commandProxy().remove(KshstEmpNormalSet.class, new KshstEmpNormalSetPK(cid, emplCode, year));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#findByCidAndEmplCodeAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<EmpNormalSetting> find(String cid, String emplCode, int year) {
		Optional<KshstEmpNormalSet> optEntity = this.queryProxy().find(new KshstEmpNormalSetPK(cid, emplCode, year), KshstEmpNormalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst emp normal set
	 */
	private KshstEmpNormalSet toEntity(EmpNormalSetting domain) {
		KshstEmpNormalSet entity = new KshstEmpNormalSet();
		domain.saveToMemento(new JpaEmpNormalSettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entities
	 * @return the emp normal setting
	 */
	private EmpNormalSetting toDomain(KshstEmpNormalSet entity) {
		return new EmpNormalSetting(new JpaEmpNormalSettingGetMemento(entity));
	}
}
