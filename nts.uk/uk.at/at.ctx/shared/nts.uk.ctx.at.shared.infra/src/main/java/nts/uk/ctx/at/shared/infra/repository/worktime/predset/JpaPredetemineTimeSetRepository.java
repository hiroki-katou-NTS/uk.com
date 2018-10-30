/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet_;

/**
 * The Class JpaPredetemineTimeSetRepository.
 */
@Stateless
public class JpaPredetemineTimeSetRepository extends JpaRepository implements PredetemineTimeSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByWorkTimeCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<PredetemineTimeSetting> findByWorkTimeCode(String companyId, String workTimeCode) {

		// Query
		Optional<KshmtPredTimeSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtPredTimeSetPK(companyId, workTimeCode), KshmtPredTimeSet.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(
				new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(optionalEntityTimeSet.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#save(nts.uk.ctx.at.shared.dom.worktime.
	 * predset.PredetemineTimeSetting)
	 */
	@Override
	public void add(PredetemineTimeSetting domain) {
		KshmtPredTimeSet entity = new KshmtPredTimeSet();
		domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity));
		this.commandProxy().insert(entity);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#update(nts.uk.ctx.at.shared.dom.worktime
	 * .predset.PredetemineTimeSetting)
	 */
	@Override
	public void update(PredetemineTimeSetting domain) {
		// Query
		Optional<KshmtPredTimeSet> optionalEntityTimeSet = this.queryProxy().find(
				new KshmtPredTimeSetPK(domain.getCompanyId(), domain.getWorkTimeCode().v()), KshmtPredTimeSet.class);
		KshmtPredTimeSet entity = optionalEntityTimeSet.get();
		domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity));
		this.commandProxy().update(entity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#remove(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtPredTimeSet.class, new KshmtPredTimeSetPK(companyId, workTimeCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByStart(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public List<PredetemineTimeSetting> findByStart(String companyID, List<String> workTimeCodes, int startClock) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder predCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtPredTimeSet> predCquery = predCb.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> predRoot = predCquery.from(KshmtPredTimeSet.class);
		ListJoin<KshmtPredTimeSet, KshmtWorkTimeSheetSet> joinRoot = predRoot
				.join(KshmtPredTimeSet_.kshmtWorkTimeSheetSets, JoinType.LEFT);
		// select root
		predCquery.select(predRoot);

		List<KshmtPredTimeSet> resultList = new ArrayList<>();

		// split list worktimecode
		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add predicates
			List<Predicate> predTimePredicates = new ArrayList<>();
			predTimePredicates.add(predCb
					.equal(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyID));
			predTimePredicates.add(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK)
					.get(KshmtPredTimeSetPK_.worktimeCd).in(subList));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.useAtr), UseAtr.USE.value));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.startTime), startClock));

			// set condition
			predCquery.where(predTimePredicates.toArray(new Predicate[] {}));

			// add all to resultList
			resultList.addAll(em.createQuery(predCquery).getResultList());
		});

		// get results
		return resultList.stream()
				.map(entity -> new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByEnd(java.lang.String,
	 * java.util.List, int)
	 */
	@Override
	public List<PredetemineTimeSetting> findByEnd(String companyID, List<String> workTimeCodes, int endClock) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder predCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtPredTimeSet> predCquery = predCb.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> predRoot = predCquery.from(KshmtPredTimeSet.class);
		ListJoin<KshmtPredTimeSet, KshmtWorkTimeSheetSet> joinRoot = predRoot
				.join(KshmtPredTimeSet_.kshmtWorkTimeSheetSets, JoinType.LEFT);
		// select root
		predCquery.select(predRoot);

		List<KshmtPredTimeSet> resultList = new ArrayList<>();

		// split list worktimecode
		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add predicates
			List<Predicate> predTimePredicates = new ArrayList<>();
			predTimePredicates.add(predCb
					.equal(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyID));
			predTimePredicates.add(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK)
					.get(KshmtPredTimeSetPK_.worktimeCd).in(subList));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.useAtr), UseAtr.USE.value));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.endTime), endClock));

			// set condition
			predCquery.where(predTimePredicates.toArray(new Predicate[] {}));

			// add all to resultList
			resultList.addAll(em.createQuery(predCquery).getResultList());
		});

		// get results
		return resultList.stream()
				.map(entity -> new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByStartAndEnd(java.lang.String,
	 * java.util.List, int, int)
	 */
	@Override
	public List<PredetemineTimeSetting> findByStartAndEnd(String companyID, List<String> workTimeCodes, int startClock,
			int endClock) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder predCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtPredTimeSet> predCquery = predCb.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> predRoot = predCquery.from(KshmtPredTimeSet.class);
		ListJoin<KshmtPredTimeSet, KshmtWorkTimeSheetSet> joinRoot = predRoot
				.join(KshmtPredTimeSet_.kshmtWorkTimeSheetSets, JoinType.LEFT);
		// select root
		predCquery.select(predRoot);

		List<KshmtPredTimeSet> resultList = new ArrayList<>();

		// split list worktimecode
		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add predicates
			List<Predicate> predTimePredicates = new ArrayList<>();
			predTimePredicates.add(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK)
					.get(KshmtPredTimeSetPK_.worktimeCd).in(subList));
			predTimePredicates.add(predCb
					.equal(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyID));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.useAtr), UseAtr.USE.value));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.startTime), startClock));
			predTimePredicates.add(predCb.equal(joinRoot.get(KshmtWorkTimeSheetSet_.endTime), endClock));

			// set condition
			predCquery.where(predTimePredicates.toArray(new Predicate[] {}));

			// add all to resultList
			resultList.addAll(em.createQuery(predCquery).getResultList());
		});

		// get results
		return resultList.stream()
				.map(entity -> new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByCompanyID(java.lang.String)
	 */
	@Override
	public List<PredetemineTimeSetting> findByCompanyID(String companyID) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder predCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtPredTimeSet> predCquery = predCb.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> predRoot = predCquery.from(KshmtPredTimeSet.class);

		// select root
		predCquery.select(predRoot);

		// add predicates
		List<Predicate> predTimePredicates = new ArrayList<>();

		// predTime predicates
		predTimePredicates.add(predCb
				.equal(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyID));

		// set condition
		predCquery.where(predTimePredicates.toArray(new Predicate[] {}));

		// get results
		return em.createQuery(predCquery).getResultList().stream()
				.map(entity -> new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#findByCodeList(java.lang.String,
	 * java.util.List)
	 */
	@Override
	public List<PredetemineTimeSetting> findByCodeList(String companyID,
			List<String> worktimeCodes) {
		if (CollectionUtil.isEmpty(worktimeCodes)) {
			return Collections.emptyList();
		}
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder predCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtPredTimeSet> predCquery = predCb.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> predRoot = predCquery.from(KshmtPredTimeSet.class);

		// select root
		predCquery.select(predRoot);

		List<KshmtPredTimeSet> result = new ArrayList<>();

		// split list worktimecode
		CollectionUtil.split(worktimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			// add predicates
			List<Predicate> predTimePredicates = new ArrayList<>();

			// predTime predicates
			predTimePredicates.add(predCb.equal(
					predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid),
					companyID));
			predTimePredicates.add(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK)
					.get(KshmtPredTimeSetPK_.worktimeCd).in(subList));

			// set condition
			predCquery.where(predTimePredicates.toArray(new Predicate[] {}));

			result.addAll(em.createQuery(predCquery).getResultList());
		});

		// get results
		return result.stream()
				.map(entity -> new PredetemineTimeSetting(
						new JpaPredetemineTimeSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}
}
