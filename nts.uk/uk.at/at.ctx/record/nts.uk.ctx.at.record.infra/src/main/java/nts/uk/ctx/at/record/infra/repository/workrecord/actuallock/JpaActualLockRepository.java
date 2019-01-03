/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.actuallock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcstActualLock;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcstActualLockPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcstActualLockPK_;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcstActualLock_;

/**
 * The Class JpaActualLockRepository.
 */
@Stateless
public class JpaActualLockRepository extends JpaRepository implements ActualLockRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#add(
	 * nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock)
	 */
	@Override
	public void add(ActualLock actualLock) {
		KrcstActualLock entity = new KrcstActualLock();
		actualLock.saveToMemento(new JpaActualLockSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#
	 * update(nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock)
	 */
	@Override
	public void update(ActualLock actualLock) {
		Optional<KrcstActualLock> optional = this.queryProxy().find(
				new KrcstActualLockPK(actualLock.getCompanyId(), actualLock.getClosureId().value),
				KrcstActualLock.class);
		KrcstActualLock entity = new KrcstActualLock();
		if (optional.isPresent()) {
			entity = optional.get();
		}
		actualLock.saveToMemento(new JpaActualLockSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#
	 * findAll(java.lang.String)
	 */
	@Override
	public List<ActualLock> findAll(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KrcstActualLock> cq = bd.createQuery(KrcstActualLock.class);

		// Root
		Root<KrcstActualLock> root = cq.from(KrcstActualLock.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList
				.add(bd.equal(root.get(KrcstActualLock_.krcstActualLockPK).get(KrcstActualLockPK_.cid), companyId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KrcstActualLock> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#
	 * findById(java.lang.String, int)
	 */
	@Override
	public Optional<ActualLock> findById(String companyId, int closureId) {
		return this.queryProxy().find(new KrcstActualLockPK(companyId, closureId), KrcstActualLock.class)
				.map(c -> this.toDomain(c));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#
	 * findByListId(java.lang.String, java.util.List)
	 */
	@Override
	public List<ActualLock> findByListId(String companyId, List<Integer> closureIds) {
		// check empty closure id 
		if(CollectionUtil.isEmpty(closureIds)){
			return new ArrayList<>();
		}
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KrcstActualLock> cq = bd.createQuery(KrcstActualLock.class);

		// Root
		Root<KrcstActualLock> root = cq.from(KrcstActualLock.class);
		cq.select(root);

		List<KrcstActualLock> resultList = new ArrayList<>();

		CollectionUtil.split(closureIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// Predicate where clause
			List<Predicate> predicateList = new ArrayList<>();
			predicateList
					.add(bd.equal(root.get(KrcstActualLock_.krcstActualLockPK).get(KrcstActualLockPK_.cid), companyId));
			
			// in closure id
			predicateList
					.add(root.get(KrcstActualLock_.krcstActualLockPK).get(KrcstActualLockPK_.closureId).in(splitData));

			// Set Where clause to SQL Query
			cq.where(predicateList.toArray(new Predicate[] {}));

			resultList.addAll(em.createQuery(cq).getResultList());
		});

		return resultList.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#
	 * remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int closureId) {
		this.commandProxy().remove(KrcstActualLock.class, new KrcstActualLockPK(companyId, closureId));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the actual lock
	 */
	private ActualLock toDomain(KrcstActualLock entity) {
		return new ActualLock(new JpaActualLockGetMemento(entity));

	}

}
