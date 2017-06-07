/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosureHist;

/**
 * The Class JpaClosureHistoryRepository.
 */
@Stateless
public class JpaClosureHistoryRepository extends JpaRepository implements ClosureHistoryRepository{

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
		this.commandProxy().update(this.toEntity(closureHistory));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository#
	 * findByClosureId(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId)
	 */
	@Override
	public List<ClosureHistory> findByClosureId(ClosureId closureId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE_HIST (KclmtClosureHist SQL)
		CriteriaQuery<KclmtClosureHist> cq = criteriaBuilder.createQuery(KclmtClosureHist.class);

		// root data
		Root<KclmtClosureHist> root = cq.from(KclmtClosureHist.class);

		// select root
		cq.select(root);

		// creat query
		TypedQuery<KclmtClosureHist> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
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
}
