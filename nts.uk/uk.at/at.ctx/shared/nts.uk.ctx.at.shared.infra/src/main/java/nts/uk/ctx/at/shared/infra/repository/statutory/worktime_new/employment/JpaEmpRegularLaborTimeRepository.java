/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

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
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime_;

/**
 * The Class JpaEmpRegularLaborTimeRepository.
 */
@Stateless
public class JpaEmpRegularLaborTimeRepository extends JpaRepository implements RegularLaborTimeEmpRepo {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTime)
	 */
	@Override
	public void add(RegularLaborTimeEmp emplRegWorkHour) {
		commandProxy().insert(this.toEntity(emplRegWorkHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTime)
	 */
	@Override
	public void update(RegularLaborTimeEmp emplRegWorkHour) {
		commandProxy().update(this.toEntity(emplRegWorkHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTime)
	 */
	@Override
	public void delete(String cid, String employmentCode) {
		commandProxy().remove(KshstEmpRegLaborTime.class, new KshstEmpRegLaborTimePK(cid, employmentCode));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#findListByCid(java.lang.String)
	 */
	@Override
	public List<RegularLaborTimeEmp> findListByCid(String cid) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmpRegLaborTime> cq = cb.createQuery(KshstEmpRegLaborTime.class);
		Root<KshstEmpRegLaborTime> root = cq.from(KshstEmpRegLaborTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstEmpRegLaborTime_.kshstEmpRegLaborTimePK).get(KshstEmpRegLaborTimePK_.cid), cid));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return this.toDomain(em.createQuery(cq).getResultList());	
	}
	

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<RegularLaborTimeEmp> findById(String cid, String employmentCode) {
		Optional<KshstEmpRegLaborTime> optEntity = this.queryProxy().find(new KshstEmpRegLaborTimePK(cid, employmentCode), KshstEmpRegLaborTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}
		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 * @return the kshst emp reg labor time
	 */
	private KshstEmpRegLaborTime toEntity(RegularLaborTimeEmp domain) {
		KshstEmpRegLaborTime entity = new KshstEmpRegLaborTime();

		entity.setDailyTime(domain.getDailyTime().getDailyTime().v());
		entity.setWeeklyTime(domain.getWeeklyTime().getTime().v());
		entity.setKshstEmpRegLaborTimePK(new KshstEmpRegLaborTimePK(domain.getComId(), 
													domain.getEmploymentCode().v()));
		
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<RegularLaborTimeEmp> toDomain(List<KshstEmpRegLaborTime> entities) {
		if (entities == null ||entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> toDomain(entity)).collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the emp regular work time
	 */
	private RegularLaborTimeEmp toDomain(KshstEmpRegLaborTime entity) {
		return RegularLaborTimeEmp.of(entity.getKshstEmpRegLaborTimePK().getCid(),
				new EmploymentCode(entity.getKshstEmpRegLaborTimePK().getEmpCd()),
				new WeeklyUnit(new WeeklyTime(entity.getWeeklyTime())), 
				new DailyUnit(new TimeOfDay(entity.getDailyTime())));
	}
}
