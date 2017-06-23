/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.repository.company.organization.workplace;

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
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHierarchy;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkPlaceHistory;
import nts.uk.ctx.basic.dom.company.organization.workplace.Workplace;
import nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplace;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplacePK_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWorkplace_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHierarchy;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHierarchyPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHierarchy_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHist;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHistPK_;
import nts.uk.ctx.basic.infra.entity.company.organization.workplace.KwpmtWplHist_;

/**
 * The Class JpaWorkplaceRepository.
 */
@Stateless
public class JpaWorkplaceRepository extends JpaRepository implements WorkplaceRepository {
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository#
	 * add(nts.uk.ctx.basic.dom.company.organization.workplace.Workplace)
	 */
	@Override
	public void add(Workplace workplace) {
		this.commandProxy().insert(this.toEntity(workplace));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.basic.dom.company.organization.workplace.WorkplaceRepository#
	 * update(nts.uk.ctx.basic.dom.company.organization.workplace.Workplace)
	 */
	@Override
	public void update(Workplace workplace) {
		this.commandProxy().update(this.toEntity(workplace));
	}
	
	@Override
	public List<WorkPlaceHistory> findAllHistory(String companyId,GeneralDate generalDate){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KwpmtWplHist> cq = criteriaBuilder.createQuery(KwpmtWplHist.class);
		Root<KwpmtWplHist> root = cq.from(KwpmtWplHist.class);

		// select root
		cq.select(root);
		
		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KwpmtWplHist_.kwpmtWplHistPK).get(KwpmtWplHistPK_.cid),companyId));
//		// >= general date
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(root.get(KwpmtWplHist_.strD), generalDate));
//		// <= general date
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KwpmtWplHist_.endD), generalDate));
		
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// exclude select
		return em.createQuery(cq).getResultList().stream().map(item->this.histToDomain(item)).collect(Collectors.toList());
	}
	
	
	public List<WorkPlaceHierarchy> findAllHierarchy(String historyId){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWPMT_WORK_PLACE (KwpmtWorkPlace SQL)
		CriteriaQuery<KwpmtWplHierarchy> cq = criteriaBuilder.createQuery(KwpmtWplHierarchy.class);

		// root data
		Root<KwpmtWplHierarchy> root = cq.from(KwpmtWplHierarchy.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		//equals companyId
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KwpmtWplHierarchy_.kwpmtWplHierarchyPK).get(KwpmtWplHierarchyPK_.hisId),historyId));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		cq.orderBy(criteriaBuilder.asc(root.get(KwpmtWplHierarchy_.wplcd)));
		// create query
		TypedQuery<KwpmtWplHierarchy> query = em.createQuery(cq);
		
		List<KwpmtWplHierarchy> lst = query.getResultList();
		// exclude select
		return lst.stream().map(item->this.hierarchyToDomain(item)).collect(Collectors.toList());
	}
	
	public List<Workplace> findAllWorkplace(String workplaceId){
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWPMT_WORK_PLACE (KwpmtWorkPlace SQL)
		CriteriaQuery<KwpmtWorkplace> cq = criteriaBuilder.createQuery(KwpmtWorkplace.class);

		// root data
		Root<KwpmtWorkplace> root = cq.from(KwpmtWorkplace.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		//equals workplaceId
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KwpmtWorkplace_.kwpmtWorkplacePK).get(KwpmtWorkplacePK_.wplid),workplaceId));
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KwpmtWorkplace> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item->this.toDomain(item)).collect(Collectors.toList());
	}
	
	private WorkPlaceHistory histToDomain(KwpmtWplHist entity){
		return new WorkPlaceHistory(new JpaWorkplaceHistoryGetMemento(entity));
	}
	
	private WorkPlaceHierarchy hierarchyToDomain(KwpmtWplHierarchy entity) {
		return new WorkPlaceHierarchy(new JpaWorkplaceHierarchyGetMemento(entity));
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the workplace
	 */
	private Workplace toDomain(KwpmtWorkplace entity){
		return new Workplace(new JpaWorkplaceGetMemento(entity));
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kwpmt work place
	 */
	private KwpmtWorkplace toEntity(Workplace domain){
		KwpmtWorkplace entity = new KwpmtWorkplace();
		domain.saveToMemento(new JpaWorkplaceSetMemento(entity));
		return entity;
	}

}
