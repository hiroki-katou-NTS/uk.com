/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.employment.affiliate;

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
import nts.uk.ctx.bs.employee.dom.employment.affiliate.AffEmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.affiliate.AffEmploymentHistoryRepository;
import nts.uk.ctx.bs.employee.infra.entity.employment.affiliate.KmnmtAffiliEmploymentHist;
import nts.uk.ctx.bs.employee.infra.entity.employment.affiliate.KmnmtAffiliEmploymentHistPK_;
import nts.uk.ctx.bs.employee.infra.entity.employment.affiliate.KmnmtAffiliEmploymentHist_;

/**
 * The Class JpaAffiliationEmploymentHistoryRepository.
 */
@Stateless
public class JpaAffEmploymentHistoryRepository extends JpaRepository
		implements AffEmploymentHistoryRepository {

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the employment history
	 */
	private AffEmploymentHistory toDomain(KmnmtAffiliEmploymentHist entity) {
		return new AffEmploymentHistory(new JpaAffEmploymentHistoryGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryRepository#searchEmployee(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<AffEmploymentHistory> searchEmployee(GeneralDate baseDate,
			List<String> employmentCodes) {
		
		// check not data input
		if (CollectionUtil.isEmpty(employmentCodes)) {
			return new ArrayList<>();
		}

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<KmnmtAffiliEmploymentHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliEmploymentHist.class);

		// root data
		Root<KmnmtAffiliEmploymentHist> root = cq.from(KmnmtAffiliEmploymentHist.class);

		// select root
		cq.select(root);
		
		// Split query.
		List<KmnmtAffiliEmploymentHist> resultList = new ArrayList<>();
		
		CollectionUtil.split(employmentCodes, 1000, (subList) -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// employment in data employment
			lstpredicateWhere
					.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
							.get(KmnmtAffiliEmploymentHistPK_.emptcd).in(subList)));

			// start date <= base date
			lstpredicateWhere.add(criteriaBuilder
					.lessThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
							.get(KmnmtAffiliEmploymentHistPK_.strD), baseDate));

			// endDate >= base date
			lstpredicateWhere.add(criteriaBuilder
					.greaterThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.endD), baseDate));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// create query
			TypedQuery<KmnmtAffiliEmploymentHist> query = em.createQuery(cq);
			resultList.addAll(query.getResultList());
		});
		

		// exclude select
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryRepository#searchEmployee(java.util.List,
	 * nts.arc.time.GeneralDate, java.util.List)
	 */
	@Override
	public List<AffEmploymentHistory> searchEmployee(List<String> employeeIds, GeneralDate baseDate,
			List<String> employmentCodes) {
		if (CollectionUtil.isEmpty(employeeIds) || CollectionUtil.isEmpty(employmentCodes)) {
			return new ArrayList<>();
		}
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<KmnmtAffiliEmploymentHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliEmploymentHist.class);

		// root data
		Root<KmnmtAffiliEmploymentHist> root = cq.from(KmnmtAffiliEmploymentHist.class);

		// select root
		cq.select(root);

		List<KmnmtAffiliEmploymentHist> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, employeeSubList -> {
			CollectionUtil.split(employmentCodes, 1000, employmentSubList -> {
				// add where
				List<Predicate> lstpredicateWhere = new ArrayList<>();

				// employment in data employment
				lstpredicateWhere
						.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
								.get(KmnmtAffiliEmploymentHistPK_.emptcd).in(employmentSubList)));

				// employee id in data employee id
				lstpredicateWhere
						.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
								.get(KmnmtAffiliEmploymentHistPK_.empId).in(employeeSubList)));

				// start date <= base date
				lstpredicateWhere.add(criteriaBuilder
						.lessThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
								.get(KmnmtAffiliEmploymentHistPK_.strD), baseDate));

				// endDate >= base date
				lstpredicateWhere.add(criteriaBuilder
						.greaterThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.endD), baseDate));

				// set where to SQL
				cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

				// create query
				TypedQuery<KmnmtAffiliEmploymentHist> query = em.createQuery(cq);
				resultList.addAll(query.getResultList());
			});
		});
		

		// exclude select
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.basic.dom.company.organization.employee.employment.AffEmploymentHistoryRepository
	 * #searchEmploymentOfSids(java.util.List, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<AffEmploymentHistory> searchEmploymentOfSids(List<String> employeeIds,
			GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<KmnmtAffiliEmploymentHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliEmploymentHist.class);

		// root data
		Root<KmnmtAffiliEmploymentHist> root = cq.from(KmnmtAffiliEmploymentHist.class);

		// select root
		cq.select(root);
		if (CollectionUtil.isEmpty(employeeIds)) {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			// start date <= base date
			lstpredicateWhere.add(criteriaBuilder
					.lessThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
							.get(KmnmtAffiliEmploymentHistPK_.strD), baseDate));
	
			// endDate >= base date
			lstpredicateWhere.add(criteriaBuilder
					.greaterThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.endD), baseDate));
	
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
	
			// create query
			TypedQuery<KmnmtAffiliEmploymentHist> query = em.createQuery(cq);
	
			// exclude select
			return query.getResultList().stream().map(category -> toDomain(category))
					.collect(Collectors.toList());
		}
		
		// Split employee ids.
		List<KmnmtAffiliEmploymentHist> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, 1000, subList -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();
			// start date <= base date
			lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
					root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK).get(KmnmtAffiliEmploymentHistPK_.strD),
					baseDate));

			// employee id in data employee id
			lstpredicateWhere.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
					.get(KmnmtAffiliEmploymentHistPK_.empId).in(subList)));

			// endDate >= base date
			lstpredicateWhere
					.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.endD), baseDate));

			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			// create query
			TypedQuery<KmnmtAffiliEmploymentHist> query = em.createQuery(cq);
			resultList.addAll(query.getResultList());
		});
		

		// exclude select
		return resultList.stream().map(category -> toDomain(category)).collect(Collectors.toList());
	}

}
