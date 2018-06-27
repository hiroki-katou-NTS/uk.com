/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.fixedset.KshmtFixedWorkSet_;
import nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave.JpaNursingLeaveSettingGetMemento;

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
		this.commandProxy().update(this.toEntity(domain));
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
		this.commandProxy().remove(KshmtFixedWorkSet.class, new KshmtFixedWorkSetPK(companyId, workTimeCode));
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
		Optional<KshmtFixedWorkSet> optionalEntityTimeSet = this.queryProxy()
				.find(new KshmtFixedWorkSetPK(companyId, workTimeCode), KshmtFixedWorkSet.class);

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
	private KshmtFixedWorkSet toEntity(FixedWorkSetting domain) {
		// Find entity
		Optional<KshmtFixedWorkSet> optional = this.queryProxy().find(
				new KshmtFixedWorkSetPK(domain.getCompanyId(), domain.getWorkTimeCode().v()), KshmtFixedWorkSet.class);

		KshmtFixedWorkSet entity;
		// check existed
		if (optional.isPresent()) {
			entity = optional.get();
		} else {
			entity = new KshmtFixedWorkSet();
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
		CriteriaQuery<KshmtFixedWorkSet> query = builder.createQuery(KshmtFixedWorkSet.class);
		Root<KshmtFixedWorkSet> root = query.from(KshmtFixedWorkSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(
				root.get(KshmtFixedWorkSet_.kshmtFixedWorkSetPK).get(KshmtFixedWorkSetPK_.cid),
				companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KshmtFixedWorkSet> result = em.createQuery(query).getResultList();

		return result.stream()
				.map(entity -> new FixedWorkSetting(new JpaFixedWorkSettingGetMemento(entity)))
				.collect(Collectors.toList());
	}
}
