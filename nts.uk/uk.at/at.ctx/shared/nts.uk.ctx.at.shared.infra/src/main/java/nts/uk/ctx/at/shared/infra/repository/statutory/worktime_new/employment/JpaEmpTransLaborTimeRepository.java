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
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegaltimeDRegEmp;
//import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegaltimeDRegEmpPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegaltimeDDefEmp;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshmtLegaltimeDDefEmpPK;

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
		commandProxy().remove(KshmtLegaltimeDDefEmp.class, new KshmtLegaltimeDDefEmpPK(cid, employmentCode));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository#findByCidAndEmplId(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<DeforLaborTimeEmp> find(String cid, String employmentCode) {
		Optional<KshmtLegaltimeDDefEmp> optEntity = this.queryProxy().find(new KshmtLegaltimeDDefEmpPK(cid, employmentCode), KshmtLegaltimeDDefEmp.class);

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
	private KshmtLegaltimeDDefEmp toEntity(DeforLaborTimeEmp domain) {
		KshmtLegaltimeDDefEmp entity = new KshmtLegaltimeDDefEmp();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshmtLegaltimeDDefEmpPK(new KshmtLegaltimeDDefEmpPK(domain.getComId(), 
													domain.getEmploymentCode().v()));
		
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the emp trans work time
	 */
	private DeforLaborTimeEmp toDomain(KshmtLegaltimeDDefEmp entity) {
		return DeforLaborTimeEmp.of(entity.getKshmtLegaltimeDDefEmpPK().getCid(),
				new EmploymentCode(entity.getKshmtLegaltimeDDefEmpPK().getEmpCd()),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}

}
