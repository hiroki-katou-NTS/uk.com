/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.classification;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffiliationClassificationHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.classification.AffiClassHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.classification.KmnmtAffiliClassificationHist_;

/**
 * The Class JpaAffiliationClassificationHistoryRepository.
 */
@Stateless
public class JpaAffiliationClassificationHistoryRepository extends JpaRepository
		implements AffiClassHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryRepository#searchClassification(nts.arc.time.
	 * GeneralDate, java.util.List)
	 */
	@Override
	public List<AffiliationClassificationHistory> searchClassification(GeneralDate baseDate,
			List<String> classificationCodes) {
		
		if(CollectionUtil.isEmpty(classificationCodes)){
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<KmnmtAffiliClassificationHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliClassificationHist.class);

		// root data
		Root<KmnmtAffiliClassificationHist> root = cq.from(KmnmtAffiliClassificationHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// classification in data classification
		lstpredicateWhere.add(
				criteriaBuilder.and(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.clscd).in(classificationCodes)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliClassificationHist> query = em.createQuery(cq);

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
	private AffiliationClassificationHistory toDomain(KmnmtAffiliClassificationHist entity) {
		return new AffiliationClassificationHistory(new JpaAffiliationClassificationHistoryGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.classification.history.
	 * ClassificationHistoryRepository#searchClassification(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<AffiliationClassificationHistory> searchClassification(List<String> employeeIds,
			GeneralDate baseDate, List<String> classificationCodes) {

		// check not data 
		if(CollectionUtil.isEmpty(classificationCodes) || CollectionUtil.isEmpty(employeeIds)){
			return new ArrayList<>();
		}
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_CLASSIFICATION_HIST (KmnmtClassificationHist SQL)
		CriteriaQuery<KmnmtAffiliClassificationHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliClassificationHist.class);

		// root data
		Root<KmnmtAffiliClassificationHist> root = cq.from(KmnmtAffiliClassificationHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// employee id in data employee id
		lstpredicateWhere.add(
				criteriaBuilder.and(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.empId).in(employeeIds)));
		
		// classification in data classification
		lstpredicateWhere.add(
				criteriaBuilder.and(root.get(KmnmtAffiliClassificationHist_.kmnmtClassificationHistPK)
						.get(KmnmtAffiliClassificationHistPK_.clscd).in(classificationCodes)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliClassificationHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliClassificationHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

}
