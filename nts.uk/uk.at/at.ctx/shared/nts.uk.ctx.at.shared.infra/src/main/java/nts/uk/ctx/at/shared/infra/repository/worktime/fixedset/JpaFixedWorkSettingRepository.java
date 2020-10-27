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
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTsPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrWekTs_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTsPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixBrHolTs_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTs;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixStmpRefTsPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFixPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtWtFix_;
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
		boolean notUseRefTimeNo2 = !entity.getLstKshmtWtFixStmpRefTs().stream()
				.filter(x -> x.getKshmtWtFixStmpRefTsPK().getWorkNo() == 2).findAny().isPresent();
		if (notUseRefTimeNo2) {
			entity.getLstKshmtWtFixStmpRefTs().stream().filter(x -> x.getKshmtWtFixStmpRefTsPK().getWorkNo() == 1)
					.findFirst().ifPresent(x -> {
						KshmtWtFixStmpRefTsPK pk = x.getKshmtWtFixStmpRefTsPK();
						String SEL_REF_TIME_NO_2 = "SELECT a FROM KshmtWtFixStmpRefTs a WHERE "
								+ "a.kshmtWtFixStmpRefTsPK.cid= :cid "
								+ "AND a.kshmtWtFixStmpRefTsPK.worktimeCd = :worktimeCd "
								+ "AND a.kshmtWtFixStmpRefTsPK.workNo = 2";
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
		this.commandProxy().remove(KshmtWtFix.class, new KshmtWtFixPK(companyId, workTimeCode));
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
				.find(new KshmtWtFixPK(companyId, workTimeCode), KshmtWtFix.class);

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
				new KshmtWtFixPK(domain.getCompanyId(), domain.getWorkTimeCode().v()), KshmtWtFix.class);

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
				root.get(KshmtWtFix_.kshmtWtFixPK).get(KshmtWtFixPK_.cid),
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
				root.get(KshmtWtFixBrHolTs_.kshmtWtFixBrHolTsPK).get(KshmtWtFixBrHolTsPK_.cid), companyId));
		predicateList.add(root.get(KshmtWtFixBrHolTs_.kshmtWtFixBrHolTsPK).get(KshmtWtFixBrHolTsPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtWtFixBrHolTs_.startTime)));

		List<KshmtWtFixBrHolTs> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtWtFixBrHolTs>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtWtFixBrHolTsPK().getWorktimeCd())));

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
				root.get(KshmtWtFixBrWekTs_.kshmtWtFixBrWekTsPK).get(KshmtWtFixBrWekTsPK_.cid), companyId));
		predicateList.add(root.get(KshmtWtFixBrWekTs_.kshmtWtFixBrWekTsPK).get(KshmtWtFixBrWekTsPK_.worktimeCd)
				.in(workTimeCodes));

		query.where(predicateList.toArray(new Predicate[] {}));
		
		query.orderBy(builder.asc(root.get(KshmtWtFixBrWekTs_.startTime)));

		List<KshmtWtFixBrWekTs> result = em.createQuery(query).getResultList();

		Map<WorkTimeCode, List<KshmtWtFixBrWekTs>> mapResttimes = result.stream().collect(
				Collectors.groupingBy(item -> new WorkTimeCode(item.getKshmtWtFixBrWekTsPK().getWorktimeCd())));

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
				root.get(KshmtWtFix_.kshmtWtFixPK).get(KshmtWtFixPK_.cid), companyId));
		predicateList.add(root.get(KshmtWtFix_.kshmtWtFixPK).get(KshmtWtFixPK_.worktimeCd).in(workTimeCodes));
		query.where(predicateList.toArray(new Predicate[] {}));
		List<KshmtWtFix> result = em.createQuery(query).getResultList();
		return result.stream()
				.map(entity -> new FixedWorkSetting(new JpaFixedWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}
}
