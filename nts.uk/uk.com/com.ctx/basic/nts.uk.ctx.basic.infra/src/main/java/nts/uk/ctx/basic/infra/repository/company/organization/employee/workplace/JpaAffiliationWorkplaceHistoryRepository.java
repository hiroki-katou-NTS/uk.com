/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.employee.workplace;

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
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffiliationWorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.employee.workplace.AffiliationWorkplaceHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace.KmnmtAffiliWorkplaceHist;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace.KmnmtAffiliWorkplaceHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.employee.workplace.KmnmtAffiliWorkplaceHist_;

/**
 * The Class JpaAffiliationWorkplaceHistoryRepository.
 */
@Stateless
public class JpaAffiliationWorkplaceHistoryRepository extends JpaRepository
		implements AffiliationWorkplaceHistoryRepository {

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryRepository#searchEmployee(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<AffiliationWorkplaceHistory> searchWorkplaceHistory(GeneralDate baseDate,
			List<String> workplaces) {

		// check exist data
		if(CollectionUtil.isEmpty(workplaces)){
			return new ArrayList<>();
		}
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_WORKPLACE_HIST (KmnmtWorkplaceHist SQL)
		CriteriaQuery<KmnmtAffiliWorkplaceHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliWorkplaceHist.class);

		// root data
		Root<KmnmtAffiliWorkplaceHist> root = cq.from(KmnmtAffiliWorkplaceHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// work place in data work place
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliWorkplaceHist_.kmnmtAffiliWorkplaceHistPK)
						.get(KmnmtAffiliWorkplaceHistPK_.wplId).in(workplaces)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtAffiliWorkplaceHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtAffiliWorkplaceHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliWorkplaceHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employee workplace history
	 */
	private AffiliationWorkplaceHistory toDomain(KmnmtAffiliWorkplaceHist entity){
		return new AffiliationWorkplaceHistory(new JpaAffiliationWorkplaceHistoryGetMemento(entity));
	}

	@Override
	public List<AffiliationWorkplaceHistory> searchWorkplaceHistory(List<String> employeeIds, GeneralDate baseDate,
			List<String> workplaces) {
		
		// check exist data
		if (CollectionUtil.isEmpty(employeeIds) || CollectionUtil.isEmpty(workplaces)) {
			return new ArrayList<>();
		}
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_WORKPLACE_HIST (KmnmtWorkplaceHist SQL)
		CriteriaQuery<KmnmtAffiliWorkplaceHist> cq = criteriaBuilder
				.createQuery(KmnmtAffiliWorkplaceHist.class);

		// root data
		Root<KmnmtAffiliWorkplaceHist> root = cq.from(KmnmtAffiliWorkplaceHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// employee id in data employee id 
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtAffiliWorkplaceHist_.kmnmtAffiliWorkplaceHistPK)
						.get(KmnmtAffiliWorkplaceHistPK_.sid).in(employeeIds)));
		
		// work place in data work place
		lstpredicateWhere
		.add(criteriaBuilder.and(root.get(KmnmtAffiliWorkplaceHist_.kmnmtAffiliWorkplaceHistPK)
				.get(KmnmtAffiliWorkplaceHistPK_.wplId).in(workplaces)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtAffiliWorkplaceHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(
				criteriaBuilder.greaterThanOrEqualTo(root.get(KmnmtAffiliWorkplaceHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KmnmtAffiliWorkplaceHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

}
