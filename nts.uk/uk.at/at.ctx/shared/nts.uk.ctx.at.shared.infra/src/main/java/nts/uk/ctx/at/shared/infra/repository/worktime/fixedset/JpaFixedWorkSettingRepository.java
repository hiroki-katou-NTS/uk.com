/******************************************************************

 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

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
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.perfomance.AmPmWorkTimezone;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHalfRestSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedHolRestSet_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedStampReflectPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet_;
import nts.uk.ctx.at.shared.infra.repository.worktime.performance.JpaAmPmWorkTimezoneGetMemento;

/**
 * The Class JpaFixedWorkSettingRepository.
 */
@Stateless
public class JpaFixedWorkSettingRepository extends JpaRepository implements FixedWorkSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#add
	 * (nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting)
	 */
	@Override
	public void add(FixedWorkSetting domain) {
		if(domain.getLstHalfDayWorkTimezone().get(1).getRestTimezone().getTimezones().isEmpty()) {
			domain.getLstHalfDayWorkTimezone().get(1).getRestTimezone().getTimezones().addAll(domain.getLstHalfDayWorkTimezone().get(0).getRestTimezone().getTimezones());
		}
		
		if(domain.getLstHalfDayWorkTimezone().get(2).getRestTimezone().getTimezones().isEmpty()) {
			domain.getLstHalfDayWorkTimezone().get(2).getRestTimezone().getTimezones().addAll(domain.getLstHalfDayWorkTimezone().get(0).getRestTimezone().getTimezones());
		}
		this.commandProxy().insert(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#
	 * update(nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting)
	 */
	@Override
	public void update(FixedWorkSetting domain) {
		KshmtWtFix entity = this.toEntity(domain);
		this.commandProxy().update(entity);
		
		removeRefTimeNo2(entity);
	}

	private void removeRefTimeNo2(KshmtWtFix entity) {
		// this algorithm for remove RefTimeNo2 if not Use
		boolean notUseRefTimeNo2 = !entity.getLstKshmtFixedStampReflect().stream()
				.filter(x -> x.getKshmtFixedStampReflectPK().getWorkNo() == 2).findAny().isPresent();
		if (notUseRefTimeNo2) {
			entity.getLstKshmtFixedStampReflect().stream().filter(x -> x.getKshmtFixedStampReflectPK().getWorkNo() == 1)
					.findFirst().ifPresent(x -> {
						KshmtFixedStampReflectPK pk = x.getKshmtFixedStampReflectPK();
						String SEL_REF_TIME_NO_2 = "SELECT a FROM KshmtWtFixStmpRefTs a WHERE "
								+ "a.kshmtFixedStampReflectPK.cid= :cid "
								+ "AND a.kshmtFixedStampReflectPK.worktimeCd = :worktimeCd "
								+ "AND a.kshmtFixedStampReflectPK.workNo = 2";
						// get No 2
						List<KshmtWtFixStmpRefTs> no2Items = this.queryProxy()
								.query(SEL_REF_TIME_NO_2, KshmtWtFixStmpRefTs.class).setParameter("cid", pk.getCid())
								.setParameter("worktimeCd", pk.getWorktimeCd()).getList();

						if (!no2Items.isEmpty()) {
							this.commandProxy().removeAll(no2Items);
						}
					});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#
	 * remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String workTimeCode) {
		this.commandProxy().remove(KshmtWtFix.class, new KshmtFixedWorkSetPK(companyId, workTimeCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#
	 * findByKey(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<FixedWorkSetting> findByKey(String companyId, String workTimeCode) {
		// Query
		Optional<KshmtWtFix> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtFixedWorkSetPK(companyId, workTimeCode), KshmtWtFix.class);

		// Check exist
		if (!optionalEntityTimeSet.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(new FixedWorkSetting(new JpaFixedWorkSettingGetMemento(optionalEntityTimeSet.get())));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kshmt fixed work set
	 */
	private KshmtWtFix toEntity(FixedWorkSetting domain) {
		// Find entity
		Optional<KshmtWtFix> optional = this.queryProxy().find(
				new KshmtFixedWorkSetPK(domain.getCompanyId(), domain.getWorkTimeCode().v()), KshmtWtFix.class);

		KshmtWtFix entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtWtFix();
		}
		// save to memento
		domain.saveToMemento(new JpaFixedWorkSettingSetMemento(entity));
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#
	 * findByCId(java.lang.String)
	 */
	@Override
	public List<FixedWorkSetting> findByCId(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtWtFix> query = builder.createQuery(KshmtWtFix.class);
		Root<KshmtWtFix> root = query.from(KshmtWtFix.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFixedWorkSet_.kshmtFixedWorkSetPK).get(KshmtFixedWorkSetPK_.cid),
				companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtWtFix> result = em.createQuery(query).getResultList();

		return result.stream()
				.map(entity -> new FixedWorkSetting(new JpaFixedWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#
	 * getFixOffdayWorkRestTimezones(java.lang.String)
	 */
	@Override
	public Map<WorkTimeCode, List<AmPmWorkTimezone>> getFixOffdayWorkRestTimezones(String companyId, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtWtFixBrHolTs> query = builder.createQuery(KshmtWtFixBrHolTs.class);
		Root<KshmtWtFixBrHolTs> root = query.from(KshmtWtFixBrHolTs.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFixedHolRestSet_.kshmtFixedHolRestSetPK).get(KshmtFixedHolRestSetPK_.cid), companyId));
		predicateList.add(root.get(KshmtFixedHolRestSet_.kshmtFixedHolRestSetPK).get(KshmtFixedHolRestSetPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtFixedHolRestSet_.startTime)));

		List<KshmtWtFixBrHolTs> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtWtFixBrHolTs>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtFixedHolRestSetPK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream().collect(Collectors
				.toMap(e -> e.getKey(),  e -> e.getValue().stream().map(
						item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
						.collect(Collectors.toList())));
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository#
	 * getFixHalfDayWorkRestTimezones(java.lang.String)
	 */
	@Override
	public Map<WorkTimeCode, List<AmPmWorkTimezone>> getFixHalfDayWorkRestTimezones(String companyId, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtWtFixBrWekTs> query = builder.createQuery(KshmtWtFixBrWekTs.class);
		Root<KshmtWtFixBrWekTs> root = query.from(KshmtWtFixBrWekTs.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFixedHalfRestSet_.kshmtFixedHalfRestSetPK).get(KshmtFixedHalfRestSetPK_.cid), companyId));
		predicateList.add(root.get(KshmtFixedHalfRestSet_.kshmtFixedHalfRestSetPK).get(KshmtFixedHalfRestSetPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtFixedHalfRestSet_.startTime)));

		List<KshmtWtFixBrWekTs> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtWtFixBrWekTs>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtFixedHalfRestSetPK().getWorktimeCd())));

		Map<WorkTimeCode, List<AmPmWorkTimezone>> map = mapResttimes.entrySet().stream().collect(Collectors
				.toMap(e -> e.getKey(),  e -> e.getValue().stream().map(
						item -> new AmPmWorkTimezone(new JpaAmPmWorkTimezoneGetMemento<>(item)))
						.collect(Collectors.toList())));
		return map;
	}

	@Override
	public List<FixedWorkSetting> findByCidAndWorkTimeCodes(String companyId, List<String> workTimeCodes) {
		EntityManager em = this.getEntityManager();
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KshmtWtFix> query = builder.createQuery(KshmtWtFix.class);
		Root<KshmtWtFix> root = query.from(KshmtWtFix.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFixedWorkSet_.kshmtFixedWorkSetPK).get(KshmtFixedWorkSetPK_.cid), companyId));
		predicateList.add(root.get(KshmtFixedWorkSet_.kshmtFixedWorkSetPK).get(KshmtFixedWorkSetPK_.worktimeCd).in(workTimeCodes));
		query.where(predicateList.toArray(new Predicate[] {}));
		List<KshmtWtFix> result = em.createQuery(query).getResultList();
		return result.stream()
				.map(entity -> new FixedWorkSetting(new JpaFixedWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}
}
