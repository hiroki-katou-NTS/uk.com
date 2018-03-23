/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime_;

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
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmpTransLabTime> cq = cb.createQuery(KshstEmpTransLabTime.class);
		Root<KshstEmpTransLabTime> root = cq.from(KshstEmpTransLabTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstEmpTransLabTime_.kshstEmpTransLabTimePK).get(KshstEmpTransLabTimePK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstEmpTransLabTime_.kshstEmpTransLabTimePK).get(KshstEmpTransLabTimePK_.empCd), emplId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
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
		if (entity == null) {
			return null;
		}
		return new EmpTransLaborTime(new JpaEmpTransLaborTimeGetMemento(entity));
	}

}
