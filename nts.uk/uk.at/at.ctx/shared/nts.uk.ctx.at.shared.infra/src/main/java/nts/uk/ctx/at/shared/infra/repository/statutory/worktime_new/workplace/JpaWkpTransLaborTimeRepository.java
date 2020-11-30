/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpTransLabTimePK;

/**
 * The Class JpaWkpTransLaborTimeRepository.
 */
@Stateless
public class JpaWkpTransLaborTimeRepository extends JpaRepository
		implements DeforLaborTimeWkpRepo {
	
	private static final String SELECT_BY_CID = "SELECT c FROM KshstWkpTransLabTime c"
			+ " WHERE c.kshstWkpTransLabTimePK.cid = :cid";

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpTransLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DeforLaborTimeWkp> find(String cid, String wkpId) {
		// Get info
		Optional<KshstWkpTransLabTime> optEntity = this.queryProxy()
				.find(new KshstWkpTransLabTimePK(cid, wkpId), KshstWkpTransLabTime.class);

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
		KshstWkpTransLabTime entity = new KshstWkpTransLabTime();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshstWkpTransLabTimePK(new KshstWkpTransLabTimePK(domain.getComId(), 
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
		this.commandProxy().remove(KshstWkpTransLabTime.class,
				new KshstWkpTransLabTimePK(cid, wkpId));
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
		KshstWkpTransLabTime entity = this.queryProxy().find(
				new KshstWkpTransLabTimePK(domain.getComId(), domain.getWorkplaceId()),
				KshstWkpTransLabTime.class).get();
		
		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		
		this.commandProxy().update(entity);
	}
	
	/**
	 * To domain.
	 *
	 */
	private DeforLaborTimeWkp toDomain(KshstWkpTransLabTime entity) {
		return DeforLaborTimeWkp.of(entity.getKshstWkpTransLabTimePK().getCid(),
				entity.getKshstWkpTransLabTimePK().getCid(),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}

	@Override
	public List<DeforLaborTimeWkp> findDeforLaborTimeWkpByCid(String cid) {
		List<KshstWkpTransLabTime> entitys = this.queryProxy().query(SELECT_BY_CID, KshstWkpTransLabTime.class)
				.setParameter("cid", cid).getList();
		
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}
}
