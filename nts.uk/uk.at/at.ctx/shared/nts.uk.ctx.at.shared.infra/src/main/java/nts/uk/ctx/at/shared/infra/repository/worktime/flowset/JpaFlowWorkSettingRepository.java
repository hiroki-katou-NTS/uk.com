/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.flowset;

import java.util.ArrayList;
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

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowFixedRtSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowFixedRtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowFixedRtSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtFlowWorkSet_;
import nts.uk.ctx.at.shared.infra.repository.worktime.performance.JpaAmPmWorkTimezoneGetMemento;

/**
 * The Class JpaFlowWorkSettingRepository.
 */
@Stateless
public class JpaFlowWorkSettingRepository extends JpaRepository
		implements FlowWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository#find(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FlowWorkSetting> find(String companyId, String workTimeCode) {
		// Query
		Optional<KshmtFlowWorkSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtFlowWorkSetPK(companyId, workTimeCode), KshmtFlowWorkSet.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(new FlowWorkSetting(new JpaFlowWorkSettingGetMemento(optionalEntityTimeSet.get())));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#add(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void add(FlowWorkSetting domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#update(nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting)
	 */
	@Override
	public void update(FlowWorkSetting domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtFlowWorkSet.class, new KshmtFlowWorkSetPK(companyId, workTimeCode));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt flow work set
	 */
	private KshmtFlowWorkSet toEntity(FlowWorkSetting domain) {
		// Find entity
		Optional<KshmtFlowWorkSet> optional = this.queryProxy().find(
				new KshmtFlowWorkSetPK(domain.getCompanyId(), domain.getWorkingCode().v()), KshmtFlowWorkSet.class);

		KshmtFlowWorkSet entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtFlowWorkSet();
		}
		// save to memento
		domain.saveToMemento(new JpaFlowWorkSettingSetMemento(entity));
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#
	 * findByCId(java.lang.String)
	 */
	@Override
	public List<FlowWorkSetting> findByCId(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtFlowWorkSet> query = builder.createQuery(KshmtFlowWorkSet.class);
		Root<KshmtFlowWorkSet> root = query.from(KshmtFlowWorkSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFlowWorkSet_.kshmtFlowWorkSetPK).get(KshmtFlowWorkSetPK_.cid),
				companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtFlowWorkSet> result = em.createQuery(query).getResultList();

		return result.stream()
				.map(entity -> new FlowWorkSetting(new JpaFlowWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#getFlowOffdayWorkRestTimezones(java.lang.String)
	 */
	@Override
	public Map<WorkTimeCode, List<AmPmWorkTimezone>> getFlowOffdayWorkRestTimezones(String companyId, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtFlowFixedRtSet> query = builder.createQuery(KshmtFlowFixedRtSet.class);
		Root<KshmtFlowFixedRtSet> root = query.from(KshmtFlowFixedRtSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFlowFixedRtSet_.kshmtFlowFixedRtSetPK).get(KshmtFlowFixedRtSetPK_.cid), companyId));
		predicateList.add(builder.equal(
				root.get(KshmtFlowFixedRtSet_.kshmtFlowFixedRtSetPK).get(KshmtFlowFixedRtSetPK_.resttimeAtr),
				ResttimeAtr.OFF_DAY.value));
		predicateList.add(root.get(KshmtFlowFixedRtSet_.kshmtFlowFixedRtSetPK).get(KshmtFlowFixedRtSetPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtFlowFixedRtSet_.strDay)));

		List<KshmtFlowFixedRtSet> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtFlowFixedRtSet>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtFlowFixedRtSetPK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream().collect(Collectors.toMap(
				e -> e.getKey(),
				e -> e.getValue().stream().map(
						item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
						.collect(Collectors.toList())));
		return map;
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository#getFlowHalfDayWorkRestTimezones(java.lang.String)
	 */
	@Override
	public Map<WorkTimeCode, List<AmPmWorkTimezone>> getFlowHalfDayWorkRestTimezones(String companyId, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtFlowFixedRtSet> query = builder.createQuery(KshmtFlowFixedRtSet.class);
		Root<KshmtFlowFixedRtSet> root = query.from(KshmtFlowFixedRtSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFlowFixedRtSet_.kshmtFlowFixedRtSetPK).get(KshmtFlowFixedRtSetPK_.cid), companyId));
		predicateList.add(builder.equal(
				root.get(KshmtFlowFixedRtSet_.kshmtFlowFixedRtSetPK).get(KshmtFlowFixedRtSetPK_.resttimeAtr),
				ResttimeAtr.HALF_DAY.value));
		predicateList.add(root.get(KshmtFlowFixedRtSet_.kshmtFlowFixedRtSetPK).get(KshmtFlowFixedRtSetPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtFlowFixedRtSet_.strDay)));

		List<KshmtFlowFixedRtSet> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtFlowFixedRtSet>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtFlowFixedRtSetPK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream().collect(Collectors.toMap(
				e -> e.getKey(),
				 e -> e.getValue().stream().map(
							item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
							.collect(Collectors.toList())));
		
		return map;
	}
}
