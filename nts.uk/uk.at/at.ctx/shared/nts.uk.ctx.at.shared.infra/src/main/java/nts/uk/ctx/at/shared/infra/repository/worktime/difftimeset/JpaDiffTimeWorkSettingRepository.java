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
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDif_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTsPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrWekTs_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTsPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtWtDifBrHolTs_;
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
		Optional<KshmtWtDif> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtWtDifPK(companyId, workTimeCode), KshmtWtDif.class);

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
		this.commandProxy().remove(KshmtWtDif.class, new KshmtWtDifPK(companyId, workTimeCode));
	}

	private KshmtWtDif toEntity(DiffTimeWorkSetting domain) {
		// Find entity
		Optional<KshmtWtDif> optional = this.queryProxy().find(
				new KshmtWtDifPK(domain.getCompanyId(), domain.getWorkTimeCode().v()),
				KshmtWtDif.class);

		KshmtWtDif entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtWtDif();
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
		CriteriaQuery<KshmtWtDif> query = builder.createQuery(KshmtWtDif.class);
		Root<KshmtWtDif> root = query.from(KshmtWtDif.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(KshmtWtDif_.kshmtWtDifPK)
				.get(KshmtWtDifPK_.cid), companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtWtDif> result = em.createQuery(query).getResultList();

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
		CriteriaQuery<KshmtWtDifBrHolTs> query = builder.createQuery(KshmtWtDifBrHolTs.class);
		Root<KshmtWtDifBrHolTs> root = query.from(KshmtWtDifBrHolTs.class);
		
		List<KshmtWtDifBrHolTs> resultList = new ArrayList<>();

		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> predicateList = new ArrayList<>();

			predicateList.add(builder.equal(
					root.get(KshmtWtDifBrHolTs_.kshmtWtDifBrHolTsPK).get(KshmtWtDifBrHolTsPK_.cid),
					companyId));
			predicateList.add(root.get(KshmtWtDifBrHolTs_.kshmtWtDifBrHolTsPK)
					.get(KshmtWtDifBrHolTsPK_.worktimeCd).in(splitData));

			query.where(predicateList.toArray(new Predicate[] {}));

			query.orderBy(builder.asc(root.get(KshmtWtDifBrHolTs_.startTime)));

			resultList.addAll(em.createQuery(query).getResultList());
		});

		Map<WorkTimeCode, List<KshmtWtDifBrHolTs>> mapResttimes = resultList.stream()
				.collect(Collectors.groupingBy(
						item -> new WorkTimeCode(item.getKshmtWtDifBrHolTsPK().getWorktimeCd())));

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
		CriteriaQuery<KshmtWtDifBrWekTs> query = builder.createQuery(KshmtWtDifBrWekTs.class);
		Root<KshmtWtDifBrWekTs> root = query.from(KshmtWtDifBrWekTs.class);
		
		List<KshmtWtDifBrWekTs> resultList = new ArrayList<>();

		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> predicateList = new ArrayList<>();

			predicateList.add(builder.equal(root.get(KshmtWtDifBrWekTs_.kshmtWtDifBrWekTsPK)
					.get(KshmtWtDifBrWekTsPK_.cid), companyId));
			predicateList.add(root.get(KshmtWtDifBrWekTs_.kshmtWtDifBrWekTsPK)
					.get(KshmtWtDifBrWekTsPK_.worktimeCd).in(splitData));

			query.where(predicateList.toArray(new Predicate[] {}));

			// order by closure id asc
			query.orderBy(builder.asc(root.get(KshmtWtDifBrWekTs_.startTime)));

			resultList.addAll(em.createQuery(query).getResultList());
		});

		Map<WorkTimeCode, List<KshmtWtDifBrWekTs>> mapResttimes = resultList.stream()
				.collect(Collectors.groupingBy(
						item -> new WorkTimeCode(item.getKshmtWtDifBrWekTsPK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().map(
						item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
						.collect(Collectors.toList())));

		return map;
	}

	@Override
	public List<DiffTimeWorkSetting> findByCidAndWorkCodes(String cid, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtWtDif> query = builder.createQuery(KshmtWtDif.class);
		Root<KshmtWtDif> root = query.from(KshmtWtDif.class);
		List<KshmtWtDif> resultList = new ArrayList<>();
		CollectionUtil.split(workTimeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			List<Predicate> predicateList = new ArrayList<>();

			predicateList.add(builder.equal(root.get(KshmtWtDif_.kshmtWtDifPK)
					.get(KshmtWtDifPK_.cid), cid));
			
			predicateList.add(root.get(KshmtWtDif_.kshmtWtDifPK)
					.get(KshmtWtDifPK_.worktimeCd).in(workTimeCodes));

			query.where(predicateList.toArray(new Predicate[] {}));

			List<KshmtWtDif> result = em.createQuery(query).getResultList();
			resultList.addAll(result);
		});


		return resultList.stream().map(
				entity -> new DiffTimeWorkSetting(new JpaDiffTimeWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

}
