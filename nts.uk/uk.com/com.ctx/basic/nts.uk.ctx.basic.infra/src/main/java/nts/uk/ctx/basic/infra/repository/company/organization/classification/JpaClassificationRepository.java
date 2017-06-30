/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.classification;

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
import nts.uk.ctx.basic.dom.company.organization.classification.Classification;
import nts.uk.ctx.basic.dom.company.organization.classification.ClassificationRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.CclmtClassification;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.CclmtClassificationPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.classification.CclmtClassification_;

/**
 * The Class JpaManagementCategoryRepository.
 */
@Stateless
public class JpaClassificationRepository extends JpaRepository
	implements ClassificationRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryRepository#add(nts.uk.ctx.basic.dom.company.
	 * organization.category.ManagementCategory)
	 */
	@Override
	public void add(Classification managementCategory) {
		this.commandProxy().insert(this.toEntity(managementCategory));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryRepository#update(nts.uk.ctx.basic.dom.company.
	 * organization.category.ManagementCategory)
	 */
	@Override
	public void update(Classification managementCategory) {
		this.commandProxy().update(this.toEntity(managementCategory));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryRepository#getAllManagementCategory(java.lang.String)
	 */
	@Override
	public List<Classification> getAllManagementCategory(String companyId) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call CCLMT_MANAGEMENT_CATEGORY (CclmtManagementCategory SQL)
		CriteriaQuery<CclmtClassification> cq = criteriaBuilder
			.createQuery(CclmtClassification.class);

		// root data
		Root<CclmtClassification> root = cq.from(CclmtClassification.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere
			.add(criteriaBuilder.equal(root.get(CclmtClassification_.cclmtClassificationPK)
				.get(CclmtClassificationPK_.cid), companyId));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<CclmtClassification> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
			.collect(Collectors.toList());
	}
	
	/**
	 * To entity.
	 *
	 * @param managementCategory the management category
	 * @return the cclmt management category
	 */
	private CclmtClassification toEntity(Classification domain){
		CclmtClassification entity = new CclmtClassification();
		domain.saveToMemento(new JpaClassificationSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the management category
	 */
	private Classification toDomain(CclmtClassification entity){
		return new Classification(new JpaClassificationGetMemento(entity));
	}

}
