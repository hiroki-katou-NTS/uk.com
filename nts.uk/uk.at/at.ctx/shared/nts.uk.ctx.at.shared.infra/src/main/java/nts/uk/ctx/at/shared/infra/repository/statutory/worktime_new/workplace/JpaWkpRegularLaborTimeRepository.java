/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.ArrayList;
import java.util.Collections;
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
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpRegLaborTime_;

/**
 * The Class JpaWkpRegularLaborTimeRepository.
 */
@Stateless
public class JpaWkpRegularLaborTimeRepository extends JpaRepository implements RegularLaborTimeWkpRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpRegularLaborTime)
	 */
	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * WkpRegularLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.companyNew.WkpRegularLaborTime)
	 */
	@Override
	public void update(RegularLaborTimeWkp setting) {
		KshstWkpRegLaborTime entity = this.queryProxy()
				.find(new KshstWkpRegLaborTimePK(setting.getComId(), setting.getWorkplaceId()),
						KshstWkpRegLaborTime.class)
				.get();

		entity.setDailyTime(setting.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(setting.getWeeklyTime().getTime().v());
		
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<RegularLaborTimeWkp> find(String cid, String wkpId) {
		// Get info
		return this.queryProxy().find(new KshstWkpRegLaborTimePK(cid, wkpId),
				KshstWkpRegLaborTime.class).map(c -> toDomain(c));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpRegularLaborTime)
	 */
	@Override
	public void add(RegularLaborTimeWkp domain) {
		KshstWkpRegLaborTime entity = new KshstWkpRegLaborTime();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshstWkpRegLaborTimePK(new KshstWkpRegLaborTimePK(domain.getComId(), 
													domain.getWorkplaceId()));
		
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpRegularLaborTimeRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		this.commandProxy().remove(KshstWkpRegLaborTime.class, new KshstWkpRegLaborTimePK(cid, wkpId));
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<RegularLaborTimeWkp> toDomain(List<KshstWkpRegLaborTime> entities) {
		if (entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> toDomain(entity)).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<RegularLaborTimeWkp> findAll(String cid) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpRegLaborTime> cq = cb.createQuery(KshstWkpRegLaborTime.class);
		Root<KshstWkpRegLaborTime> root = cq.from(KshstWkpRegLaborTime.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(KshstWkpRegLaborTime_.kshstWkpRegLaborTimePK).get(KshstWkpRegLaborTimePK_.cid), cid));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshstWkpRegLaborTime> resultList = em.createQuery(cq).getResultList();

		return this.toDomain(resultList); 
	}
	
	/**
	 * To domain.
	 *
	 */
	private RegularLaborTimeWkp toDomain(KshstWkpRegLaborTime entity) {
		return RegularLaborTimeWkp.of(entity.getKshstWkpRegLaborTimePK().getCid(),
				entity.getKshstWkpRegLaborTimePK().getCid(),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}
}
