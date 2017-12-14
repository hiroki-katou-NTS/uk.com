/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondItem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondItem_;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond_;

/**
 * The Class JpaWorkingConditionItemRepository.
 */
@Stateless
public class JpaWorkingConditionItemRepository extends JpaRepository
		implements WorkingConditionItemRepository {

	/** The Constant FIRST_ITEM_INDEX. */
	private final static int FIRST_ITEM_INDEX = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository#
	 * findWorkingConditionItemByPersWorkCat(java.lang.String,
	 * nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkingConditionItem> findWorkingConditionItemByPersWorkCat(String employeeId,
			GeneralDate baseDate) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCondItem> cq = criteriaBuilder
				.createQuery(KshmtWorkingCondItem.class);

		// root data
		Root<KshmtWorkingCondItem> root = cq.from(KshmtWorkingCondItem.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtWorkingCondItem_.sid), employeeId));
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
				root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.strD),
				baseDate));
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
				root.get(KshmtWorkingCondItem_.kshmtWorkingCond).get(KshmtWorkingCond_.endD),
				baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KshmtWorkingCondItem> query = em.createQuery(cq);

		List<KshmtWorkingCondItem> result = query.getResultList();

		// exclude select
		return Optional.of(new WorkingConditionItem(
				new JpaWorkingConditionItemGetMemento(result.get(FIRST_ITEM_INDEX))));
	}

}
