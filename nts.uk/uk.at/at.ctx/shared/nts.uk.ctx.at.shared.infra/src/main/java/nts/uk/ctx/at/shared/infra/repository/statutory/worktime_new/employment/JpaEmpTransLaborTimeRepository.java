/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

//import java.util.ArrayList;
//import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK_;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime_;

/**
 * The Class JpaEmpTransLaborTimeRepository.
 */
@Stateless
public class JpaEmpTransLaborTimeRepository extends JpaRepository implements EmpTransWorkTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void add(EmpTransLaborTime emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void update(EmpTransLaborTime emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void delete(String cid, String emplId) {
		commandProxy().remove(KshstEmpTransLabTime.class, new KshstEmpTransLabTimePK(cid, emplId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#findByCidAndEmplId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmpTransLaborTime> find(String cid, String emplId) {
		Optional<KshstEmpTransLabTime> optEntity = this.queryProxy().find(new KshstEmpTransLabTimePK(cid, emplId), KshstEmpTransLabTime.class);

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
	 * @return the kshst emp trans lab time
	 */
	private KshstEmpTransLabTime toEntity(EmpTransLaborTime domain) {
		KshstEmpTransLabTime entity = new KshstEmpTransLabTime();
		domain.saveToMemento(new JpaEmpTransLaborTimeSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the emp trans work time
	 */
	private EmpTransLaborTime toDomain(KshstEmpTransLabTime entity) {
		return new EmpTransLaborTime(new JpaEmpTransLaborTimeGetMemento(entity));
	}

}
