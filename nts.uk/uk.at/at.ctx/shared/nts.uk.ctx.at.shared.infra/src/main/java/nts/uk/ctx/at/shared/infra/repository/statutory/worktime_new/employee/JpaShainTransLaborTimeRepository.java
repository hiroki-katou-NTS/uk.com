/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaTransLabTimePK;

/**
 * The Class JpaShainTransLaborTimeRepository.
 */
@Stateless
public class JpaShainTransLaborTimeRepository extends JpaRepository implements DeforLaborTimeShaRepo {

	private static final String SELECT_BY_CID = "SELECT c FROM KshstShaTransLabTime c"
			+ " WHERE c.kshstShaTransLabTimePK.cid = :cid";
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void add(DeforLaborTimeSha emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void update(DeforLaborTimeSha emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTime)
	 */
	@Override
	public void delete(String cid, String empId) {
		commandProxy().remove(KshstShaTransLabTime.class, new KshstShaTransLabTimePK(cid, empId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainSpeDeforLaborTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DeforLaborTimeSha> find(String cid, String empId) {
		Optional<KshstShaTransLabTime> optEntity = this.queryProxy().find(new KshstShaTransLabTimePK(cid, empId), KshstShaTransLabTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha trans lab time
	 */
	private KshstShaTransLabTime toEntity(DeforLaborTimeSha domain) {
		KshstShaTransLabTime entity = new KshstShaTransLabTime();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshstShaTransLabTimePK(new KshstShaTransLabTimePK(domain.getComId(), domain.getEmpId()));
		
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain spe defor labor time
	 */
	private DeforLaborTimeSha toDomain(KshstShaTransLabTime entity) {
		return DeforLaborTimeSha.of(entity.getKshstShaTransLabTimePK().getCid(),
				entity.getKshstShaTransLabTimePK().getSid(),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}

	@Override
	public List<DeforLaborTimeSha> findDeforLaborTimeShaByCid(String cid) {
		List<KshstShaTransLabTime> entitys = this.queryProxy().query(SELECT_BY_CID, KshstShaTransLabTime.class)
				.setParameter("cid", cid).getList();
		return entitys.stream().map(m -> {
			return toDomain(m);
		}).collect(Collectors.toList());
	}
}
