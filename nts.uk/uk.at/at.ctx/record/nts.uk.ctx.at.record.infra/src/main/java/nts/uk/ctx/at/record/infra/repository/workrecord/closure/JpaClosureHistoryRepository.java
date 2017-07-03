/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

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
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHist;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHistPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHistPK_;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHist_;

/**
 * The Class JpaClosureHistoryRepository.
 */
@Stateless
public class JpaClosureHistoryRepository extends JpaRepository implements ClosureHistoryRepository{
	
	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;
	
	/** The Constant FIRST_LENGTH. */
	public static final int FIRST_LENGTH = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository#add(
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory)
	 */
	@Override
	public void add(ClosureHistory closureHistory) {
		this.commandProxy().insert(this.toEntity(closureHistory));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository#
	 * update(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory)
	 */
	@Override
	public void update(ClosureHistory closureHistory) {
		this.commandProxy().update(this.toEntityUpdate(closureHistory));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository#
	 * findByClosureId(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId)
	 */
	@Override
	public List<ClosureHistory> findByClosureId(String companyId, int closureId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE_HIST (KclmtClosureHist SQL)
		CriteriaQuery<KclmtClosureHist> cq = criteriaBuilder.createQuery(KclmtClosureHist.class);

		// root data
		Root<KclmtClosureHist> root = cq.from(KclmtClosureHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(KclmtClosureHist_.kclmtClosureHistPK).get(KclmtClosureHistPK_.cid),
			companyId));

		// equal closure id
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(KclmtClosureHist_.kclmtClosureHistPK).get(KclmtClosureHistPK_.closureId),
			closureId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by end date
		cq.orderBy(criteriaBuilder.desc(root.get(KclmtClosureHist_.endD)));

		// create query
		TypedQuery<KclmtClosureHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository#
	 * findByHistoryId(java.lang.String, int, java.lang.String)
	 */
	@Override
	public Optional<ClosureHistory> findById(String companyId, int closureId,
			String historyId) {
		return this.queryProxy().find(new KclmtClosureHistPK(companyId, closureId, historyId),
				KclmtClosureHist.class).map(c -> this.toDomain(c));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the closure history
	 */
	private ClosureHistory toDomain(KclmtClosureHist entity){
		return new ClosureHistory(new JpaClosureHistoryGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kclmt closure hist
	 */
	private KclmtClosureHist toEntity(ClosureHistory domain){
		KclmtClosureHist entity = new KclmtClosureHist();
		domain.saveToMemento(new JpaClosureHistorySetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity update.
	 *
	 * @param domain the domain
	 * @return the kclmt closure hist
	 */
	private KclmtClosureHist toEntityUpdate(ClosureHistory domain){
		Optional<KclmtClosureHist> optionalEntity = this
				.queryProxy().find(
						new KclmtClosureHistPK(domain.getCompanyId().v(),
								domain.getClosureId().value, domain.getClosureHistoryId().v()),
						KclmtClosureHist.class);
		KclmtClosureHist entity = new KclmtClosureHist();
		if(optionalEntity.isPresent()){
			entity = optionalEntity.get();
		}
		domain.saveToMemento(new JpaClosureHistorySetMemento(entity));
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository#
	 * findByCompanyId(java.lang.String)
	 */
	@Override
	public List<ClosureHistory> findByCompanyId(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE_HIST (KclmtClosureHist SQL)
		CriteriaQuery<KclmtClosureHist> cq = criteriaBuilder.createQuery(KclmtClosureHist.class);

		// root data
		Root<KclmtClosureHist> root = cq.from(KclmtClosureHist.class);

		// select root
		cq.select(root);

		// create query
		TypedQuery<KclmtClosureHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
	}


	@Override
	public Optional<ClosureHistory> findBySelectedYearMonth(String companyId, int closureId,
			int yearMonth) {
		
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE_HIST (KclmtClosureHist SQL)
		CriteriaQuery<KclmtClosureHist> cq = criteriaBuilder.createQuery(KclmtClosureHist.class);

		// root data
		Root<KclmtClosureHist> root = cq.from(KclmtClosureHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(KclmtClosureHist_.kclmtClosureHistPK).get(KclmtClosureHistPK_.cid),
			companyId));
		
		// equal closure id
		lstpredicateWhere.add(criteriaBuilder.equal(
			root.get(KclmtClosureHist_.kclmtClosureHistPK).get(KclmtClosureHistPK_.closureId),
			closureId));
		
		// less than or equal year month
		lstpredicateWhere.add(
				criteriaBuilder.lessThanOrEqualTo(root.get(KclmtClosureHist_.strD), yearMonth));
		
		
		// great than or equal year month
		lstpredicateWhere.add(
				criteriaBuilder.greaterThanOrEqualTo(root.get(KclmtClosureHist_.endD), yearMonth));
		
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		// create query
		TypedQuery<KclmtClosureHist> query = em.createQuery(cq).setMaxResults(FIRST_LENGTH);

		// exclude select
		List<KclmtClosureHist> resData = query.getResultList();
		
		if(CollectionUtil.isEmpty(resData)){
			return Optional.empty();
		}
		
		return Optional.ofNullable(this.toDomain(resData.get(FIRST_DATA)));
	}

	@Override
	public Optional<ClosureHistory> findByHistoryLast(String companyId, int closureId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE_HIST (KclmtClosureHist SQL)
		CriteriaQuery<KclmtClosureHist> cq = criteriaBuilder.createQuery(KclmtClosureHist.class);

		// root data
		Root<KclmtClosureHist> root = cq.from(KclmtClosureHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KclmtClosureHist_.kclmtClosureHistPK).get(KclmtClosureHistPK_.cid),
				companyId));

		// equal closure id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KclmtClosureHist_.kclmtClosureHistPK).get(KclmtClosureHistPK_.closureId),
				closureId));

		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by end date desc
		cq.orderBy(criteriaBuilder.desc(root.get(KclmtClosureHist_.endD)));
		
		// create query
		TypedQuery<KclmtClosureHist> query = em.createQuery(cq).setMaxResults(FIRST_LENGTH);

		// exclude select
		List<KclmtClosureHist> resData = query.getResultList();

		if (CollectionUtil.isEmpty(resData)) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(resData.get(FIRST_DATA)));
	}

	
}
