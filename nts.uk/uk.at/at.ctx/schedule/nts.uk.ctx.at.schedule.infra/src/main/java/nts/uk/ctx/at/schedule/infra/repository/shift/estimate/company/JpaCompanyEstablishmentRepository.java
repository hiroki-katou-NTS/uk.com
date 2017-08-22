/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.estimate.company;

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
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishment;
import nts.uk.ctx.at.schedule.dom.shift.estimate.company.CompanyEstablishmentRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.estimate.company.KscmtEstTimeComSet_;

/**
 * The Class JpaCompanyEstablishmentRepository.
 */
@Stateless
public class JpaCompanyEstablishmentRepository extends JpaRepository
		implements CompanyEstablishmentRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.company.
	 * CompanyEstablishmentRepository#findById(java.lang.String, int)
	 */
	@Override
	public Optional<CompanyEstablishment> findById(String companyId, int targetYear) {
		return Optional
				.ofNullable(this.toDomain(this.getEstimateTimeCompany(companyId, targetYear)));
	}
	
	/**
	 * Gets the estimate time company.
	 *
	 * @param companyId the company id
	 * @param targetYear the target year
	 * @return the estimate time company
	 */
	private List<KscmtEstTimeComSet> getEstimateTimeCompany(String companyId, int targetYear){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_EST_TIME_COM_SET (KscmtEstTimeComSet SQL)
		CriteriaQuery<KscmtEstTimeComSet> cq = criteriaBuilder
				.createQuery(KscmtEstTimeComSet.class);

		// root data
		Root<KscmtEstTimeComSet> root = cq.from(KscmtEstTimeComSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeComSet_.kscmtEstTimeComSetPK)
						.get(KscmtEstTimeComSetPK_.cid), companyId));
		
		// equal target year
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtEstTimeComSet_.kscmtEstTimeComSetPK)
						.get(KscmtEstTimeComSetPK_.targetYear), targetYear));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtEstTimeComSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	
	/**
	 * To domain.
	 *
	 * @param estimateTimeCompanys the estimate time companys
	 * @return the company establishment
	 */
	private CompanyEstablishment toDomain(List<KscmtEstTimeComSet> estimateTimeCompanys){
		return new CompanyEstablishment(new JpaCompanyEstablishmentGetMemento(estimateTimeCompanys));
	}

}
