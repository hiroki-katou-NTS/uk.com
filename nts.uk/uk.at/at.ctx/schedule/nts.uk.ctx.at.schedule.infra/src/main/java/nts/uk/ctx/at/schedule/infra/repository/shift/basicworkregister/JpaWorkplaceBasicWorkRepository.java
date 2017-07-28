/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSet_;

/**
 * The Class JpaWorkplaceBasicWorkRepository.
 */
@Stateless
public class JpaWorkplaceBasicWorkRepository extends JpaRepository implements WorkplaceBasicWorkRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#insert(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.WorkplaceBasicWork)
	 */
	@Override
	public void insert(WorkplaceBasicWork workplaceBasicWork) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.WorkplaceBasicWork)
	 */
	@Override
	public void update(WorkplaceBasicWork workplaceBasicWork) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String workplaceId) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#find(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Optional<WorkplaceBasicWork> find(String workplaceId, Integer workdayDivision) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#findAll(java.lang.String)
	 */
	@Override
	public Optional<WorkplaceBasicWork> findAll(String workplaceId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KwbmtWorkplaceWorkSet> cq = bd.createQuery(KwbmtWorkplaceWorkSet.class);
		// Root
		Root<KwbmtWorkplaceWorkSet> root = cq.from(KwbmtWorkplaceWorkSet.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KwbmtWorkplaceWorkSet_.kwbmtWorkplaceWorkSetPK).get(KwbmtWorkplaceWorkSetPK_.workplaceId),
				workplaceId));
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KwbmtWorkplaceWorkSet> query = em.createQuery(cq);
		return Optional.of(this.toDomain(query.getResultList()));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the workplace basic work
	 */
	private WorkplaceBasicWork toDomain(List<KwbmtWorkplaceWorkSet> entity) {
		return new WorkplaceBasicWork(new JpaWorkplaceBasicWorkGetMemento(entity));
	}

}
