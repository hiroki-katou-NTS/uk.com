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
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistory;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.ActualLockHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLockHist;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLockHistPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLockHistPK_;
import nts.uk.ctx.at.record.infra.entity.workrecord.actuallock.KrcmtActualLockHist_;

/**
 * The Class JpaActualLockHistRepository.
 */
@Stateless
public class JpaActualLockHistRepository extends JpaRepository implements ActualLockHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#add(nts.uk.ctx.at.record.dom.workrecord.
	 * actuallock.ActualLockHistory)
	 */
	@Override
	public void add(ActualLockHistory actualLockHistory) {
		KrcmtActualLockHist entity = new KrcmtActualLockHist();
		actualLockHistory.saveToMemento(new JpaActualLockHistSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#update(nts.uk.ctx.at.record.dom.workrecord.
	 * actuallock.ActualLockHistory)
	 */
	@Override
	public void update(ActualLockHistory actualLockHistory) {
		Optional<KrcmtActualLockHist> optional = this.queryProxy()
				.find(new KrcmtActualLockHistPK(actualLockHistory.getCompanyId(),
						actualLockHistory.getClosureId().value, actualLockHistory.getLockDateTime()),
						KrcmtActualLockHist.class);
		KrcmtActualLockHist entity = null;
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KrcmtActualLockHist();
		}
		actualLockHistory.saveToMemento(new JpaActualLockHistSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#findAll(java.lang.String)
	 */
	@Override
	public List<ActualLockHistory> findAll(String companyId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtActualLockHist> cq = bd.createQuery(KrcmtActualLockHist.class);

		// Root
		Root<KrcmtActualLockHist> root = cq.from(KrcmtActualLockHist.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(
				bd.equal(root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.cid), companyId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// Create Query
		TypedQuery<KrcmtActualLockHist> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#findByClosureId(java.lang.String, int)
	 */
	@Override
	public List<ActualLockHistory> findByClosureId(String companyId, int closureId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtActualLockHist> cq = cb.createQuery(KrcmtActualLockHist.class);

		// Root
		Root<KrcmtActualLockHist> root = cq.from(KrcmtActualLockHist.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();

		// Equal CompanyId
		predicateList.add(
				cb.equal(root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.cid), companyId));
		// Equal ClosureId
		predicateList.add(cb.equal(
				root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.closureId), closureId));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// LockDate Desc
		cq.orderBy(cb.desc(root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.lockDate)));

		// Create Query
		TypedQuery<KrcmtActualLockHist> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#findByLockDate(java.lang.String, int,
	 * nts.arc.time.GeneralDateTime)
	 */
	@Override
	public Optional<ActualLockHistory> findByLockDate(String companyId, int closureId, GeneralDateTime lockDate) {
		return this.queryProxy()
				.find(new KrcmtActualLockHistPK(companyId, closureId, lockDate), KrcmtActualLockHist.class)
				.map(c -> this.toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#findByTargetMonth(java.lang.String, int, int)
	 */
	@Override
	public List<ActualLockHistory> findByTargetMonth(String companyId, int closureId, int targetMonth) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtActualLockHist> cq = cb.createQuery(KrcmtActualLockHist.class);

		// Root
		Root<KrcmtActualLockHist> root = cq.from(KrcmtActualLockHist.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();

		// Equal CompanyId
		predicateList.add(
				cb.equal(root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.cid), companyId));
		// Equal ClosureId
		predicateList.add(cb.equal(
				root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.closureId), closureId));
		// Equal TargetMonth
		predicateList.add(cb.equal(root.get(KrcmtActualLockHist_.targetMonth), targetMonth));

		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));

		// LockDate Desc
		cq.orderBy(cb.desc(root.get(KrcmtActualLockHist_.krcmtActualLockPK).get(KrcmtActualLockHistPK_.lockDate)));

		// Create Query
		TypedQuery<KrcmtActualLockHist> query = em.createQuery(cq);

		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.actuallock.
	 * ActualLockHistoryRepository#remove(java.lang.String, int,
	 * nts.arc.time.GeneralDateTime)
	 */
	@Override
	public void remove(String companyId, int closureId, GeneralDateTime lockDate) {
		this.commandProxy().remove(KrcmtActualLockHist.class,
				new KrcmtActualLockHistPK(companyId, closureId, lockDate));
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the actual lock history
	 */
	private ActualLockHistory toDomain(KrcmtActualLockHist entity) {
		return new ActualLockHistory(new JpaActualLockHistGetMemento(entity));

	}


}
