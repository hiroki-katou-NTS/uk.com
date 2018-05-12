/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.workfixed;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixed;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixedPK;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixedPK_;
import nts.uk.ctx.at.record.infra.entity.workrecord.workfixed.KrcstWorkFixed_;

/**
 * The Class JpaWorkfixedRepository.
 */
@Stateless
public class JpaWorkfixedRepository extends JpaRepository implements WorkfixedRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#remove(
	 * java.lang.String, java.lang.Integer)
	 */
	@Override
	public void remove(String workPlaceId, Integer closureId, String cid) {
		this.commandProxy().remove(KrcstWorkFixed.class, new KrcstWorkFixedPK(workPlaceId, closureId, cid));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#add(nts
	 * .uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed)
	 */
	@Override
	public void add(WorkFixed workFixed) {
		KrcstWorkFixed entity = new KrcstWorkFixed();
		workFixed.saveToMemento(new JpaWorkFixedSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#
	 * findByWorkPlaceIdAndClosureId(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Optional<WorkFixed> findByWorkPlaceIdAndClosureId(String workPlaceId, Integer closureId, String cid) {
		Optional<KrcstWorkFixed> optional = this.queryProxy().find(new KrcstWorkFixedPK(workPlaceId, closureId, cid),
				KrcstWorkFixed.class);

		if (optional.isPresent()) {
			return Optional.ofNullable(new WorkFixed(new JpaWorkfixedGetMemento(optional.get())));
		}

		return Optional.empty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#update(
	 * nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed)
	 */
	@Override
	public void update(WorkFixed workFixed) {
		Optional<KrcstWorkFixed> optional = this.queryProxy().find(
				new KrcstWorkFixedPK(workFixed.getWkpId(), workFixed.getClosureId(), workFixed.getCid()),
				KrcstWorkFixed.class);
		KrcstWorkFixed entity = null;
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KrcstWorkFixed();
		}
		workFixed.saveToMemento(new JpaWorkFixedSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#
	 * findWorkFixedByWorkPlaceId(java.lang.String, java.util.List,
	 * java.util.List)
	 */
	@Override
	public List<WorkFixed> findWorkFixed(String cid) {

		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstWorkFixed> cq = criteriaBuilder.createQuery(KrcstWorkFixed.class);
		Root<KrcstWorkFixed> root = cq.from(KrcstWorkFixed.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KrcstWorkFixed_.krcstWorkFixedPK).get(KrcstWorkFixedPK_.cid), cid));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		List<KrcstWorkFixed> listKrcstWorkFixed = em.createQuery(cq).getResultList();

		// return data
		return listKrcstWorkFixed.stream()
				.map(item -> new WorkFixed(new JpaWorkfixedGetMemento(item)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository#find(
	 * java.lang.String, nts.arc.time.YearMonth, java.lang.String,
	 * java.lang.Integer)
	 */
	@Override
	public Optional<WorkFixed> find(String cid, String wkpId, Integer closureId, YearMonth processYm) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstWorkFixed> cq = criteriaBuilder.createQuery(KrcstWorkFixed.class);
		Root<KrcstWorkFixed> root = cq.from(KrcstWorkFixed.class);

		// Build query
		cq.select(root);

		// Add where conditions
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KrcstWorkFixed_.krcstWorkFixedPK).get(KrcstWorkFixedPK_.cid), cid));
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KrcstWorkFixed_.krcstWorkFixedPK).get(KrcstWorkFixedPK_.wkpid), wkpId));
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KrcstWorkFixed_.krcstWorkFixedPK).get(KrcstWorkFixedPK_.closureId), closureId));
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KrcstWorkFixed_.processYm), processYm.v()));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		List<KrcstWorkFixed> listKrcstWorkFixed = em.createQuery(cq).getResultList();

		if (CollectionUtil.isEmpty(listKrcstWorkFixed)) {
			return Optional.empty();
		}

		return Optional.ofNullable(new WorkFixed(new JpaWorkfixedGetMemento(listKrcstWorkFixed.get(0))));

	}

}
