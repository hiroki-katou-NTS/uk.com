/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime_;

/**
 * The Class JpaWkpTransLaborTimeRepository.
 */
@Stateless
public class JpaWkpTransLaborTimeRepository extends JpaRepository implements WkpDeforLaborWorkTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTime)
	 */
	@Override
	public void add(WkpDeforLaborWorkTime emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTime)
	 */
	@Override
	public void update(WkpDeforLaborWorkTime emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTime)
	 */
	@Override
	public void delete(String cid, String wkpId) {
		commandProxy().remove(KshstWkpTransLabTime.class, new KshstWkpTransLabTimePK(cid, wkpId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpSpeDeforLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpDeforLaborWorkTime> find(String cid, String wkpId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpTransLabTime> cq = cb.createQuery(KshstWkpTransLabTime.class);
		Root<KshstWkpTransLabTime> root = cq.from(KshstWkpTransLabTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstWkpTransLabTime_.kshstWkpTransLabTimePK).get(KshstWkpTransLabTimePK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstWkpTransLabTime_.kshstWkpTransLabTimePK).get(KshstWkpTransLabTimePK_.wkpId), wkpId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha trans lab time
	 */
	private KshstWkpTransLabTime toEntity(WkpDeforLaborWorkTime domain) {
		JpaWkpTransLaborTimeSetMemento memento = new JpaWkpTransLaborTimeSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain spe defor labor time
	 */
	private WkpDeforLaborWorkTime toDomain(KshstWkpTransLabTime entity) {
		if (entity == null) {
			return null;
		}
		return new WkpDeforLaborWorkTime(new JpaWkpTransLaborTimeGetMemento(entity));
	}

}
