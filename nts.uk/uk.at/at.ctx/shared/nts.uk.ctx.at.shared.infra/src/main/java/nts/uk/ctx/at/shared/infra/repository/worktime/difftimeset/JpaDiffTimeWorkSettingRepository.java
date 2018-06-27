/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.difftimeset;

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
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSetPK;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSetPK_;
import nts.uk.ctx.at.shared.infra.entity.worktime.difftimeset.KshmtDiffTimeWorkSet_;

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

}
