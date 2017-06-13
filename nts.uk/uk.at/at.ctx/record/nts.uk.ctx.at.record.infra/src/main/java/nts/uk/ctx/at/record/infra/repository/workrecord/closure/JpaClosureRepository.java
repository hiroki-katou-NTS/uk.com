/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.closure;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.Closure;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosure;
import nts.uk.ctx.at.record.infra.entity.workrecord.closure.KclmtClosurePK;

/**
 * The Class JpaClosureRepository.
 */
@Stateless
public class JpaClosureRepository extends JpaRepository implements ClosureRepository{
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#add(nts.uk.
	 * ctx.at.record.dom.workrecord.closure.Closure)
	 */
	@Override
	public void add(Closure closure) {
		this.commandProxy().insert(this.toEntity(closure));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#update(nts.
	 * uk.ctx.at.record.dom.workrecord.closure.Closure)
	 */
	@Override
	public void update(Closure closure) {
		this.commandProxy().update(this.toEntity(closure));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#
	 * getAllClosure(java.lang.String)
	 */
	@Override
	public List<Closure> getAllClosure(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE (KclmtClosure SQL)
		CriteriaQuery<KclmtClosure> cq = criteriaBuilder.createQuery(KclmtClosure.class);

		// root data
		Root<KclmtClosure> root = cq.from(KclmtClosure.class);

		// select root
		cq.select(root);

		// create query
		TypedQuery<KclmtClosure> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
			.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository#
	 * getClosureById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<Closure> getClosureById(String companyId, int closureId) {
		return this.queryProxy().find(new KclmtClosurePK(companyId, closureId), KclmtClosure.class)
			.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the closure
	 */
	private Closure toDomain(KclmtClosure entity){
		return new Closure(new JpaClosureGetMemento(entity));
	}

	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kclmt closure
	 */
	private KclmtClosure toEntity(Closure domain){
		KclmtClosure entity = new KclmtClosure();
		domain.saveToMemento(new JpaClosureSetMemento(entity));
		return entity;
	}

}
