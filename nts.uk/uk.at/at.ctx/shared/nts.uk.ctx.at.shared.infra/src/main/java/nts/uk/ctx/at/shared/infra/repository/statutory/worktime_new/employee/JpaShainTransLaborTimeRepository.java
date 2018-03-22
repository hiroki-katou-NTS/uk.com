/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTime_;

/**
 * The Class JpaShainTransLaborTimeRepository.
 */
@Stateless
public class JpaShainTransLaborTimeRepository extends JpaRepository implements ShainSpeDeforLaborTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void add(ShainSpeDeforLaborTime emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void update(ShainSpeDeforLaborTime emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void delete(ShainSpeDeforLaborTime emplDeforLaborWorkingHour) {
		commandProxy().remove(emplDeforLaborWorkingHour);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShainSpeDeforLaborTime> find(String cid, String empId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaTransLabTime> cq = cb.createQuery(KshstShaTransLabTime.class);
		Root<KshstShaTransLabTime> root = cq.from(KshstShaTransLabTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstShaTransLabTime_.kshstShaTransLabTimePK).get(KshstShaTransLabTimePK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstShaTransLabTime_.kshstShaTransLabTimePK).get(KshstShaTransLabTimePK_.sid), empId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha trans lab time
	 */
	private KshstShaTransLabTime toEntity(ShainSpeDeforLaborTime domain) {
		JpaShainTransLaborTimeSetMemento memento = new JpaShainTransLaborTimeSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain spe defor labor time
	 */
	private ShainSpeDeforLaborTime toDomain(KshstShaTransLabTime entity) {
		if (entity == null) {
			return null;
		}
		return new ShainSpeDeforLaborTime(new JpaShainTransLaborTimeGetMemento(entity));
	}

}
