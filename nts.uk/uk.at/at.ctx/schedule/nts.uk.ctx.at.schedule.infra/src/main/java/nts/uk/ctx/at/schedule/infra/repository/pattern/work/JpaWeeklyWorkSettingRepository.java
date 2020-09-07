/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

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
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSetPK;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWeeklyWorkSet_;

/**
 * The Class JpaWeeklyWorkSettingRepository.
 */
@Stateless
public class JpaWeeklyWorkSettingRepository extends JpaRepository
		implements WeeklyWorkSettingRepository {

	/**
	 * select a KscmtWeeklyWorkSet ALL
	 */
	private static final String SELECT_ALL = "SELECT w FROM KscmtWeeklyWorkSet w";

	private static final String GET_BY_COMPANY_ID = SELECT_ALL + " WHERE w.kscmtWeeklyWorkSetPK.cid = :companyId";

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
				.find(new KscmtWeeklyWorkSetPK(companyId, dayOfWeek), KscmtWeeklyWorkSet.class)
				.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the weekly work setting
	 */
	private WeeklyWorkSetting toDomain(KscmtWeeklyWorkSet entity){
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
		CriteriaQuery<KscmtWeeklyWorkSet> cq = criteriaBuilder
				.createQuery(KscmtWeeklyWorkSet.class);

		// root data
		Root<KscmtWeeklyWorkSet> root = cq.from(KscmtWeeklyWorkSet.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtWeeklyWorkSet_.kscmtWeeklyWorkSetPK).get(KscmtWeeklyWorkSetPK_.cid),
				companyId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscmtWeeklyWorkSet> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());

	}

	@Override
	public WeeklyWorkDayPattern getWeeklyWorkDayPatternByCompanyId(String companyId) {
		List<KscmtWeeklyWorkSet> kscmtWeeklyWorkSets = this.queryProxy().query(
				GET_BY_COMPANY_ID, KscmtWeeklyWorkSet.class)
				.setParameter("companyId", companyId)
				.getList();
		WeeklyWorkDayPattern weeklyWorkDayPattern = KscmtWeeklyWorkSet.listEntitytoDomain(kscmtWeeklyWorkSets);
		return weeklyWorkDayPattern;
	}

	@Override
	public void addWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern) {
		KscmtWeeklyWorkSet.toEntity(weeklyWorkDayPattern).forEach(item -> this.commandProxy().insert(item));
	}

	@Override
	public void updateWeeklyWorkDayPattern(WeeklyWorkDayPattern weeklyWorkDayPattern) {
		KscmtWeeklyWorkSet.toEntity(weeklyWorkDayPattern).forEach(item -> this.commandProxy().update(item));
	}

}
