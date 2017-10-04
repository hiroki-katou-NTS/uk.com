/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
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

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLock;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLock;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLockPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLockPK_;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLock_;

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
		KrcmtActualLock entity = new KrcmtActualLock();
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
		Optional<KrcmtActualLock> optional = this.queryProxy().find(
				new KrcmtActualLockPK(actualLock.getCompanyId(), actualLock.getClosureId().value),
				KrcmtActualLock.class);
		KrcmtActualLock entity = null;
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KrcmtActualLock();
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
		CriteriaQuery<KrcmtActualLock> cq = bd.createQuery(KrcmtActualLock.class);

		// Root
		Root<KrcmtActualLock> root = cq.from(KrcmtActualLock.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList
				.add(bd.equal(root.get(KrcmtActualLock_.krcmtActualLockPK).get(KrcmtActualLockPK_.cid), companyId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KrcmtActualLock> query = em.createQuery(cq);

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
		return this.queryProxy().find(new KrcmtActualLockPK(companyId, closureId), KrcmtActualLock.class)
				.map(c -> this.toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockRepository#
	 * remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int closureId) {
		this.commandProxy().remove(KrcmtActualLock.class, new KrcmtActualLockPK(companyId, closureId));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the actual lock
	 */
	private ActualLock toDomain(KrcmtActualLock entity) {
		return new ActualLock(new JpaActualLockGetMemento(entity));

	}

}
