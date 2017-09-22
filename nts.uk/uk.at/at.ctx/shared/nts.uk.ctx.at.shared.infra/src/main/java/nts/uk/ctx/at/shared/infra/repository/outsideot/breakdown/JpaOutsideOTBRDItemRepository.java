/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItemRepository;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrd;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrdPK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.KshstOutsideOtBrd_;

/**
 * The Class JpaOutsideOTBRDItemRepository.
 */
@Stateless
public class JpaOutsideOTBRDItemRepository extends JpaRepository
		implements OutsideOTBRDItemRepository {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<OutsideOTBRDItem> overtimeBreakdownItems, String companyId) {

		// to map over time break down item
		Map<BreakdownItemNo, OutsideOTBRDItem> mapOvertimeBRDItem = this.findAll(companyId).stream()
				.collect(Collectors.toMap((overtime) -> {
					return overtime.getBreakdownItemNo();
				}, Function.identity()));

		// entity add all
		List<KshstOutsideOtBrd> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshstOutsideOtBrd> entityUpdateAll = new ArrayList<>();

		// for each data overtime
		overtimeBreakdownItems.forEach(overtimeBRDItem -> {
			if (mapOvertimeBRDItem.containsKey(overtimeBRDItem.getBreakdownItemNo())) {
				entityUpdateAll.add(this.toEntity(overtimeBRDItem, companyId));
			} else {
				entityAddAll.add(this.toEntity(overtimeBRDItem, companyId));
			}
		});

		// insert all
		this.commandProxy().insertAll(entityAddAll);

		// update all
		this.commandProxy().updateAll(entityUpdateAll);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * findAll(java.lang.String)
	 */
	@Override
	public List<OutsideOTBRDItem> findAll(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshstOutsideOtBrd> cq = criteriaBuilder.createQuery(KshstOutsideOtBrd.class);

		// root data
		Root<KshstOutsideOtBrd> root = cq.from(KshstOutsideOtBrd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK).get(KshstOutsideOtBrdPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time break down item no asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK).get(KshstOutsideOtBrdPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOutsideOtBrd> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * findAllUse(java.lang.String)
	 */
	@Override
	public List<OutsideOTBRDItem> findAllUse(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshstOutsideOtBrd> cq = criteriaBuilder.createQuery(KshstOutsideOtBrd.class);

		// root data
		Root<KshstOutsideOtBrd> root = cq.from(KshstOutsideOtBrd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK).get(KshstOutsideOtBrdPK_.cid),
				companyId));

		// equal use classification
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshstOutsideOtBrd_.useAtr),
				UseClassification.UseClass_Use.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time break down item no asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KshstOutsideOtBrd_.kshstOutsideOtBrdPK).get(KshstOutsideOtBrdPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOutsideOtBrd> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the outside OTBRD item
	 */
	private OutsideOTBRDItem toDomain(KshstOutsideOtBrd entity) {
		return new OutsideOTBRDItem(new JpaOutsideOTBRDItemGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @param companyId the company id
	 * @return the kshst outside ot brd
	 */
	private KshstOutsideOtBrd toEntity(OutsideOTBRDItem domain,String companyId) {
		KshstOutsideOtBrd entity = new KshstOutsideOtBrd();
		domain.saveToMemento(new JpaOutsideOTBRDItemSetMemento(entity, companyId));
		return entity;
	}
	

}
