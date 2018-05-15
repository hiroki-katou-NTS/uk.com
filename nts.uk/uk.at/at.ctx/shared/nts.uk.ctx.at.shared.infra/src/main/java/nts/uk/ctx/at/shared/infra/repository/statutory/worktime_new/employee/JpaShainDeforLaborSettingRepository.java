/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSetPK;

/**
 * The Class JpaShainDeforLaborSettingRepository.
 */
@Stateless
public class JpaShainDeforLaborSettingRepository extends JpaRepository implements ShainDeforLaborSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#findEmpDeforLaborSettingByCidAndEmpIdAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<ShainDeforLaborSetting> find(String cid, String empId, int year) {
		
		Optional<KshstShaDeforLarSet> optEntity = this.queryProxy().find(new KshstShaDeforLarSetPK(cid, empId, year), KshstShaDeforLarSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting)
	 */
	@Override
	public void add(ShainDeforLaborSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting)
	 */
	@Override
	public void update(ShainDeforLaborSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstShaDeforLarSet.class, new KshstShaDeforLarSetPK(cid, empId, year));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain defor labor setting
	 */
	private ShainDeforLaborSetting toDomain(KshstShaDeforLarSet entity) {
		return new ShainDeforLaborSetting(new JpaShainDeforLaborSettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha defor lar set
	 */
	private KshstShaDeforLarSet toEntity(ShainDeforLaborSetting domain) {
		KshstShaDeforLarSet entity = new KshstShaDeforLarSet();
		domain.saveToMemento(new JpaShainDeforLaborSettingSetMemento(entity));
		return entity;
	}

}
