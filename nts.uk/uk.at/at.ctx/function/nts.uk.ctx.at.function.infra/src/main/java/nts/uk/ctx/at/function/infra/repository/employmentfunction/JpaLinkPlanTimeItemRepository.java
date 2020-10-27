/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.employmentfunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItem;
import nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemRepository;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnmtPlanTimeItem;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnmtPlanTimeItemPK_;
import nts.uk.ctx.at.function.infra.entity.employmentfunction.KfnmtPlanTimeItem_;

/**
 * The Class JpaLinkPlanTimeItemRepository.
 */
@Stateless
public class JpaLinkPlanTimeItemRepository extends JpaRepository implements LinkPlanTimeItemRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.employmentfunction.LinkPlanTimeItemRepository#findAll(java.lang.String)
	 */
	@Override
	public List<LinkPlanTimeItem> findAll(String cId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<KfnmtPlanTimeItem> query = builder.createQuery(KfnmtPlanTimeItem.class);
		Root<KfnmtPlanTimeItem> root = query.from(KfnmtPlanTimeItem.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(
				builder.equal(root.get(KfnmtPlanTimeItem_.kfnmtPlanTimeItemPK).get(KfnmtPlanTimeItemPK_.cid), cId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KfnmtPlanTimeItem> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		return result.stream().map(entity -> new LinkPlanTimeItem(new JpaLinkPlanTimeItemGetMemento(entity)))
				.collect(Collectors.toList());
	}
}
