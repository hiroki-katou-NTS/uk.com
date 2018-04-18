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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComRegLaborTime_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;

/**
 * The Class JpaComRegularLaborTimeRepository.
 */
@Stateless
public class JpaComRegularLaborTimeRepository extends JpaRepository
		implements ComRegularLaborTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#create(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime)
	 */
	@Override
	public void create(ComRegularLaborTime setting) {
		KshstComRegLaborTime entity = new KshstComRegLaborTime();
		setting.saveToMemento(new JpaComRegularLaborTimeSetMemento(entity));
		commandProxy().insert(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime)
	 */
	@Override
	public void update(ComRegularLaborTime setting) {
		KshstComRegLaborTime entity = this.queryProxy().find(setting.getCompanyId().v(), KshstComRegLaborTime.class).get();
		setting.saveToMemento(new JpaComRegularLaborTimeSetMemento(entity));
		commandProxy().update(entity);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId) {
		commandProxy().remove(KshstComTransLabTime.class, companyId);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository#find(java.lang.String)
	 */
	@Override
	public Optional<ComRegularLaborTime> find(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstComRegLaborTime> cq = cb.createQuery(KshstComRegLaborTime.class);
		Root<KshstComRegLaborTime> root = cq.from(KshstComRegLaborTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstComRegLaborTime_.cid), companyId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the com regular labor time
	 */
	private ComRegularLaborTime toDomain(List<KshstComRegLaborTime> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new ComRegularLaborTime(new JpaComRegularLaborTimeGetMemento(entities.get(0)));
	}
}
