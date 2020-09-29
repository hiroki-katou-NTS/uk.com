/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

import java.util.Optional;

import javax.ejb.Stateless;
//import javax.persistence.EntityManager;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.common.TimeOfDay;
import nts.uk.ctx.at.shared.dom.common.WeeklyTime;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.DailyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.WeeklyUnit;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpTransLabTimePK;

/**
 * The Class JpaEmpTransLaborTimeRepository.
 */
@Stateless
public class JpaEmpTransLaborTimeRepository extends JpaRepository implements DeforLaborTimeEmpRepo {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void add(DeforLaborTimeEmp emplDeforLaborWorkingHour) {
		commandProxy().insert(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void update(DeforLaborTimeEmp emplDeforLaborWorkingHour) {
		commandProxy().update(this.toEntity(emplDeforLaborWorkingHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTime)
	 */
	@Override
	public void delete(String cid, String employmentCode) {
		commandProxy().remove(KshstEmpTransLabTime.class, new KshstEmpTransLabTimePK(cid, employmentCode));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#findByCidAndEmplId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DeforLaborTimeEmp> find(String cid, String employmentCode) {
		Optional<KshstEmpTransLabTime> optEntity = this.queryProxy().find(new KshstEmpTransLabTimePK(cid, employmentCode), KshstEmpTransLabTime.class);

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
	 * @return the kshst emp trans lab time
	 */
	private KshstEmpTransLabTime toEntity(DeforLaborTimeEmp domain) {
		KshstEmpTransLabTime entity = new KshstEmpTransLabTime();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshstEmpTransLabTimePK(new KshstEmpTransLabTimePK(domain.getComId(), 
													domain.getEmploymentCode().v()));
		
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the emp trans work time
	 */
	private DeforLaborTimeEmp toDomain(KshstEmpTransLabTime entity) {
		return DeforLaborTimeEmp.of(entity.getKshstEmpTransLabTimePK().getCid(),
				new EmploymentCode(entity.getKshstEmpTransLabTimePK().getEmpCd()),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}

}
