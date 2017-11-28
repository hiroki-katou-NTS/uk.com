/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.predset;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetRepository;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtspWorkTimeSetPKNew_;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtstWorkTimeSetNew;
import nts.uk.ctx.at.shared.infra.entity.predset.KwtstWorkTimeSetNew_;

@Stateless
public class JpaPredetemineTimeSetRepository extends JpaRepository implements PredetemineTimeSetRepository {

	@Override
	public PredetemineTimeSet findByCode(String companyId, String siftCD) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KwtstWorkTimeSetNew> cq = criteriaBuilder.createQuery(KwtstWorkTimeSetNew.class);
		Root<KwtstWorkTimeSetNew> root = cq.from(KwtstWorkTimeSetNew.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KwtstWorkTimeSetNew_.kwtspWorkTimeSetPK).get(KwtspWorkTimeSetPKNew_.companyId), companyId));

		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KwtstWorkTimeSetNew_.kwtspWorkTimeSetPK).get(KwtspWorkTimeSetPKNew_.siftCD), siftCD));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		KwtstWorkTimeSetNew kwtstWorkTimeSet = em.createQuery(cq).getSingleResult();

		// check empty
		if (kwtstWorkTimeSet == null) {
			return null;
		}
			return new PredetemineTimeSet(new JpaPredetemineTimeGetMemento(kwtstWorkTimeSet));
	}

}
