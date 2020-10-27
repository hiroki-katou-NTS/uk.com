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
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTsPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloBrFiAllTs_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFloPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.flowset.KshmtWtFlo_;
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
		Optional<KshmtWtFlo> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtWtFloPK(companyId, workTimeCode), KshmtWtFlo.class);

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
		this.commandProxy().remove(KshmtWtFlo.class, new KshmtWtFloPK(companyId, workTimeCode));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt flow work set
	 */
	private KshmtWtFlo toEntity(FlowWorkSetting domain) {
		// Find entity
		Optional<KshmtWtFlo> optional = this.queryProxy().find(
				new KshmtWtFloPK(domain.getCompanyId(), domain.getWorkingCode().v()), KshmtWtFlo.class);

		KshmtWtFlo entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtWtFlo();
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
		CriteriaQuery<KshmtWtFlo> query = builder.createQuery(KshmtWtFlo.class);
		Root<KshmtWtFlo> root = query.from(KshmtWtFlo.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtWtFlo_.kshmtWtFloPK).get(KshmtWtFloPK_.cid),
				companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtWtFlo> result = em.createQuery(query).getResultList();

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
		CriteriaQuery<KshmtWtFloBrFiAllTs> query = builder.createQuery(KshmtWtFloBrFiAllTs.class);
		Root<KshmtWtFloBrFiAllTs> root = query.from(KshmtWtFloBrFiAllTs.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtWtFloBrFiAllTs_.kshmtWtFloBrFiAllTsPK).get(KshmtWtFloBrFiAllTsPK_.cid), companyId));
		predicateList.add(builder.equal(
				root.get(KshmtWtFloBrFiAllTs_.kshmtWtFloBrFiAllTsPK).get(KshmtWtFloBrFiAllTsPK_.resttimeAtr),
				ResttimeAtr.OFF_DAY.value));
		predicateList.add(root.get(KshmtWtFloBrFiAllTs_.kshmtWtFloBrFiAllTsPK).get(KshmtWtFloBrFiAllTsPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtWtFloBrFiAllTs_.strDay)));

		List<KshmtWtFloBrFiAllTs> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtWtFloBrFiAllTs>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtWtFloBrFiAllTsPK().getWorktimeCd())));

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
		CriteriaQuery<KshmtWtFloBrFiAllTs> query = builder.createQuery(KshmtWtFloBrFiAllTs.class);
		Root<KshmtWtFloBrFiAllTs> root = query.from(KshmtWtFloBrFiAllTs.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtWtFloBrFiAllTs_.kshmtWtFloBrFiAllTsPK).get(KshmtWtFloBrFiAllTsPK_.cid), companyId));
		predicateList.add(builder.equal(
				root.get(KshmtWtFloBrFiAllTs_.kshmtWtFloBrFiAllTsPK).get(KshmtWtFloBrFiAllTsPK_.resttimeAtr),
				ResttimeAtr.HALF_DAY.value));
		predicateList.add(root.get(KshmtWtFloBrFiAllTs_.kshmtWtFloBrFiAllTsPK).get(KshmtWtFloBrFiAllTsPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtWtFloBrFiAllTs_.strDay)));

		List<KshmtWtFloBrFiAllTs> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtWtFloBrFiAllTs>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtWtFloBrFiAllTsPK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream().collect(Collectors.toMap(
				e -> e.getKey(),
				 e -> e.getValue().stream().map(
							item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
							.collect(Collectors.toList())));
		
		return map;
	}

	@Override
	public List<FlowWorkSetting> findByCidAndWorkCodes(String cid, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtWtFlo> query = builder.createQuery(KshmtWtFlo.class);
		Root<KshmtWtFlo> root = query.from(KshmtWtFlo.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtWtFlo_.kshmtWtFloPK).get(KshmtWtFloPK_.cid),
				cid));
		
		predicateList.add(root.get(KshmtWtFlo_.kshmtWtFloPK).get(KshmtWtFloPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtWtFlo> result = em.createQuery(query).getResultList();

		return result.stream()
				.map(entity -> new FlowWorkSetting(new JpaFlowWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}
}
