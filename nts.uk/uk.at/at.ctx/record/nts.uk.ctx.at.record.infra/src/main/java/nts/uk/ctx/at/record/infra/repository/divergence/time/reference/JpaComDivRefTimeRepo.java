package nts.uk.ctx.at.record.infra.repository.divergence.time.reference;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.val;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.history.CompanyDivergenceReferenceTimeRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcRefTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstDrtPK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstDrtPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstDrt_;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class JpaCompanyDivergenceReferenceTimeRepository.
 */
@Stateless
public class JpaComDivRefTimeRepo extends JpaRepository
		implements CompanyDivergenceReferenceTimeRepository {

	/** The Constant DIVERGENCE_TIME_MAX_COUNT. */
	private static final int DIVERGENCE_TIME_MAX_COUNT = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceType)
	 */
	@Override
	public Optional<CompanyDivergenceReferenceTime> findByKey(String histId, Integer divergenceTimeNo) {
		KrcstDrtPK pk = new KrcstDrtPK();
		pk.setHistId(histId);
		pk.setDvgcTimeNo(divergenceTimeNo);

		Optional<KrcmtDvgcRefTime> drt = this.queryProxy().find(pk, KrcmtDvgcRefTime.class);//.orElse(null);
		if(drt == null) return Optional.empty();
		if(!drt.isPresent()) return Optional.empty();
		if(drt.get() == null) return Optional.empty();

		
		val domain = this.toDomain(drt.get());
		
		return Optional.of(domain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<CompanyDivergenceReferenceTime> findAll(String histId) {

		// query data
		List<KrcmtDvgcRefTime> krcstDrts = this.findByHistoryId(histId);

		// return
		return krcstDrts.isEmpty() ? new ArrayList<CompanyDivergenceReferenceTime>()
				: krcstDrts.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#addDefaultDataWhenCreateHistory(java
	 * .lang.String)
	 */
	@Override
	public void addDefaultDataWhenCreateHistory(String historyId) {
		for (int i = 1; i <= DIVERGENCE_TIME_MAX_COUNT; i++) {
			// set value for entity
			KrcmtDvgcRefTime drt = new KrcmtDvgcRefTime();
			drt.setId(new KrcstDrtPK(historyId, i));
			drt.setDvgcTimeUseSet(BigDecimal.valueOf(NotUseAtr.NOT_USE.value));

			// Insert to DB
			this.commandProxy().insert(drt);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void update(List<CompanyDivergenceReferenceTime> listDomain) {
		this.commandProxy()
				.updateAll(listDomain.stream().map(domain -> this.toEntity(domain)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#delete(nts.uk.ctx.at.record.dom.
	 * divergence.time.history.CompanyDivergenceReferenceTime)
	 */
	@Override
	public void delete(CompanyDivergenceReferenceTime domain) {
		this.commandProxy().remove(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#copyDataFromLatestHistory(java.lang.
	 * String, java.lang.String)
	 */
	@Override
	public void copyDataFromLatestHistory(String targetHistId, String destHistId) {
		List<KrcmtDvgcRefTime> targetHistories = this.findByHistoryId(targetHistId);

		targetHistories.forEach(history -> {
			// copy to new entity
			KrcmtDvgcRefTime drt = new KrcmtDvgcRefTime();
			KrcstDrtPK pk = new KrcstDrtPK(destHistId, history.getId().getDvgcTimeNo());
			drt.setId(pk);
			drt.setDvgcTimeUseSet(history.getDvgcTimeUseSet());
			drt.setAlarmTime(history.getAlarmTime());
			drt.setErrorTime(history.getErrorTime());

			// Insert to DB
			this.commandProxy().insert(drt);
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * CompanyDivergenceReferenceTimeRepository#findByHistoryIdAndDivergenceTimeNos(
	 * java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public List<CompanyDivergenceReferenceTime> findByHistoryIdAndDivergenceTimeNos(String historyId,
			List<Integer> divTimeNos) {
		List<KrcmtDvgcRefTime> krcstDrts = this.findByHistoryId(historyId, divTimeNos);

		// return
		return krcstDrts.isEmpty() ? new ArrayList<CompanyDivergenceReferenceTime>()
				: krcstDrts.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the company divergence reference time
	 */
	private CompanyDivergenceReferenceTime toDomain(KrcmtDvgcRefTime entity) {
		JpaCompanyDivergenceReferenceTimeGetMemento memento = new JpaCompanyDivergenceReferenceTimeGetMemento(entity);
		return new CompanyDivergenceReferenceTime(memento);
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst drt
	 */
	private KrcmtDvgcRefTime toEntity(CompanyDivergenceReferenceTime domain) {
		KrcstDrtPK pk = new KrcstDrtPK();
		pk.setHistId(domain.getHistoryId());
		pk.setDvgcTimeNo(domain.getDivergenceTimeNo());

		KrcmtDvgcRefTime entity = this.queryProxy().find(pk, KrcmtDvgcRefTime.class).orElse(new KrcmtDvgcRefTime());
		domain.saveToMemento(new JpaCompanyDivergenceReferenceTimeSetMemento(entity));

		return entity;
	}

	/**
	 * Find by history id.
	 *
	 * @param historyId
	 *            the history id
	 * @param divTimeNos
	 *            the div time nos
	 * @return the list
	 */
	private List<KrcmtDvgcRefTime> findByHistoryId(String historyId, List<Integer> divTimeNos) {
		
		if (CollectionUtil.isEmpty(divTimeNos)) {
			return Collections.emptyList();
		}

		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefTime> cq = criteriaBuilder.createQuery(KrcmtDvgcRefTime.class);
		Root<KrcmtDvgcRefTime> root = cq.from(KrcmtDvgcRefTime.class);

		// Build query
		cq.select(root);

		List<KrcmtDvgcRefTime> krcstDrts = new ArrayList<>();

		CollectionUtil.split(divTimeNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// create where conditions
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get(KrcstDrt_.id).get(KrcstDrtPK_.histId),
					historyId));
			predicates.add(root.get(KrcstDrt_.id).get(KrcstDrtPK_.dvgcTimeNo).in(splitData));

			// add where to query
			cq.where(predicates.toArray(new Predicate[] {}));
			cq.orderBy(criteriaBuilder.asc(root.get(KrcstDrt_.id).get(KrcstDrtPK_.dvgcTimeNo)));

			// query data
			krcstDrts.addAll(em.createQuery(cq).getResultList());
		});

		return krcstDrts;
	}
	
	private List<KrcmtDvgcRefTime> findByHistoryId(String historyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcRefTime> cq = criteriaBuilder.createQuery(KrcmtDvgcRefTime.class);
		Root<KrcmtDvgcRefTime> root = cq.from(KrcmtDvgcRefTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(
				criteriaBuilder.equal(root.get(KrcstDrt_.id).get(KrcstDrtPK_.histId), historyId));
		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.asc(root.get(KrcstDrt_.id).get(KrcstDrtPK_.dvgcTimeNo)));

		// query data
		return em.createQuery(cq).getResultList();
	}
}
