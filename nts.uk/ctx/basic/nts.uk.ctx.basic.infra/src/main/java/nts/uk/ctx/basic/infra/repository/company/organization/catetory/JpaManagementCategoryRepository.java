/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.catetory;

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
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategory;
import nts.uk.ctx.basic.dom.company.organization.category.ManagementCategoryRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.catetory.CclmtManagementCategory;
import nts.uk.ctx.basic.infra.entity.company.organization.catetory.CclmtManagementCategoryPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.catetory.CclmtManagementCategory_;

/**
 * The Class JpaManagementCategoryRepository.
 */
@Stateless
public class JpaManagementCategoryRepository extends JpaRepository
	implements ManagementCategoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryRepository#add(nts.uk.ctx.basic.dom.company.
	 * organization.category.ManagementCategory)
	 */
	@Override
	public void add(ManagementCategory managementCategory) {
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
	public void update(ManagementCategory managementCategory) {
		this.commandProxy().update(this.toEntity(managementCategory));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.basic.dom.company.organization.category.
	 * ManagementCategoryRepository#getAllManagementCategory(java.lang.String)
	 */
	@Override
	public List<ManagementCategory> getAllManagementCategory(String companyId) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call CCLMT_MANAGEMENT_CATEGORY (CclmtManagementCategory SQL)
		CriteriaQuery<CclmtManagementCategory> cq = criteriaBuilder
			.createQuery(CclmtManagementCategory.class);

		// root data
		Root<CclmtManagementCategory> root = cq.from(CclmtManagementCategory.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere
			.add(criteriaBuilder.equal(root.get(CclmtManagementCategory_.cclmtManagementCategoryPK)
				.get(CclmtManagementCategoryPK_.ccid), companyId));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<CclmtManagementCategory> query = em.createQuery(cq);

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
	private CclmtManagementCategory toEntity(ManagementCategory domain){
		CclmtManagementCategory entity = new CclmtManagementCategory();
		domain.saveToMemento(new JpaManagementCategorySetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the management category
	 */
	private ManagementCategory toDomain(CclmtManagementCategory entity){
		return new ManagementCategory(new JpaManagementCategoryGetMemento(entity));
	}

}
