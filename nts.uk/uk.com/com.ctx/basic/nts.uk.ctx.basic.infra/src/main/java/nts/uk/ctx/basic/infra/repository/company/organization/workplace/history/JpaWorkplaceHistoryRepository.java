/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace.history;

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
import nts.uk.ctx.basic.dom.company.organization.workplace.history.WorkplaceHistory;
import nts.uk.ctx.basic.dom.company.organization.workplace.history.WorkplaceHistoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.history.KmnmtWorkplaceHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.history.KmnmtWorkplaceHist_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.history.KmnmtWorkplaceHist;

/**
 * The Class JpaWorkplaceHistoryRepository.
 */
@Stateless
public class JpaWorkplaceHistoryRepository extends JpaRepository
		implements WorkplaceHistoryRepository {

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.workplace.history.
	 * WorkplaceHistoryRepository#searchEmployee(nts.arc.time.GeneralDate,
	 * java.util.List)
	 */
	@Override
	public List<WorkplaceHistory> searchEmployee(GeneralDate baseDate,
			List<String> workplaces) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KMNMT_EMP_WORKPLACE_HIST (KmnmtEmpWorkplaceHist SQL)
		CriteriaQuery<KmnmtWorkplaceHist> cq = criteriaBuilder
				.createQuery(KmnmtWorkplaceHist.class);

		// root data
		Root<KmnmtWorkplaceHist> root = cq.from(KmnmtWorkplaceHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// work place in data work place
		lstpredicateWhere
				.add(criteriaBuilder.and(root.get(KmnmtWorkplaceHist_.kmnmtEmpWorkplaceHistPK)
						.get(KmnmtWorkplaceHistPK_.wplId).in(workplaces)));

		// start date <= base date
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KmnmtWorkplaceHist_.strD), baseDate));

		// endDate >= base date
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KmnmtWorkplaceHist_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KmnmtWorkplaceHist> query = em.createQuery(cq);

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
	private WorkplaceHistory toDomain(KmnmtWorkplaceHist entity){
		return new WorkplaceHistory(new JpaWorkplaceHistoryGetMemento(entity));
	}

}
