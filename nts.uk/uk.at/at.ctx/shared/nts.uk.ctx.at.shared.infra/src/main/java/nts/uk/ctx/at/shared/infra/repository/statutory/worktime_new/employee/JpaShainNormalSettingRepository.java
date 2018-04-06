/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSetPK;

/**
 * The Class JpaShainNormalSettingRepository.
 */
@Stateless
public class JpaShainNormalSettingRepository extends JpaRepository implements ShainNormalSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting)
	 */
	@Override
	public void add(ShainNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting)
	 */
	@Override
	public void update(ShainNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#find(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Optional<ShainNormalSetting> find(String cid, String empId, int year) {
		Optional<KshstShaNormalSet> optEntity = this.queryProxy().find(new KshstShaNormalSetPK(cid, empId, year), KshstShaNormalSet.class);

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
	 * @return the kshst sha normal set
	 */
	private KshstShaNormalSet toEntity(ShainNormalSetting domain) {
		KshstShaNormalSet entity = new KshstShaNormalSet();
		domain.saveToMemento(new JpaShainNormalSettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain normal setting
	 */
	private ShainNormalSetting toDomain(KshstShaNormalSet entity) {
		return new ShainNormalSetting(new JpaShainNormalSettingGetMemento(entity));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#delete(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstShaNormalSet.class, new KshstShaNormalSetPK(cid, empId, year));
	}
}
