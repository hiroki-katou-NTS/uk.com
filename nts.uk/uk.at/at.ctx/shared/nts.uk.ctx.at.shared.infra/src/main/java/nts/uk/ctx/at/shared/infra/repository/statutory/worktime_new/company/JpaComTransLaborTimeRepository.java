/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime_;

/**
 * The Class JpaComTransLaborTimeRepository.
 */
@Stateless
public class JpaComTransLaborTimeRepository extends JpaRepository implements ComTransLaborTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime)
	 */
	@Override
	public void create(ComTransLaborTime setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime)
	 */
	@Override
	public void update(ComTransLaborTime setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		commandProxy().remove(KshstComTransLabTime.class, companyId);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComTransLaborTime> find(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstComTransLabTime> cq = cb.createQuery(KshstComTransLabTime.class);
		Root<KshstComTransLabTime> root = cq.from(KshstComTransLabTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstComTransLabTime_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst com trans lab time
	 */
	private KshstComTransLabTime toEntity(ComTransLaborTime domain) {
		JpaComTransLaborTimeSetMemento memento = new JpaComTransLaborTimeSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the com trans labor time
	 */
	private ComTransLaborTime toDomain(List<KshstComTransLabTime> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new ComTransLaborTime(new JpaComTransLaborTimeGetMemento(entities.get(0)));
	}
}
