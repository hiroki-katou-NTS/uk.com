/*
 * 
 */
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
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeHistorySetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefHistCom;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefHistCom_;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class JpaCompanyDivergenceReferenceTimeHistoryRepository.
 */
@Stateless
public class JpaComDivRefTimeHisRepo extends JpaRepository
		implements CompanyDivergenceReferenceTimeHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#findbyPeriodDate(nts.arc.time
	 * .GeneralDate, nts.arc.time.GeneralDate)
	 */
	@Override
	public Integer countByDatePeriod(String companyId, DatePeriod datePeriod, String histId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		Root<KrcmtDvgcRefHistCom> root = cq.from(KrcmtDvgcRefHistCom.class);

		// Get start date, end Date
		GeneralDate startDate = datePeriod.start();
		GeneralDate endDate = datePeriod.end();

		// Build query
		cq.select(criteriaBuilder.count(root));

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistCom_.cid), companyId));
		if (!StringUtils.isEmpty(histId)) {
			predicates.add(criteriaBuilder.notEqual(root.get(KrcmtDvgcRefHistCom_.histId), histId));
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
	 * CompanyDivergenceReferenceTimeHistoryRepository#findByHistId(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public CompanyDivergenceReferenceTimeHistory findByHistId(String histId) {
		KrcmtDvgcRefHistCom krcmtDvgcRefHistCom = this.queryProxy().find(histId, KrcmtDvgcRefHistCom.class).orElse(null);
		ArrayList<KrcmtDvgcRefHistCom> entities = new ArrayList<>();

		if (krcmtDvgcRefHistCom != null) {
			entities.add(krcmtDvgcRefHistCom);
		}

		return this.toDomain(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#findAll(java.lang.String)
	 */
	@Override
	public CompanyDivergenceReferenceTimeHistory findAll(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistCom> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistCom.class);
		Root<KrcmtDvgcRefHistCom> root = cq.from(KrcmtDvgcRefHistCom.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistCom_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// order by insert date
		cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistCom_.strD)));

		// return
		return this.toDomain(em.createQuery(cq).getResultList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#add(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTimeHistory)
	 */
	@Override
	public void add(CompanyDivergenceReferenceTimeHistory domain) {
		this.commandProxy().insertAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#update(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory)
	 */
	@Override
	public void update(CompanyDivergenceReferenceTimeHistory domain) {
		this.commandProxy().updateAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#delete(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.CompanyDivergenceReferenceTimeHistory)
	 */
	@Override
	public void delete(CompanyDivergenceReferenceTimeHistory domain) {
		this.commandProxy().removeAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#findLatestHist(java.lang.
	 * String)
	 */
	@Override
	public CompanyDivergenceReferenceTimeHistory findLatestHist(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistCom> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistCom.class);
		Root<KrcmtDvgcRefHistCom> root = cq.from(KrcmtDvgcRefHistCom.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistCom_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistCom_.endD)));

		// query data
		List<KrcmtDvgcRefHistCom> comDrtHists = em.createQuery(cq).setMaxResults(1).getResultList();

		return this.toDomain(comDrtHists);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeHistoryRepository#findByDatePeriod(java.lang.
	 * String, nts.arc.time.calendar.period.DatePeriod, java.lang.String)
	 */
	@Override
	public CompanyDivergenceReferenceTimeHistory findByDate(String companyId, GeneralDate date) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistCom> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistCom.class);
		Root<KrcmtDvgcRefHistCom> root = cq.from(KrcmtDvgcRefHistCom.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistCom_.cid), companyId));

		predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(KrcmtDvgcRefHistCom_.strD), date));
		predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KrcmtDvgcRefHistCom_.endD), date));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistCom_.endD)));

		// query data
		List<KrcmtDvgcRefHistCom> comDrtHists = em.createQuery(cq).getResultList();

		return this.toDomain(comDrtHists);
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the company divergence reference time history
	 */
	private CompanyDivergenceReferenceTimeHistory toDomain(List<KrcmtDvgcRefHistCom> entities) {
		CompanyDivergenceReferenceTimeHistoryGetMemento memento = new JpaCompanyDivergenceReferenceTimeHistoryGetMemento(
				entities);
		return new CompanyDivergenceReferenceTimeHistory(memento);
	}

	/**
	 * To entities.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KrcmtDvgcRefHistCom> toEntities(CompanyDivergenceReferenceTimeHistory domain) {
		List<String> histIds = domain.getHistoryItems().stream().map(item -> item.identifier())
				.collect(Collectors.toList());

		List<KrcmtDvgcRefHistCom> comDrtHists = this.findByCompanyId(domain.getCId(), histIds);

		CompanyDivergenceReferenceTimeHistorySetMemento memento = new JpaCompanyDivergenceReferenceTimeHistorySetMemento(
				comDrtHists);

		domain.saveToMemento(memento);

		return comDrtHists;
	}

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @param histIds
	 *            the hist ids
	 * @return the list
	 */
	private List<KrcmtDvgcRefHistCom> findByCompanyId(String companyId, List<String> historyIds) {
		
		if (CollectionUtil.isEmpty(historyIds)) {
			return Collections.emptyList();
		}

		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefHistCom> cq = criteriaBuilder.createQuery(KrcmtDvgcRefHistCom.class);
		Root<KrcmtDvgcRefHistCom> root = cq.from(KrcmtDvgcRefHistCom.class);

		// Build query
		cq.select(root);

		List<KrcmtDvgcRefHistCom> comDrtHists = new ArrayList<>();

		CollectionUtil.split(historyIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// create where conditions
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcRefHistCom_.cid), companyId));

			// Find by history id
			predicates.add(root.get(KrcmtDvgcRefHistCom_.histId).in(splitData));

			// add where to query
			cq.where(predicates.toArray(new Predicate[] {}));

			// order by insert date
			cq.orderBy(criteriaBuilder.desc(root.get(KrcmtDvgcRefHistCom_.strD)));
			comDrtHists.addAll(em.createQuery(cq).getResultList());
		});

		return comDrtHists;
	}
}
