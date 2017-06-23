/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employment.history;

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
import nts.uk.ctx.basic.dom.company.organization.employment.history.EmploymentHistory;
import nts.uk.ctx.basic.dom.company.organization.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employment.history.KmnmtEmploymentHist_;

/**
 * The Class JpaEmploymentHistoryRepository.
 */
@Stateless
public class JpaEmploymentHistoryRepository extends JpaRepository implements EmploymentHistoryRepository{

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employment history
	 */
	private EmploymentHistory toDomain(KmnmtEmploymentHist entity){
		return new EmploymentHistory(new JpaEmploymentHistoryGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.employment.history.
	 * EmploymentHistoryRepository#searchEmployee(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<EmploymentHistory> searchEmployee(GeneralDate baseDate,
			List<String> employmentCodes) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<KmnmtEmploymentHist> cq = criteriaBuilder
				.createQuery(KmnmtEmploymentHist.class);

		// root data
		Root<KmnmtEmploymentHist> root = cq.from(KmnmtEmploymentHist.class);

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
				.add(criteriaBuilder.and(root.get(KmnmtEmploymentHist_.kmnmtEmploymentHistPK)
						.get(KmnmtEmploymentHistPK_.emptcd).in(employmentCodes)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtEmploymentHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(
				criteriaBuilder.greaterThanOrEqualTo(root.get(KmnmtEmploymentHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtEmploymentHist> query = em.createQuery(cq);

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
	public List<EmploymentHistory> searchEmployee(List<String> employeeIds, GeneralDate baseDate,
			List<String> employmentCodes) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMPLOYMENT_HIST (KmnmtEmploymentHist SQL)
		CriteriaQuery<KmnmtEmploymentHist> cq = criteriaBuilder
				.createQuery(KmnmtEmploymentHist.class);

		// root data
		Root<KmnmtEmploymentHist> root = cq.from(KmnmtEmploymentHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		if(CollectionUtil.isEmpty( employeeIds) || CollectionUtil.isEmpty(employmentCodes)){
			return new ArrayList<>();
		}
		
		// employment in data employment
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtEmploymentHist_.kmnmtEmploymentHistPK)
						.get(KmnmtEmploymentHistPK_.emptcd).in(employmentCodes)));
		
		// employee id in data employee id
		lstpredicateWhere
		.add(criteriaBuilder.and(root.get(KmnmtEmploymentHist_.kmnmtEmploymentHistPK)
				.get(KmnmtEmploymentHistPK_.sid).in(employeeIds)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtEmploymentHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(
				criteriaBuilder.greaterThanOrEqualTo(root.get(KmnmtEmploymentHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtEmploymentHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

}
