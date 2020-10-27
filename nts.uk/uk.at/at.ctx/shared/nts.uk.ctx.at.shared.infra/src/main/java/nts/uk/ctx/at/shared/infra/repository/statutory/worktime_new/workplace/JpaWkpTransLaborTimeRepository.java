/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshmtLegaltimeDDefWkp;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshmtLegaltimeDDefWkpPK;

/**
 * The Class JpaWkpTransLaborTimeRepository.
 */
@Stateless
public class JpaWkpTransLaborTimeRepository extends JpaRepository
		implements DeforLaborTimeWkpRepo {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DeforLaborTimeWkp> find(String cid, String wkpId) {
		// Get info
		Optional<KshmtLegaltimeDDefWkp> optEntity = this.queryProxy()
				.find(new KshmtLegaltimeDDefWkpPK(cid, wkpId), KshmtLegaltimeDDefWkp.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		// Return
		return Optional.of(toDomain(optEntity.get()));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpTransLaborTime)
	 */
	@Override
	public void add(DeforLaborTimeWkp domain) {
		KshmtLegaltimeDDefWkp entity = new KshmtLegaltimeDDefWkp();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshmtLegaltimeDDefWkpPK(new KshmtLegaltimeDDefWkpPK(domain.getComId(), 
													domain.getWorkplaceId()));
		
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String cid, String wkpId) {
		this.commandProxy().remove(KshmtLegaltimeDDefWkp.class,
				new KshmtLegaltimeDDefWkpPK(cid, wkpId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.workplaceNew.WkpTransLaborTime)
	 */
	@Override
	public void update(DeforLaborTimeWkp domain) {
		KshmtLegaltimeDDefWkp entity = this.queryProxy().find(
				new KshmtLegaltimeDDefWkpPK(domain.getComId(), domain.getWorkplaceId()),
				KshmtLegaltimeDDefWkp.class).get();
		
		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		
		this.commandProxy().update(entity);
	}
	
	/**
	 * To domain.
	 *
	 */
	private DeforLaborTimeWkp toDomain(KshmtLegaltimeDDefWkp entity) {
		return DeforLaborTimeWkp.of(entity.getKshmtLegaltimeDDefWkpPK().getCid(),
				entity.getKshmtLegaltimeDDefWkpPK().getCid(),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}
}
