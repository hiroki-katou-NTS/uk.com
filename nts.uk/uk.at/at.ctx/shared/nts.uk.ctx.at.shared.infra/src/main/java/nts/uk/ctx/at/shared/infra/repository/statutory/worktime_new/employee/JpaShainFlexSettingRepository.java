/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSetPK;

/**
 * The Class JpaShainFlexSettingRepository.
 */
@Stateless
public class JpaShainFlexSettingRepository extends JpaRepository implements ShainFlexSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting)
	 */
	@Override
	public void add(ShainFlexSetting setting) {
		KshstShaFlexSet entity = new KshstShaFlexSet();
		setting.saveToMemento(new JpaShainFlexSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting)
	 */
	@Override
	public void update(ShainFlexSetting setting) {
		KshstShaFlexSet entity = this
				.queryProxy().find(
						new KshstShaFlexSetPK(setting.getCompanyId().v(),
								setting.getEmployeeId().v(), setting.getYear().v()),
						KshstShaFlexSet.class)
				.get();
		setting.saveToMemento(new JpaShainFlexSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		this.commandProxy().remove(KshstShaFlexSet.class, new KshstShaFlexSetPK(cid, empId, year));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#find(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<ShainFlexSetting> find(String cid, String empId, int year) {
		Optional<KshstShaFlexSet> optEntity = this.queryProxy().find(new KshstShaFlexSetPK(cid, empId, year), KshstShaFlexSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the shain flex setting
	 */
	private ShainFlexSetting toDomain(KshstShaFlexSet entities) {
		return new ShainFlexSetting(new JpaShainFlexSettingGetMemento(entities));
	}

}
