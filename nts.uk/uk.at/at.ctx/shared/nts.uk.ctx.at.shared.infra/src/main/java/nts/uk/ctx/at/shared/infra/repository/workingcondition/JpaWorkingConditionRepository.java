/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondPK_;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCond_;

/**
 * The Class JpaWorkingConditionRepository.
 */
@Stateless
public class JpaWorkingConditionRepository extends JpaRepository
		implements WorkingConditionRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * getWorkingConditionHistory(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WorkingCondition> getWorkingConditionHistory(String companyId, String sId,
			String historyId) {

		List<KshmtWorkingCond> result = this.findBy(companyId, sId, historyId);

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
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#add(
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	@Override
	public void add(WorkingCondition workingCondition) {
		List<KshmtWorkingCond> entities = new ArrayList<>();
		workingCondition.saveToMemento(new JpaWorkingConditionSetMemento(entities));
		this.commandProxy().insertAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * update(nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition)
	 */
	@Override
	public void update(WorkingCondition workingCondition) {
		List<KshmtWorkingCond> entities = findBy(workingCondition.getCompanyId(),
				workingCondition.getEmployeeId(), null);
		workingCondition.saveToMemento(new JpaWorkingConditionSetMemento(entities));
		this.commandProxy().updateAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String employeeId) {
		List<KshmtWorkingCond> entities = findBy(null, employeeId, null);
		this.commandProxy().removeAll(entities);
	}

	/**
	 * Find by.
	 *
	 * @param companyId
	 *            the company id
	 * @param sId
	 *            the s id
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
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.cid), companyId));
		}

		if (sId != null) {
			lstpredicateWhere.add(criteriaBuilder.equal(
					root.get(KshmtWorkingCond_.kshmtWorkingCondPK).get(KshmtWorkingCondPK_.sid),
					sId));
		}

		if (historyId != null) {
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtWorkingCond_.kshmtWorkingCondPK)
							.get(KshmtWorkingCondPK_.historyId), historyId));
		}

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtWorkingCond> query = em.createQuery(cq);

		return query.getResultList();
	}

}
