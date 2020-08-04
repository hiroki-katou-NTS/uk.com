/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTimePK;

/**
 * The Class JpaShainTransLaborTimeRepository.
 */
@Stateless
public class JpaShainTransLaborTimeRepository extends JpaRepository implements ShainTransLaborTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void add(ShainTransLaborTime emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void update(ShainTransLaborTime emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void delete(String cid, String empId) {
		commandProxy().remove(KshstShaTransLabTime.class, new KshstShaTransLabTimePK(cid, empId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShainTransLaborTime> find(String cid, String empId) {
		Optional<KshstShaTransLabTime> optEntity = this.queryProxy().find(new KshstShaTransLabTimePK(cid, empId), KshstShaTransLabTime.class);

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
	 * @return the kshst sha trans lab time
	 */
	private KshstShaTransLabTime toEntity(ShainTransLaborTime domain) {
		KshstShaTransLabTime entity = new KshstShaTransLabTime();
		domain.saveToMemento(new JpaShainTransLaborTimeSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain spe defor labor time
	 */
	private ShainTransLaborTime toDomain(KshstShaTransLabTime entity) {
		return new ShainTransLaborTime(new JpaShainTransLaborTimeGetMemento(entity));
	}

}
