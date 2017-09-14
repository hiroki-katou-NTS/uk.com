/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime.breakdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItem;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository;
import nts.uk.ctx.at.shared.dom.overtime.breakdown.attendance.OvertimeBRDItemAtenRepository;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrd;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrdPK_;
import nts.uk.ctx.at.shared.infra.entity.overtime.breakdown.KshstOverTimeBrd_;

/**
 * The Class JpaOvertimeBRDItemRepository.
 */
@Stateless
public class JpaOvertimeBRDItemRepository extends JpaRepository
		implements OvertimeBRDItemRepository {
	
	/** The repository. */
	@Inject
	private OvertimeBRDItemAtenRepository repository;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<OvertimeBRDItem> overtimeBreakdownItems, String companyId) {

		// to map over time break down item
		Map<BreakdownItemNo, OvertimeBRDItem> mapOvertimeBRDItem = this.findAll(companyId).stream()
				.collect(Collectors.toMap((overtime) -> {
					return overtime.getBreakdownItemNo();
				}, Function.identity()));

		// entity add all
		List<KshstOverTimeBrd> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshstOverTimeBrd> entityUpdateAll = new ArrayList<>();

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

		// save all aten
		overtimeBreakdownItems
				.forEach(domain -> this.repository.saveAll(domain.getAttendanceItemIds(), companyId,
						domain.getBreakdownItemNo().value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.breakdown.OvertimeBRDItemRepository#
	 * findAll(java.lang.String)
	 */
	@Override
	public List<OvertimeBRDItem> findAll(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshstOverTimeBrd> cq = criteriaBuilder.createQuery(KshstOverTimeBrd.class);

		// root data
		Root<KshstOverTimeBrd> root = cq.from(KshstOverTimeBrd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTimeBrd_.kshstOverTimeBrdPK).get(KshstOverTimeBrdPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time break down item no asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KshstOverTimeBrd_.kshstOverTimeBrdPK).get(KshstOverTimeBrdPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOverTimeBrd> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream()
				.map(entity -> this.toDomain(entity,
						this.repository.findAll(companyId,
								entity.getKshstOverTimeBrdPK().getBrdItemNo())))
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
	public List<OvertimeBRDItem> findAllUse(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_BRD (KshstOverTimeBrd SQL)
		CriteriaQuery<KshstOverTimeBrd> cq = criteriaBuilder.createQuery(KshstOverTimeBrd.class);

		// root data
		Root<KshstOverTimeBrd> root = cq.from(KshstOverTimeBrd.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTimeBrd_.kshstOverTimeBrdPK).get(KshstOverTimeBrdPK_.cid),
				companyId));

		// equal use classification
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshstOverTimeBrd_.useAtr),
				UseClassification.UseClass_Use.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time break down item no asc
		cq.orderBy(criteriaBuilder.asc(
				root.get(KshstOverTimeBrd_.kshstOverTimeBrdPK).get(KshstOverTimeBrdPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOverTimeBrd> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream()
				.map(entity -> this.toDomain(entity,
						this.repository.findAll(companyId,
								entity.getKshstOverTimeBrdPK().getBrdItemNo())))
				.collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the overtime BRD item
	 */
	private OvertimeBRDItem toDomain(KshstOverTimeBrd entity, List<Integer> entityAtens){
		return new OvertimeBRDItem(new JpaOvertimeBRDItemGetMemento(entity,entityAtens));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst over time brd
	 */
	private KshstOverTimeBrd toEntity(OvertimeBRDItem domain,String companyId) {
		KshstOverTimeBrd entity = new KshstOverTimeBrd();
		domain.saveToMemento(new JpaOvertimeBRDItemSetMemento(entity, new ArrayList<>(), companyId));
		return entity;
	}

	
	

}
