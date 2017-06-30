/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.employment;

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
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffiliationEmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.employment.AffiliationEmploymentHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.employment.KmnmtAffiliEmploymentHist_;

/**
 * The Class JpaAffiliationEmploymentHistoryRepository.
 */
@Stateless
public class JpaAffiliationEmploymentHistoryRepository extends JpaRepository
		implements AffiliationEmploymentHistoryRepository {

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employment history
	 */
	private AffiliationEmploymentHistory toDomain(KmnmtAffiliEmploymentHist entity){
		return new AffiliationEmploymentHistory(new JpaAffiliationEmploymentHistoryGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryRepository#searchEmployee(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<AffiliationEmploymentHistory> searchEmployee(GeneralDate baseDate,
			List<String> employmentCodes) {
		
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

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// check not data input
		if(CollectionUtil.isEmpty(employmentCodes)){
			return new ArrayList<>();
		}
		
		// employment in data employment
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
						.get(KmnmtAffiliEmploymentHistPK_.emptcd).in(employmentCodes)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(
				criteriaBuilder.greaterThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliEmploymentHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
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
	public List<AffiliationEmploymentHistory> searchEmployee(List<String> employeeIds, GeneralDate baseDate,
			List<String> employmentCodes) {
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

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		if(CollectionUtil.isEmpty( employeeIds) || CollectionUtil.isEmpty(employmentCodes)){
			return new ArrayList<>();
		}
		
		// employment in data employment
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
						.get(KmnmtAffiliEmploymentHistPK_.emptcd).in(employmentCodes)));
		
		// employee id in data employee id
		lstpredicateWhere
		.add(criteriaBuilder.and(root.get(KmnmtAffiliEmploymentHist_.kmnmtEmploymentHistPK)
				.get(KmnmtAffiliEmploymentHistPK_.empId).in(employeeIds)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(
				criteriaBuilder.greaterThanOrEqualTo(root.get(KmnmtAffiliEmploymentHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliEmploymentHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

}
