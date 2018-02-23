/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondPK_;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond_;

/**
 * The Class JpaWorkingConditionRepository.
 */
@RequestScoped
public class JpaWorkingConditionRepository extends JpaRepository implements WorkingConditionRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getAllWokingCondition(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> getBySid(String companyId, String sId) {

		List<KshmtWorkingCond> result = this.findBy(companyId, sId, null);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getBySid(java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> getBySid(String sId) {

		List<KshmtWorkingCond> result = this.findBy(null, sId, null);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getByHistoryId(java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> getByHistoryId(String historyId) {
		List<KshmtWorkingCond> result = this.findBy(null, null, historyId);

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		// exclude select
		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getBySidAndStandardDate(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<WorkingCondition> getBySidAndStandardDate(String employeeId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.sid), employeeId));
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(root.get(KshmtWorkingCond_.strD), baseDate));
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KshmtWorkingCond_.endD), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

		List<KshmtWorkingCond> result = query.getResultList();

		// Check exist
		if (CollectionUtil.isEmpty(result)) {
			return Optional.empty();
		}

		return Optional.of(new WorkingCondition(new JpaWorkingConditionGetMemento(result)));
	}

	/**
	 * Adds the.
	 *
	 * @param entities
	 *            the entities
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#add(
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	private void add(List<KshmtWorkingCond> entities) {
		this.commandProxy().insertAll(entities);
		this.getEntityManager().flush();
	}

	/**
	 * Update.
	 *
	 * @param entities
	 *            the entities
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * update(nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	private void update(List<KshmtWorkingCond> entities) {
		this.commandProxy().updateAll(entities);
	}

	/**
	 * Delete all.
	 *
	 * @param entities
	 *            the entities
	 */
	private void deleteAll(List<KshmtWorkingCond> entities) {
		this.commandProxy().removeAll(entities);
		this.getEntityManager().flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#save
	 * (nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	@Override
	@Transactional
	public void save(WorkingCondition workingCondition) {
		List<KshmtWorkingCond> entities = this.findBy(workingCondition.getCompanyId(), workingCondition.getEmployeeId(),
		null);
		
		List<KshmtWorkingCond> newWorkingCondition = entities;

		workingCondition.saveToMemento(new JpaWorkingConditionSetMemento(newWorkingCondition));
		
//		this.add(newWorkingCondition.stream().filter(item ->  !entities.contains(item)).collect(Collectors.toList()));

		this.update(newWorkingCondition);
		
		this.deleteAll(entities.stream().filter(item ->  !newWorkingCondition.contains(item)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String employeeId) {
		List<KshmtWorkingCond> entities = this.findBy(null, employeeId, null);
		this.commandProxy().removeAll(entities);
	}

	/**
	 * Find by.
	 *
	 * @param companyId
	 *            the company id
	 * @param sId
	 *            the s id
	 * @param historyId
	 *            the history id
	 * @return the list
	 */
	private List<KshmtWorkingCond> findBy(String companyId, String sId, String historyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtWorkingCond> cq = criteriaBuilder.createQuery(KshmtWorkingCond.class);

		// root data
		Root<KshmtWorkingCond> root = cq.from(KshmtWorkingCond.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		if (companyId != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), companyId));
		}

		if (sId != null) {
			lstpredicateWhere.add(criteriaBuilder
					.equal(root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.sid), sId));
		}

		if (historyId != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.historyId), historyId));
		}

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// Order by start date
		cq.orderBy(criteriaBuilder.desc(root.get(KshmtWorkingCond_.strD)));

		// creat query
		TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

		return query.getResultList();
	}

}
