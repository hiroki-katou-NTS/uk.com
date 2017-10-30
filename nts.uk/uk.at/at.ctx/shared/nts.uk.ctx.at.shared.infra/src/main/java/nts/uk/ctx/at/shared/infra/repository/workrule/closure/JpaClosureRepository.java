/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workrule.closure;

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
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosure;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosurePK;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosurePK_;
import nts.uk.ctx.at.shared.infra.entity.workrule.closure.KclmtClosure_;

/**
 * The Class JpaClosureRepository.
 */
@Stateless
public class JpaClosureRepository extends JpaRepository implements ClosureRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository#add(nts.uk.
	 * ctx.at.shared.dom.workrule.closure.Closure)
	 */
	@Override
	public void add(Closure closure) {
		this.commandProxy().insert(this.toEntity(closure));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository#update(nts.uk
	 * .ctx.at.shared.dom.workrule.closure.Closure)
	 */
	@Override
	public void update(Closure closure) {
		this.commandProxy().update(this.toEntityUpdate(closure));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository#findAll(java.
	 * lang.String)
	 */
	@Override
	public List<Closure> findAll(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE (KclmtClosure SQL)
		CriteriaQuery<KclmtClosure> cq = criteriaBuilder.createQuery(KclmtClosure.class);

		// root data
		Root<KclmtClosure> root = cq.from(KclmtClosure.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.cid), companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.closureId)));

		// create query
		TypedQuery<KclmtClosure> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository#findAllUse(
	 * java.lang.String)
	 */
	@Override
	public List<Closure> findAllUse(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE (KclmtClosure SQL)
		CriteriaQuery<KclmtClosure> cq = criteriaBuilder.createQuery(KclmtClosure.class);

		// root data
		Root<KclmtClosure> root = cq.from(KclmtClosure.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.cid), companyId));
		
		// is use closure 
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KclmtClosure_.useClass),
				UseClassification.UseClass_Use.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.closureId)));

		// create query
		TypedQuery<KclmtClosure> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository#findById(java
	 * .lang.String, int)
	 */
	@Override
	public Optional<Closure> findById(String companyId, int closureId) {
		return this.queryProxy().find(new KclmtClosurePK(companyId, closureId), KclmtClosure.class)
				.map(c -> this.toDomain(c));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository#findByListId(
	 * java.lang.String, java.util.List)
	 */
	@Override
	public List<Closure> findByListId(String companyId, List<Integer> closureIds) {

		// check closure id empty
		if (CollectionUtil.isEmpty(closureIds)) {
			return new ArrayList<>();
		}
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KCLMT_CLOSURE (KclmtClosure SQL)
		CriteriaQuery<KclmtClosure> cq = criteriaBuilder.createQuery(KclmtClosure.class);

		// root data
		Root<KclmtClosure> root = cq.from(KclmtClosure.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.cid), companyId));

		// in closure id
		lstpredicateWhere.add(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.closureId).in(closureIds));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder.asc(root.get(KclmtClosure_.kclmtClosurePK).get(KclmtClosurePK_.closureId)));

		// create query
		TypedQuery<KclmtClosure> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the closure
	 */
	private Closure toDomain(KclmtClosure entity) {
		return new Closure(new JpaClosureGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kclmt closure
	 */
	private KclmtClosure toEntity(Closure domain) {
		KclmtClosure entity = new KclmtClosure();
		domain.saveToMemento(new JpaClosureSetMemento(entity));
		return entity;
	}

	/**
	 * To entity update.
	 *
	 * @param domain
	 *            the domain
	 * @return the kclmt closure
	 */
	private KclmtClosure toEntityUpdate(Closure domain) {
		KclmtClosure entity = new KclmtClosure();
		Optional<KclmtClosure> optionalEntity = this.queryProxy().find(
				new KclmtClosurePK(domain.getCompanyId().v(), domain.getClosureId()),
				KclmtClosure.class);
		if (optionalEntity.isPresent()) {
			entity = optionalEntity.get();
		}
		domain.saveToMemento(new JpaClosureSetMemento(entity));
		return entity;
	}

	

}
