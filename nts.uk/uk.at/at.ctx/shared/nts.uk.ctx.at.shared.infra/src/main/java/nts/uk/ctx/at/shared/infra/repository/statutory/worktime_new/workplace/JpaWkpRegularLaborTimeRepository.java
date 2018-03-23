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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;

/**
 * The Class JpaWkpRegularLaborTimeRepository.
 */
@Stateless
public class JpaWkpRegularLaborTimeRepository extends JpaRepository
		implements WkpRegularLaborTimeRepository {

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpRegularLaborTime)
	 */
	@Override
	public void create(WkpRegularLaborTime setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpRegularLaborTime)
	 */
	@Override
	public void update(WkpRegularLaborTime setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		commandProxy().remove(KshstWkpTransLabTime.class, companyId);
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeRepository#find(java.lang.String)
	 */
	@Override
	public Optional<WkpRegularLaborTime> find(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpRegLaborTime> cq = cb.createQuery(KshstWkpRegLaborTime.class);
		Root<KshstWkpRegLaborTime> root = cq.from(KshstWkpRegLaborTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.insert(cb.equal(root.get(KshstWkpRegLaborTime_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kshst com reg labor time
	 */
	private KshstWkpRegLaborTime toEntity(WkpRegularLaborTime domain) {
		JpaWkpRegularLaborTimeSetMemento memento = new JpaWkpRegularLaborTimeSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the com regular labor time
	 */
	private WkpRegularLaborTime toDomain(List<KshstWkpRegLaborTime> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new WkpRegularLaborTime(new JpaWkpRegularLaborTimeGetMemento(entities.get(0)));
	}
}
