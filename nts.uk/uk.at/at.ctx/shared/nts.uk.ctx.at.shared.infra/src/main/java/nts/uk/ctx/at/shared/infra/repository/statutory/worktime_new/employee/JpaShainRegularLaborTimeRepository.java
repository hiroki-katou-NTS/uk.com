/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTime_;

/**
 * The Class JpaShainRegularLaborTimeRepository.
 */
@Stateless
public class JpaShainRegularLaborTimeRepository extends JpaRepository implements ShainRegularWorkTimeRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTime)
	 */
	@Override
	public void add(ShainRegularWorkTime setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTime)
	 */
	@Override
	public void update(ShainRegularWorkTime setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTime)
	 */
	@Override
	public void delete(ShainRegularWorkTime setting) {
		commandProxy().remove(setting);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShainRegularWorkTime> find(String cid, String empId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaRegLaborTime> cq = cb.createQuery(KshstShaRegLaborTime.class);
		Root<KshstShaRegLaborTime> root = cq.from(KshstShaRegLaborTime.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(KshstShaRegLaborTime_.kshstShaRegLaborTimePK).get(KshstShaRegLaborTimePK_.cid), cid));
		predicateList.add(cb
				.equal(root.get(KshstShaRegLaborTime_.kshstShaRegLaborTimePK).get(KshstShaRegLaborTimePK_.sid), empId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param emplRegWorkHour the empl reg work hour
	 * @return the kshst sha reg labor time
	 */
	private KshstShaRegLaborTime toEntity(ShainRegularWorkTime emplRegWorkHour) {
		JpaShainRegularLaborTimeSetMemento memento = new JpaShainRegularLaborTimeSetMemento();
		emplRegWorkHour.saveToMemento(memento);
		return memento.getEntity();
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain regular work time
	 */
	private ShainRegularWorkTime toDomain(KshstShaRegLaborTime entity) {
		if (entity == null) {
			return null;
		}
		return new ShainRegularWorkTime(new JpaShainRegularLaborTimeGetMemento(entity));
	}
}
