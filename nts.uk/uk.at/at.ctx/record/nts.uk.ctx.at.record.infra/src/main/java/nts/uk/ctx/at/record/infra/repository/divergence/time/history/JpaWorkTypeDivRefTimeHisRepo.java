package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefHistCom_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefHistBus;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefHistBus_;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaWorkTypeDivergenceReferenceTimeHistoryRepository.
 */
@Stateless
public class JpaWorkTypeDivRefTimeHisRepo extends JpaRepository
		implements WorkTypeDivergenceReferenceTimeHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#countByPeriodDate(nts.arc.
	 * time.GeneralDate, nts.arc.time.GeneralDate)
	 */
	@Override
	public Integer countByDatePeriod(String companyId, BusinessTypeCode workTypeCode, DatePeriod datePeriod,
			String histId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		Root<KrcmtDvgcRefHistBus> root = cq.from(KrcmtDvgcRefHistBus.class);

		// Get start date, end Date
		GeneralDate startDate = datePeriod.start();
		GeneralDate endDate = datePeriod.end();

		// Build query
		cq.select(criteriaBuilder.count(root));

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.worktypeCd), workTypeCode.v()));
		if (!StringUtils.isEmpty(histId)) {
			predicates.add(criteriaBuilder.notEqual(root.get(KrcmtDvgcRefHistBus_.histId), histId));
		}

		predicates.add(criteriaBuilder.or(
				criteriaBuilder.between(root.get(KrcmtDvgcRefHistCom_.strD.getName()), startDate, endDate),
				criteriaBuilder.between(root.get(KrcmtDvgcRefHistCom_.endD.getName()), startDate, endDate),
				criteriaBuilder.and(criteriaBuilder.lessThan(root.get(KrcmtDvgcRefHistCom_.strD.getName()), startDate),
						criteriaBuilder.greaterThan(root.get(KrcmtDvgcRefHistCom_.endD.getName()), endDate))));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query count
		return em.createQuery(cq).getSingleResult().intValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode, java.lang.String)
	 */
	@Override
	public WorkTypeDivergenceReferenceTimeHistory findByKey(String histId) {
		KrcmtDvgcRefHistBus worktypeDrtHist = this.queryProxy().find(histId, KrcmtDvgcRefHistBus.class).orElse(null);
		ArrayList<KrcmtDvgcRefHistBus> entities = new ArrayList<>();
		if (worktypeDrtHist != null) {
			entities.add(worktypeDrtHist);
		}

		return this.toDomain(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#findAll(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public WorkTypeDivergenceReferenceTimeHistory findAll(String companyId,
			BusinessTypeCode workTypeCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistBus> cq = criteriaBuilder
				.createQuery(KrcmtDvgcRefHistBus.class);
		Root<KrcmtDvgcRefHistBus> root = cq.from(KrcmtDvgcRefHistBus.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.cid), companyId));
		predicates.add(
				criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.worktypeCd), workTypeCode));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// order by insert date
		cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistBus_.strD)));

		List<KrcmtDvgcRefHistBus> worktypeDrtHists = em.createQuery(cq).getResultList();

		return this.toDomain(worktypeDrtHists);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#add(nts.uk.ctx.at.record.dom
	 * .divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void add(WorkTypeDivergenceReferenceTimeHistory domain) {
		this.commandProxy().insertAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#update(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void update(WorkTypeDivergenceReferenceTimeHistory domain) {
		this.commandProxy().updateAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#delete(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void delete(WorkTypeDivergenceReferenceTimeHistory domain) {
		this.commandProxy().removeAll(this.toEntities(domain));
	}

	@Override
	public WorkTypeDivergenceReferenceTimeHistory findLatestHist(String companyId, BusinessTypeCode workTypeCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistBus> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistBus.class);
		Root<KrcmtDvgcRefHistBus> root = cq.from(KrcmtDvgcRefHistBus.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.worktypeCd), workTypeCode));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistBus_.endD)));

		// query data
		List<KrcmtDvgcRefHistBus> comDrtHists = em.createQuery(cq).setMaxResults(1).getResultList();

		return this.toDomain(comDrtHists);
	}

	@Override
	public WorkTypeDivergenceReferenceTimeHistory findByDate(String companyId, BusinessTypeCode worktypeCode,
			GeneralDate date) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistBus> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistBus.class);
		Root<KrcmtDvgcRefHistBus> root = cq.from(KrcmtDvgcRefHistBus.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.cid), companyId));

		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(KrcmtDvgcRefHistBus_.strD), date));
		predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KrcmtDvgcRefHistBus_.endD), date));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistBus_.endD)));

		// query data
		List<KrcmtDvgcRefHistBus> worktypeDrtHists = em.createQuery(cq).getResultList();

		return this.toDomain(worktypeDrtHists);
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the work type divergence reference time history
	 */
	private WorkTypeDivergenceReferenceTimeHistory toDomain(List<KrcmtDvgcRefHistBus> entities) {
		JpaWorkTypeDivergenceReferenceTimeHistoryGetMemento memento = new JpaWorkTypeDivergenceReferenceTimeHistoryGetMemento(
				entities);
		return new WorkTypeDivergenceReferenceTimeHistory(memento);
	}

	/**
	 * To entities.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KrcmtDvgcRefHistBus> toEntities(WorkTypeDivergenceReferenceTimeHistory domain) {
		List<String> histIds = domain.getHistoryItems().stream().map(item -> item.identifier())
				.collect(Collectors.toList());

		List<KrcmtDvgcRefHistBus> worktypeDrtHists = this.findByCompanyIdAndWorkType(domain.getCId(),
				domain.getWorkTypeCode().v(), histIds);

		JpaWorkTypeDivergenceReferenceTimeHistorySetMemento memento = new JpaWorkTypeDivergenceReferenceTimeHistorySetMemento(
				worktypeDrtHists);

		domain.saveToMemento(memento);

		return worktypeDrtHists;
	}

	/**
	 * Find by company id and work type.
	 *
	 * @param companyId
	 *            the company id
	 * @param workTypeCode
	 *            the work type code
	 * @param histIds
	 *            the hist ids
	 * @return the list
	 */
	private List<KrcmtDvgcRefHistBus> findByCompanyIdAndWorkType(String companyId, String workTypeCode,
			List<String> historyIds) {
		if(CollectionUtil.isEmpty(historyIds)) {
			return Collections.emptyList();
		}
		
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistBus> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistBus.class);
		Root<KrcmtDvgcRefHistBus> root = cq.from(KrcmtDvgcRefHistBus.class);

		// Build query
		cq.select(root);

		List<KrcmtDvgcRefHistBus> worktypeDrtHists = new ArrayList<>();

		CollectionUtil.split(historyIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// create where conditions
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.cid), companyId));
			predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistBus_.worktypeCd), workTypeCode));

			// Find by history id
			predicates.add(root.get(KrcmtDvgcRefHistBus_.histId).in(splitData));

			// add where to query
			cq.where(predicates.toArray(new Predicate[] {}));

			// order by insert date
			cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistBus_.strD)));

			worktypeDrtHists.addAll(em.createQuery(cq).getResultList());
		});

		return worktypeDrtHists;
	}
}
