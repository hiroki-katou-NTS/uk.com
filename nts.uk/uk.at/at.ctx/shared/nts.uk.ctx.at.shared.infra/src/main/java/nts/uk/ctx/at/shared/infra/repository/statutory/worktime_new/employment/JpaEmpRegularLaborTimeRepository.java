/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpRegLaborTime_;

/**
 * The Class JpaEmpRegularLaborTimeRepository.
 */
@Stateless
public class JpaEmpRegularLaborTimeRepository extends JpaRepository implements EmpRegularWorkTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTime)
	 */
	@Override
	public void add(EmpRegularWorkTime emplRegWorkHour) {
		commandProxy().insert(this.toEntity(emplRegWorkHour));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTime)
	 */
	@Override
	public void update(EmpRegularWorkTime emplRegWorkHour) {
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
	public List<EmpRegularWorkTime> findListByCid(String cid) {
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
	public Optional<EmpRegularWorkTime> findById(String cid, String employmentCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmpRegLaborTime> cq = cb.createQuery(KshstEmpRegLaborTime.class);
		Root<KshstEmpRegLaborTime> root = cq.from(KshstEmpRegLaborTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstEmpRegLaborTime_.kshstEmpRegLaborTimePK).get(KshstEmpRegLaborTimePK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstEmpRegLaborTime_.kshstEmpRegLaborTimePK).get(KshstEmpRegLaborTimePK_.empCd), employmentCode));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 * @return the kshst emp reg labor time
	 */
	private KshstEmpRegLaborTime toEntity(EmpRegularWorkTime emplRegWorkHour) {
		KshstEmpRegLaborTime entity = new KshstEmpRegLaborTime();
		emplRegWorkHour.saveToMemento(new JpaEmpRegularLaborTimeSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<EmpRegularWorkTime> toDomain(List<KshstEmpRegLaborTime> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return entities.stream().map(entity -> new EmpRegularWorkTime(new JpaEmpRegularLaborTimeGetMemento(entity))).collect(Collectors.toList());
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the emp regular work time
	 */
	private EmpRegularWorkTime toDomain(KshstEmpRegLaborTime entity) {
		if (entity == null) {
			return null;
		}
		return new EmpRegularWorkTime(new JpaEmpRegularLaborTimeGetMemento(entity));
	}
}
