/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.jobtitle;

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
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffiliationJobTitleHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.jobtile.AffiliationJobTitleHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.jobtitle.KmnmtAffiliJobTitleHist_;

/**
 * The Class JpaAffiliationJobTitleHistoryRepository.
 */
@Stateless
public class JpaAffiliationJobTitleHistoryRepository extends JpaRepository
		implements AffiliationJobTitleHistoryRepository {
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the job title history
	 */
	private AffiliationJobTitleHistory toDomain(KmnmtAffiliJobTitleHist entity){
		return new AffiliationJobTitleHistory(new JpaAffiliationJobTitleHistoryGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryRepository#searchJobTitleHistory(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<AffiliationJobTitleHistory> searchJobTitleHistory(GeneralDate baseDate,
			List<String> positionIds) {
		
		// check exist data
		if(CollectionUtil.isEmpty(positionIds)){
			return new ArrayList<>();
		}
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_JOB_TITLE_HIST (KmnmtJobTitleHist SQL)
		CriteriaQuery<KmnmtAffiliJobTitleHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliJobTitleHist.class);

		// root data
		Root<KmnmtAffiliJobTitleHist> root = cq.from(KmnmtAffiliJobTitleHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// position id in data position id
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.jobId).in(positionIds)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliJobTitleHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.jobtitle.history.
	 * JobTitleHistoryRepository#searchJobTitleHistory(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<AffiliationJobTitleHistory> searchJobTitleHistory(List<String> employeeIds,
			GeneralDate baseDate, List<String> positionIds) {
		// check exist data
		if (CollectionUtil.isEmpty(positionIds) || CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_JOB_TITLE_HIST (KmnmtJobTitleHist SQL)
		CriteriaQuery<KmnmtAffiliJobTitleHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliJobTitleHist.class);

		// root data
		Root<KmnmtAffiliJobTitleHist> root = cq.from(KmnmtAffiliJobTitleHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// position id in data position id
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.jobId).in(positionIds)));

		// employee id in data employee id
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliJobTitleHist_.kmnmtJobTitleHistPK)
						.get(KmnmtAffiliJobTitleHistPK_.empId).in(employeeIds)));

		// start date <= base date
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliJobTitleHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliJobTitleHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

}
