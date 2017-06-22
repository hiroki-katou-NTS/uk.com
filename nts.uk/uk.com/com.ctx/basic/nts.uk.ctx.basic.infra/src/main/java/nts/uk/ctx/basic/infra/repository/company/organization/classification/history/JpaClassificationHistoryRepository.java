/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.classification.history;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistory;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.history.KmnmtClassificationHist;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.history.KmnmtClassificationHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.history.KmnmtClassificationHist_;

/**
 * The Class JpaClassificationHistoryRepository.
 */
@Stateless
public class JpaClassificationHistoryRepository extends JpaRepository
		implements ClassificationHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryRepository#searchClassification(nts.arc.time.
	 * GeneralDate, java.util.List)
	 */
	@Override
	public List<ClassificationHistory> searchClassification(GeneralDate baseDate,
			List<String> classificationCodes) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<KmnmtClassificationHist> cq = criteriaBuilder
				.createQuery(KmnmtClassificationHist.class);

		// root data
		Root<KmnmtClassificationHist> root = cq.from(KmnmtClassificationHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// classification in data classification
		lstpredicateWhere.add(
				criteriaBuilder.and(root.get(KmnmtClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtClassificationHistPK_.clscd).in(classificationCodes)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtClassificationHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtClassificationHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtClassificationHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the classification history
	 */
	private ClassificationHistory toDomain(KmnmtClassificationHist entity) {
		return new ClassificationHistory(new JpaClassificationHistoryGetMemento(entity));
	}

}
