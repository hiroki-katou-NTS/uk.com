/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtPredTimeSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWorkTimeSheetSetPK_;
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
	public PredetemineTimeSetting findByWorkTimeCode(String companyId, String workTimeCode) {
		// get entity manager
		EntityManager em1 = this.getEntityManager();
		CriteriaBuilder criteriaBuilder1 = em1.getCriteriaBuilder();

		EntityManager em2 = this.getEntityManager();
		CriteriaBuilder criteriaBuilder2 = em2.getCriteriaBuilder();

		CriteriaQuery<KshmtPredTimeSet> cq = criteriaBuilder1.createQuery(KshmtPredTimeSet.class);
		Root<KshmtPredTimeSet> root = cq.from(KshmtPredTimeSet.class);

		CriteriaQuery<KshmtWorkTimeSheetSet> cq2 = criteriaBuilder2.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> root2 = cq2.from(KshmtWorkTimeSheetSet.class);

		cq.select(root);
		cq2.select(root2);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder1
				.equal(root.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyId));

		lstpredicateWhere.add(criteriaBuilder1.equal(
				root.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.worktimeCd), workTimeCode));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		KshmtPredTimeSet kwtstWorkTimeSet = em1.createQuery(cq).getSingleResult();

		// +++++++++++++++++++++++++++++++++++
		List<Predicate> lstpredicateWhere2 = new ArrayList<>();
		lstpredicateWhere2.add(criteriaBuilder2.equal(
				root2.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyId));

		lstpredicateWhere2.add(criteriaBuilder2.equal(
				root2.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.worktimeCd),
				workTimeCode));

		cq2.where(lstpredicateWhere2.toArray(new Predicate[] {}));

		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em2.createQuery(cq2).getResultList();

		return new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(kwtstWorkTimeSet, lstKshmtWorkTimeSheetSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingRepository#save(nts.uk.ctx.at.shared.dom.worktime.
	 * predset.PredetemineTimeSetting)
	 */
	@Override
	public void save(PredetemineTimeSetting domain) {
		KshmtPredTimeSet entity = new KshmtPredTimeSet();
		List<KshmtWorkTimeSheetSet> lstEntityTime = new ArrayList<>();
		domain.saveToMemento(new JpaPredetemineTimeSettingSetMemento(entity, lstEntityTime));
		this.commandProxy().update(entity);
		this.commandProxy().updateAll(lstEntityTime);
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
		// check empty list workTimeCodes
		if (CollectionUtil.isEmpty(workTimeCodes)) {
			return Collections.emptyList();
		}

		// get worktime sheet map
		Map<String, List<KshmtWorkTimeSheetSet>> sheetMap = this.findWorktimeSheetByStart(companyID, workTimeCodes,
				startClock);

		// get filtered worktime codes
		Set<String> filteredCodes = sheetMap.keySet();

		// get list PredTimeSet
		List<KshmtPredTimeSet> kwtstWorkTimeSets = this.findByListCodes(companyID, filteredCodes);

		// return mapped list
		return this.mapPredTimeWithWorktimeSheet(kwtstWorkTimeSets, sheetMap);
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
		// check empty list workTimeCodes
		if (CollectionUtil.isEmpty(workTimeCodes)) {
			return Collections.emptyList();
		}

		// get worktime sheet map
		Map<String, List<KshmtWorkTimeSheetSet>> sheetMap = this.findWorktimeSheetByEnd(companyID, workTimeCodes,
				endClock);

		// get filtered worktime codes
		Set<String> filteredCodes = sheetMap.keySet();

		// get list PredTimeSet
		List<KshmtPredTimeSet> kwtstWorkTimeSets = this.findByListCodes(companyID, filteredCodes);

		// return mapped list
		return this.mapPredTimeWithWorktimeSheet(kwtstWorkTimeSets, sheetMap);
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
		// check empty list workTimeCodes
		if (CollectionUtil.isEmpty(workTimeCodes)) {
			return Collections.emptyList();
		}

		// get worktime sheet map
		Map<String, List<KshmtWorkTimeSheetSet>> sheetMap = this.findWorktimeSheetByStartAndEnd(companyID,
				workTimeCodes, startClock, endClock);

		// get filtered worktime codes
		Set<String> filteredCodes = sheetMap.keySet();

		// get list PredTimeSet
		List<KshmtPredTimeSet> kwtstWorkTimeSets = this.findByListCodes(companyID, filteredCodes);

		// return mapped list
		return this.mapPredTimeWithWorktimeSheet(kwtstWorkTimeSets, sheetMap);
	}

	/**
	 * Find worktime sheet by start.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the work time codes
	 * @param startClock the start clock
	 * @return the map
	 */
	private Map<String, List<KshmtWorkTimeSheetSet>> findWorktimeSheetByStart(String companyID,
			List<String> workTimeCodes, int startClock) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder sheetCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtWorkTimeSheetSet> sheetCquery = sheetCb.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> sheetRoot = sheetCquery.from(KshmtWorkTimeSheetSet.class);

		// select root
		sheetCquery.select(sheetRoot);

		// add predicates
		List<Predicate> worktimeSheetPredicates = new ArrayList<>();

		// worktime sheet predicates
		worktimeSheetPredicates.add(sheetCb.equal(
				sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyID));
		worktimeSheetPredicates.add(sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK)
				.get(KshmtWorkTimeSheetSetPK_.worktimeCd).in(workTimeCodes));
		worktimeSheetPredicates.add(sheetCb.equal(sheetRoot.get(KshmtWorkTimeSheetSet_.startTime), startClock));

		// set condition
		sheetCquery.where(worktimeSheetPredicates.toArray(new Predicate[] {}));

		// get results
		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em.createQuery(sheetCquery).getResultList();

		// group by worktime code
		return lstKshmtWorkTimeSheetSet.stream()
				.collect(Collectors.groupingBy(sheet -> sheet.getKshmtWorkTimeSheetSetPK().getWorktimeCd()));
	}

	/**
	 * Find worktime sheet by end.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the work time codes
	 * @param endClock the end clock
	 * @return the map
	 */
	private Map<String, List<KshmtWorkTimeSheetSet>> findWorktimeSheetByEnd(String companyID,
			List<String> workTimeCodes, int endClock) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder sheetCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtWorkTimeSheetSet> sheetCquery = sheetCb.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> sheetRoot = sheetCquery.from(KshmtWorkTimeSheetSet.class);

		// select root
		sheetCquery.select(sheetRoot);

		// add predicates
		List<Predicate> worktimeSheetPredicates = new ArrayList<>();

		// worktime sheet predicates
		worktimeSheetPredicates.add(sheetCb.equal(
				sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyID));
		worktimeSheetPredicates.add(sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK)
				.get(KshmtWorkTimeSheetSetPK_.worktimeCd).in(workTimeCodes));
		worktimeSheetPredicates.add(sheetCb.equal(sheetRoot.get(KshmtWorkTimeSheetSet_.endTime), endClock));

		// set condition
		sheetCquery.where(worktimeSheetPredicates.toArray(new Predicate[] {}));

		// get results
		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em.createQuery(sheetCquery).getResultList();

		// group by worktime code
		return lstKshmtWorkTimeSheetSet.stream()
				.collect(Collectors.groupingBy(sheet -> sheet.getKshmtWorkTimeSheetSetPK().getWorktimeCd()));
	}

	private Map<String, List<KshmtWorkTimeSheetSet>> findWorktimeSheetByCodes(String companyID,
			List<String> workTimeCodes) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder sheetCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtWorkTimeSheetSet> sheetCquery = sheetCb.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> sheetRoot = sheetCquery.from(KshmtWorkTimeSheetSet.class);

		// select root
		sheetCquery.select(sheetRoot);

		// add predicates
		List<Predicate> worktimeSheetPredicates = new ArrayList<>();

		// worktime sheet predicates
		worktimeSheetPredicates.add(sheetCb.equal(
				sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyID));
		worktimeSheetPredicates.add(sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK)
				.get(KshmtWorkTimeSheetSetPK_.worktimeCd).in(workTimeCodes));

		// set condition
		sheetCquery.where(worktimeSheetPredicates.toArray(new Predicate[] {}));

		// get results
		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em.createQuery(sheetCquery).getResultList();

		// group by worktime code
		return lstKshmtWorkTimeSheetSet.stream()
				.collect(Collectors.groupingBy(sheet -> sheet.getKshmtWorkTimeSheetSetPK().getWorktimeCd()));
	}

	private Map<String, List<KshmtWorkTimeSheetSet>> findWorktimeSheetByComId(String companyID) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder sheetCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtWorkTimeSheetSet> sheetCquery = sheetCb.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> sheetRoot = sheetCquery.from(KshmtWorkTimeSheetSet.class);

		// select root
		sheetCquery.select(sheetRoot);

		// add predicates
		List<Predicate> worktimeSheetPredicates = new ArrayList<>();

		// worktime sheet predicates
		worktimeSheetPredicates.add(sheetCb.equal(
				sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyID));

		// set condition
		sheetCquery.where(worktimeSheetPredicates.toArray(new Predicate[] {}));

		// get results
		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em.createQuery(sheetCquery).getResultList();

		// group by worktime code
		return lstKshmtWorkTimeSheetSet.stream()
				.collect(Collectors.groupingBy(sheet -> sheet.getKshmtWorkTimeSheetSetPK().getWorktimeCd()));
	}

	/**
	 * Find worktime sheet by start and end.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the work time codes
	 * @param startClock the start clock
	 * @param endClock the end clock
	 * @return the map
	 */
	private Map<String, List<KshmtWorkTimeSheetSet>> findWorktimeSheetByStartAndEnd(String companyID,
			List<String> workTimeCodes, int startClock, int endClock) {
		// get CriteriaBuilder
		EntityManager em = this.getEntityManager();
		CriteriaBuilder sheetCb = em.getCriteriaBuilder();

		// create CriteriaQuery
		CriteriaQuery<KshmtWorkTimeSheetSet> sheetCquery = sheetCb.createQuery(KshmtWorkTimeSheetSet.class);
		Root<KshmtWorkTimeSheetSet> sheetRoot = sheetCquery.from(KshmtWorkTimeSheetSet.class);

		// select root
		sheetCquery.select(sheetRoot);

		// add predicates
		List<Predicate> worktimeSheetPredicates = new ArrayList<>();

		// worktime sheet predicates
		worktimeSheetPredicates.add(sheetCb.equal(
				sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK).get(KshmtWorkTimeSheetSetPK_.cid),
				companyID));
		worktimeSheetPredicates.add(sheetRoot.get(KshmtWorkTimeSheetSet_.kshmtWorkTimeSheetSetPK)
				.get(KshmtWorkTimeSheetSetPK_.worktimeCd).in(workTimeCodes));
		worktimeSheetPredicates.add(sheetCb.equal(sheetRoot.get(KshmtWorkTimeSheetSet_.startTime), startClock));
		worktimeSheetPredicates.add(sheetCb.equal(sheetRoot.get(KshmtWorkTimeSheetSet_.endTime), endClock));

		// set condition
		sheetCquery.where(worktimeSheetPredicates.toArray(new Predicate[] {}));

		// get results
		List<KshmtWorkTimeSheetSet> lstKshmtWorkTimeSheetSet = em.createQuery(sheetCquery).getResultList();

		// group by worktime code
		return lstKshmtWorkTimeSheetSet.stream()
				.collect(Collectors.groupingBy(sheet -> sheet.getKshmtWorkTimeSheetSetPK().getWorktimeCd()));
	}

	/**
	 * Find by list codes.
	 *
	 * @param companyID the company ID
	 * @param workTimeCodes the work time codes
	 * @return the list
	 */
	private List<KshmtPredTimeSet> findByListCodes(String companyID, Set<String> workTimeCodes) {
		if(CollectionUtil.isEmpty(workTimeCodes)){
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

		// add predicates
		List<Predicate> predTimePredicates = new ArrayList<>();

		// predTime predicates
		predTimePredicates.add(predCb
				.equal(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.cid), companyID));
		predTimePredicates.add(predRoot.get(KshmtPredTimeSet_.kshmtPredTimeSetPK).get(KshmtPredTimeSetPK_.worktimeCd)
				.in(workTimeCodes));

		// set condition
		predCquery.where(predTimePredicates.toArray(new Predicate[] {}));

		// get results
		return em.createQuery(predCquery).getResultList();
	}

	/**
	 * Map pred time with worktime sheet.
	 *
	 * @param kwtstWorkTimeSets the kwtst work time sets
	 * @param sheetMap the sheet map
	 * @return the list
	 */
	private List<PredetemineTimeSetting> mapPredTimeWithWorktimeSheet(List<KshmtPredTimeSet> kwtstWorkTimeSets,
			Map<String, List<KshmtWorkTimeSheetSet>> sheetMap) {
		return kwtstWorkTimeSets.stream().map(worktime -> {
			List<KshmtWorkTimeSheetSet> sheets = sheetMap.get(worktime.getKshmtPredTimeSetPK().getWorktimeCd());
			return new PredetemineTimeSetting(new JpaPredetemineTimeSettingGetMemento(worktime, sheets));
		}).collect(Collectors.toList());
	}

	@Override
	public List<PredetemineTimeSetting> findByCompanyID(String companyID) {
		// get worktime sheet map
		Map<String, List<KshmtWorkTimeSheetSet>> sheetMap = this.findWorktimeSheetByComId(companyID);

		// get filtered worktime codes
		Set<String> filteredCodes = sheetMap.keySet();

		// get list PredTimeSet
		List<KshmtPredTimeSet> kwtstWorkTimeSets = this.findByListCodes(companyID, filteredCodes);

		// return mapped list
		return this.mapPredTimeWithWorktimeSheet(kwtstWorkTimeSets, sheetMap);
	}

	@Override
	public List<PredetemineTimeSetting> findByCodeList(String companyID, List<String> worktimeCodes) {
		// get worktime sheet map
		Map<String, List<KshmtWorkTimeSheetSet>> sheetMap = this.findWorktimeSheetByCodes(companyID, worktimeCodes);

		// get filtered worktime codes
		Set<String> filteredCodes = sheetMap.keySet();

		// get list PredTimeSet
		List<KshmtPredTimeSet> kwtstWorkTimeSets = this.findByListCodes(companyID, filteredCodes);

		// return mapped list
		List<PredetemineTimeSetting> a = this.mapPredTimeWithWorktimeSheet(kwtstWorkTimeSets, sheetMap);
		return a;
	}
}
