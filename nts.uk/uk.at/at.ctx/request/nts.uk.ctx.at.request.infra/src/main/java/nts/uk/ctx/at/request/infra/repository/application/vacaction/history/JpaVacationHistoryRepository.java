/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.infra.repository.application.vacaction.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistory;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistoryPK;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistoryPK_;
import nts.uk.ctx.at.request.infra.entity.valication.history.KrqmtVacationHistory_;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaVacationHistoryRepository.
 */
@Stateless
public class JpaVacationHistoryRepository extends JpaRepository implements VacationHistoryRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository#findByWorkTypeCode(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PlanVacationHistory> findByWorkTypeCode(String companyId, String workTypeCode) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_CLOSURE (KshmtClosure SQL)
		CriteriaQuery<KrqmtVacationHistory> cq = criteriaBuilder.createQuery(KrqmtVacationHistory.class);

		// root data
		Root<KrqmtVacationHistory> root = cq.from(KrqmtVacationHistory.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.worktypeCd),
				workTypeCode));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder.desc(root.get(KrqmtVacationHistory_.startDate)));

		List<PlanVacationHistory> lstHist = new ArrayList<>();
		// exclude select
		lstHist = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());

		//return
		return lstHist;
	}
	
	@Override
	public List<PlanVacationHistory> findHistory(String companyId, String historyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_CLOSURE (KshmtClosure SQL)
		CriteriaQuery<KrqmtVacationHistory> cq = criteriaBuilder.createQuery(KrqmtVacationHistory.class);

		// root data
		Root<KrqmtVacationHistory> root = cq.from(KrqmtVacationHistory.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.historyId),
				historyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by startDate desc
		cq.orderBy(criteriaBuilder.desc(root.get(KrqmtVacationHistory_.startDate)));

		// exclude select
		List<PlanVacationHistory> lstHist = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(lstHist)){
			return Collections.emptyList();
		}
		//return 
		return lstHist;
	}
	
	@Override
	public List<PlanVacationHistory> findHistoryByBaseDate(String companyId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_CLOSURE (KshmtClosure SQL)
		CriteriaQuery<KrqmtVacationHistory> cq = criteriaBuilder.createQuery(KrqmtVacationHistory.class);

		// root data
		Root<KrqmtVacationHistory> root = cq.from(KrqmtVacationHistory.class);
		
		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		
		GeneralDate start = GeneralDate.fromString(String.valueOf(baseDate.year()) + "/12/31", "yyyy/MM/dd");
		GeneralDate end = GeneralDate.fromString(String.valueOf(baseDate.year()) + "/01/01", "yyyy/MM/dd");

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.cid), companyId));
		
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(root.get(KrqmtVacationHistory_.startDate), start));
		
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KrqmtVacationHistory_.endDate), end));
		
		
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		
		List<Order> orders = new ArrayList<>();
		//Sortby StartDate and WorkTypeCode
		orders.add(criteriaBuilder.asc(root.get(KrqmtVacationHistory_.startDate)));
		orders.add(criteriaBuilder.asc(root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.worktypeCd)));
		
		//order
		cq.orderBy(orders);

		// exclude select
		List<PlanVacationHistory> lstHist = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
		
		if (CollectionUtil.isEmpty(lstHist)){
			return Collections.emptyList();
		}
		//return 
		return lstHist;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository#countByDatePeriod(java.lang.String, java.lang.String, nts.arc.time.calendar.period.DatePeriod, java.lang.String)
	 */
	@Override
	public Integer countByDatePeriod(String companyId, String workTypeCode, DatePeriod datePeriod, String histId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		Root<KrqmtVacationHistory> root = cq.from(KrqmtVacationHistory.class);

		// Get start date, end Date
		GeneralDate startDate = datePeriod.start();
		GeneralDate endDate = datePeriod.end();

		// Build query
		cq.select(criteriaBuilder.count(root));

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.worktypeCd),
				workTypeCode));
		if (!StringUtils.isEmpty(histId)) {
			predicates.add(criteriaBuilder.notEqual(
					root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.historyId),
					histId));
		}

		predicates.add(criteriaBuilder.or(
				criteriaBuilder.between(root.get(KrqmtVacationHistory_.startDate.getName()), startDate, endDate),
				criteriaBuilder.between(root.get(KrqmtVacationHistory_.endDate.getName()), startDate, endDate),
				criteriaBuilder.and(
						criteriaBuilder.lessThan(root.get(KrqmtVacationHistory_.startDate.getName()), startDate),
						criteriaBuilder.greaterThan(root.get(KrqmtVacationHistory_.endDate.getName()), endDate))));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query count
		return em.createQuery(cq).getSingleResult().intValue();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository#add(nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory)
	 */
	@Override
	public void add(PlanVacationHistory vacationHistory) {
		this.commandProxy().insert(toEntity(vacationHistory));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository#update(nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory)
	 */
	@Override
	public void update(PlanVacationHistory vacationHistory) {
		this.commandProxy().update(toEntity(vacationHistory));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository#removeWkpConfigHist(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeVacationHistory(String companyId, String historyId, String workTypeCode){
		this.commandProxy().remove(KrqmtVacationHistory.class, new KrqmtVacationHistoryPK(companyId, historyId, workTypeCode));
	}

	/**
	 * To domain.
	 *
	 * @param item the item
	 * @return the plan vacation history
	 */
	private PlanVacationHistory toDomain(KrqmtVacationHistory item) {
		return new PlanVacationHistory(new JpaPlanVacationHistoryGetMemento(item));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krqmt vacation history
	 */
	private KrqmtVacationHistory toEntity(PlanVacationHistory domain) {
		KrqmtVacationHistoryPK pk = new KrqmtVacationHistoryPK(domain.getCompanyId(), domain.identifier(),
				domain.getWorkTypeCode());
		KrqmtVacationHistory entity = this.queryProxy().find(pk, KrqmtVacationHistory.class)
				.orElse(new KrqmtVacationHistory());
		entity.setKrqmtVacationHistoryPK(pk);
		entity.setStartDate(domain.span().start());
		entity.setEndDate(domain.span().end());
		entity.setMaxDay(domain.getMaxDay().v());
		return entity;
	}

	@Override
	public List<PlanVacationHistory> findByWorkTypeAndPeriod(String cid, String workTypeCode, DatePeriod dateData) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHMT_CLOSURE (KshmtClosure SQL)
		CriteriaQuery<KrqmtVacationHistory> cq = criteriaBuilder.createQuery(KrqmtVacationHistory.class);

		// root data
		Root<KrqmtVacationHistory> root = cq.from(KrqmtVacationHistory.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.cid), cid));

		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KrqmtVacationHistory_.krqmtVacationHistoryPK).get(KrqmtVacationHistoryPK_.worktypeCd),
				workTypeCode));
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(
				root.get(KrqmtVacationHistory_.endDate),
				dateData.start()));
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(
				root.get(KrqmtVacationHistory_.startDate),
				dateData.end()));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by closure id asc
		cq.orderBy(criteriaBuilder.desc(root.get(KrqmtVacationHistory_.startDate)));

		List<PlanVacationHistory> lstHist = new ArrayList<>();
		// exclude select
		lstHist = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());

		//return
		return lstHist;
	}
}
