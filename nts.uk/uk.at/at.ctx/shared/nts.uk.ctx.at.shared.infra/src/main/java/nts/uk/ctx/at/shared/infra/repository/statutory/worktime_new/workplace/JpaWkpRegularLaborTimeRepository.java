/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstWkpRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstWkpRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstWkpRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstWkpRegLaborTime_;

/**
 * The Class JpaWkpRegularLaborTimeRepository.
 */
@Stateless
public class JpaWkpRegularLaborTimeRepository extends JpaRepository implements WkpRegularWorkTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTime)
	 */
	@Override
	public void add(WkpRegularWorkTime setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTime)
	 */
	@Override
	public void update(WkpRegularWorkTime setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTime)
	 */
	@Override
	public void delete(String cid, String empId) {
		commandProxy().remove(KshstWkpRegLaborTime.class, new KshstWkpRegLaborTimePK(cid, empId));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpRegularWorkTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<WkpRegularWorkTime> find(String cid, String empId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpRegLaborTime> cq = cb.createQuery(KshstWkpRegLaborTime.class);
		Root<KshstWkpRegLaborTime> root = cq.from(KshstWkpRegLaborTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(KshstWkpRegLaborTime_.kshstWkpRegLaborTimePK).get(KshstWkpRegLaborTimePK_.cid), cid));
		predicateList.add(cb
				.equal(root.get(KshstWkpRegLaborTime_.kshstWkpRegLaborTimePK).get(KshstWkpRegLaborTimePK_.sid), empId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 * @return the kshst sha reg labor time
	 */
	private KshstWkpRegLaborTime toEntity(WkpRegularWorkTime emplRegWorkHour) {
		JpaWkpRegularLaborTimeSetMemento memento = new JpaWkpRegularLaborTimeSetMemento();
		emplRegWorkHour.saveToMemento(memento);
		return memento.getEntity();
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain regular work time
	 */
	private WkpRegularWorkTime toDomain(KshstWkpRegLaborTime entity) {
		if (entity == null) {
			return null;
		}
		return new WkpRegularWorkTime(new JpaWkpRegularLaborTimeGetMemento(entity));
	}
}
