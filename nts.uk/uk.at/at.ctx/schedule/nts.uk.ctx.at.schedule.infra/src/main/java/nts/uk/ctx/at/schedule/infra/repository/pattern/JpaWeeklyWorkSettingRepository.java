/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwwstWeeklyWorkSet_;

/**
 * The Class JpaWeeklyWorkSettingRepository.
 */
@Stateless
public class JpaWeeklyWorkSettingRepository extends JpaRepository
		implements WeeklyWorkSettingRepository {

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository
	 * #findById(java.lang.String, int)
	 */
	@Override
	public Optional<WeeklyWorkSetting> findById(String companyId, int dayOfWeek) {
		return this.queryProxy()
				.find(new KwwstWeeklyWorkSetPK(companyId, dayOfWeek), KwwstWeeklyWorkSet.class)
				.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the weekly work setting
	 */
	private WeeklyWorkSetting toDomain(KwwstWeeklyWorkSet entity){
		return new WeeklyWorkSetting(new JpaWeeklyWorkSettingGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingRepository
	 * #findAll(java.lang.String)
	 */
	@Override
	public List<WeeklyWorkSetting> findAll(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KWWST_WEEKLY_WORK_SET (KwwstWeeklyWorkSet SQL)
		CriteriaQuery<KwwstWeeklyWorkSet> cq = criteriaBuilder
				.createQuery(KwwstWeeklyWorkSet.class);

		// root data
		Root<KwwstWeeklyWorkSet> root = cq.from(KwwstWeeklyWorkSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KwwstWeeklyWorkSet_.kwwstWeeklyWorkSetPK).get(KwwstWeeklyWorkSetPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KwwstWeeklyWorkSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());

	}

}
