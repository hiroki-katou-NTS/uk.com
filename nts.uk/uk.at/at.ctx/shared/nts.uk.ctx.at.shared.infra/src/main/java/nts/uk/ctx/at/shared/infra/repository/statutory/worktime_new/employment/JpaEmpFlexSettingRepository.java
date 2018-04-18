/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSetPK;

/**
 * The Class JpaEmpFlexSettingRepository.
 */
@Stateless
public class JpaEmpFlexSettingRepository extends JpaRepository implements EmpFlexSettingRepository {
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting)
	 */
	@Override
	public void add(EmpFlexSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting)
	 */
	@Override
	public void update(EmpFlexSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting)
	 */
	@Override
	public void delete(String cid, String emplCode, int year) {
		commandProxy().remove(KshstEmpFlexSet.class, new KshstEmpFlexSetPK(cid, emplCode, year));
	}

	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#findByCidAndEmplCodeAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<EmpFlexSetting> find(String cid, String emplCode, int year) {
		Optional<KshstEmpFlexSet> optEntity = this.queryProxy().find(new KshstEmpFlexSetPK(cid, emplCode, year), KshstEmpFlexSet.class);

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
	 * @return the kshst emp flex set
	 */
	private KshstEmpFlexSet toEntity(EmpFlexSetting domain) {
		KshstEmpFlexSet entity = new KshstEmpFlexSet();
		domain.saveToMemento(new JpaEmpFlexSettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entities
	 * @return the emp flex setting
	 */
	private EmpFlexSetting toDomain(KshstEmpFlexSet entity) {
		return new EmpFlexSetting(new JpaEmpFlexSettingGetMemento(entity));
	}

}
