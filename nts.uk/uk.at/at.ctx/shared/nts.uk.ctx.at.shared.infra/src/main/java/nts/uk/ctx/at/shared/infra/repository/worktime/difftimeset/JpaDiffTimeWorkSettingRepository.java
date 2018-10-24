/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHalfRestTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHalfRestTimePK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHalfRestTime_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHolRestTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHolRestTimePK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDtHolRestTime_;
import nts.uk.ctx.at.shared.infra.repository.worktime.performance.JpaAmPmWorkTimezoneGetMemento;

/**
 * The Class JpaDiffTimeWorkSettingRepository.
 */

@Stateless
public class JpaDiffTimeWorkSettingRepository extends JpaRepository implements DiffTimeWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DiffTimeWorkSetting> find(String companyId, String workTimeCode) {
		// Query
		Optional<KshmtDiffTimeWorkSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtDiffTimeWorkSetPK(companyId, workTimeCode), KshmtDiffTimeWorkSet.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}
		return Optional
				.ofNullable(new DiffTimeWorkSetting(new JpaDiffTimeWorkSettingGetMemento(optionalEntityTimeSet.get())));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingRepository#add(nts.uk.ctx.at.shared.dom.worktime.
	 * difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void add(DiffTimeWorkSetting diffTimeWorkSetting) {
		this.commandProxy().insert(this.toEntity(diffTimeWorkSetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingRepository#update(nts.uk.ctx.at.shared.dom.worktime.
	 * difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void update(DiffTimeWorkSetting diffTimeWorkSetting) {
		this.commandProxy().update(this.toEntity(diffTimeWorkSetting));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtDiffTimeWorkSet.class, new KshmtDiffTimeWorkSetPK(companyId, workTimeCode));
	}

	private KshmtDiffTimeWorkSet toEntity(DiffTimeWorkSetting domain) {
		// Find entity
		Optional<KshmtDiffTimeWorkSet> optional = this.queryProxy().find(
				new KshmtDiffTimeWorkSetPK(domain.getCompanyId(), domain.getWorkTimeCode().v()),
				KshmtDiffTimeWorkSet.class);

		KshmtDiffTimeWorkSet entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtDiffTimeWorkSet();
		}
		// save to memento
		domain.saveToMemento(new JpaDiffTimeWorkSettingSetMemento(entity));
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingRepository#findByCId(java.lang.String)
	 */
	@Override
	public List<DiffTimeWorkSetting> findByCId(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtDiffTimeWorkSet> query = builder.createQuery(KshmtDiffTimeWorkSet.class);
		Root<KshmtDiffTimeWorkSet> root = query.from(KshmtDiffTimeWorkSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(KshmtDiffTimeWorkSet_.kshmtDiffTimeWorkSetPK)
				.get(KshmtDiffTimeWorkSetPK_.cid), companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtDiffTimeWorkSet> result = em.createQuery(query).getResultList();

		return result.stream().map(
				entity -> new DiffTimeWorkSetting(new JpaDiffTimeWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository#getDiffOffdayWorkRestTimezones(java.lang.String)
	 */
	@Override
	public Map<WorkTimeCode, List<AmPmWorkTimezone>> getDiffOffdayWorkRestTimezones(
			String companyId, List<String> workTimeCodes) {
		if(CollectionUtil.isEmpty(workTimeCodes)) {
			return Collections.emptyMap();
		}
		
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtDtHolRestTime> query = builder.createQuery(KshmtDtHolRestTime.class);
		Root<KshmtDtHolRestTime> root = query.from(KshmtDtHolRestTime.class);
		
		List<KshmtDtHolRestTime> resultList = new ArrayList<>();

		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> predicateList = new ArrayList<>();

			predicateList.add(builder.equal(
					root.get(KshmtDtHolRestTime_.kshmtDtHolRestTimePK).get(KshmtDtHolRestTimePK_.cid),
					companyId));
			predicateList.add(root.get(KshmtDtHolRestTime_.kshmtDtHolRestTimePK)
					.get(KshmtDtHolRestTimePK_.worktimeCd).in(splitData));

			query.where(predicateList.toArray(new Predicate[] {}));

			query.orderBy(builder.asc(root.get(KshmtDtHolRestTime_.startTime)));

			resultList.addAll(em.createQuery(query).getResultList());
		});

		Map<WorkTimeCode, List<KshmtDtHolRestTime>> mapResttimes = resultList.stream()
				.collect(Collectors.groupingBy(
						item -> new WorkTimeCode(item.getKshmtDtHolRestTimePK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().map(
						item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
						.collect(Collectors.toList())));
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimeWorkSettingRepository#getDiffHalfDayWorkRestTimezones(java.lang.
	 * String)
	 */
	@Override
	public Map<WorkTimeCode, List<AmPmWorkTimezone>> getDiffHalfDayWorkRestTimezones(
			String companyId, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtDtHalfRestTime> query = builder.createQuery(KshmtDtHalfRestTime.class);
		Root<KshmtDtHalfRestTime> root = query.from(KshmtDtHalfRestTime.class);
		
		List<KshmtDtHalfRestTime> resultList = new ArrayList<>();

		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> predicateList = new ArrayList<>();

			predicateList.add(builder.equal(root.get(KshmtDtHalfRestTime_.kshmtDtHalfRestTimePK)
					.get(KshmtDtHalfRestTimePK_.cid), companyId));
			predicateList.add(root.get(KshmtDtHalfRestTime_.kshmtDtHalfRestTimePK)
					.get(KshmtDtHalfRestTimePK_.worktimeCd).in(splitData));

			query.where(predicateList.toArray(new Predicate[] {}));

			// order by closure id asc
			query.orderBy(builder.asc(root.get(KshmtDtHalfRestTime_.startTime)));

			resultList.addAll(em.createQuery(query).getResultList());
		});

		Map<WorkTimeCode, List<KshmtDtHalfRestTime>> mapResttimes = resultList.stream()
				.collect(Collectors.groupingBy(
						item -> new WorkTimeCode(item.getKshmtDtHalfRestTimePK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().map(
						item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
						.collect(Collectors.toList())));

		return map;
	}

}
