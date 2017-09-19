/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime;

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
import nts.uk.ctx.at.shared.dom.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeRepository;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTimePK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.overtime.KshstOverTime_;

/**
 * The Class JpaOvertimeRepository.
 */
@Stateless
public class JpaOvertimeRepository extends JpaRepository implements OvertimeRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<Overtime> findAll(String companyId) {
		return this.findAllEntity(companyId).stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeRepository#saveAll(java.util.
	 * List, java.lang.String)
	 */
	@Override
	public void saveAll(List<Overtime> overtimes, String companyId) {
		
		// to map over time
		Map<OvertimeNo, Overtime> mapOvertime = this.findAll(companyId).stream()
				.collect(Collectors.toMap((overtime) -> {
					return overtime.getOvertimeNo();
				}, Function.identity()));
		
		// entity add all
		List<KshstOverTime> entityAddAll = new ArrayList<>();
		
		// entity update all
		List<KshstOverTime> entityUpdateAll = new ArrayList<>();
		
		
		// for each data overtime
		overtimes.forEach(overtime->{
			if (mapOvertime.containsKey(overtime.getOvertimeNo())) {
				entityUpdateAll.add(this.toEntity(overtime, companyId));
			} else {
				entityAddAll.add(this.toEntity(overtime, companyId));
			}
		});
		
		// insert all
		this.commandProxy().insertAll(entityAddAll);
		
		// update all
		this.commandProxy().updateAll(entityUpdateAll);
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the monthly pattern
	 */
	private Overtime toDomain(KshstOverTime entity){
		return new Overtime(new JpaOvertimeGetMemento(entity));
	}
	
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kmpst month pattern
	 */
	private KshstOverTime toEntity(Overtime domain, String companyId) {
		KshstOverTime entity = new KshstOverTime();
		domain.saveToMemento(new JpaOvertimeSetMemento(entity, companyId));
		return entity;
	}
	
	/**
	 * Find all entity.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	private List<KshstOverTime> findAllEntity(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME (KshstOverTime SQL)
		CriteriaQuery<KshstOverTime> cq = criteriaBuilder.createQuery(KshstOverTime.class);

		// root data
		Root<KshstOverTime> root = cq.from(KshstOverTime.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.cid), companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time no asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.overTimeNo)));

		// create query
		TypedQuery<KshstOverTime> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}


	@Override
	public List<Overtime> findAllUse(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME (KshstOverTime SQL)
		CriteriaQuery<KshstOverTime> cq = criteriaBuilder.createQuery(KshstOverTime.class);

		// root data
		Root<KshstOverTime> root = cq.from(KshstOverTime.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.cid), companyId));
		
		// equal Use Classification use
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshstOverTime_.useAtr),
				UseClassification.UseClass_Use.value));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time no asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KshstOverTime_.kshstOverTimePK).get(KshstOverTimePK_.overTimeNo)));

		// create query
		TypedQuery<KshstOverTime> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}

	
}
