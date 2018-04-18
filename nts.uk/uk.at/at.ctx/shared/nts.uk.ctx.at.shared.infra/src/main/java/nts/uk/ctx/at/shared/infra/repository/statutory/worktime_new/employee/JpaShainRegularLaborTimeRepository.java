/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employee;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTime;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTimePK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTimePK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaRegLaborTime_;

/**
 * The Class JpaShainRegularLaborTimeRepository.
 */
@Stateless
public class JpaShainRegularLaborTimeRepository extends JpaRepository implements ShainRegularWorkTimeRepository {

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * ShainRegularWorkTimeRepository#add(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.employeeNew.ShainRegularWorkTime)
	 */
	@Override
	public void add(ShainRegularLaborTime setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * ShainRegularWorkTimeRepository#update(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.employeeNew.ShainRegularWorkTime)
	 */
	@Override
	public void update(ShainRegularLaborTime setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * ShainRegularWorkTimeRepository#delete(nts.uk.ctx.at.shared.dom.statutory.
	 * worktime.employeeNew.ShainRegularWorkTime)
	 */
	@Override
	public void delete(String cid, String empId) {
		commandProxy().remove(KshstShaRegLaborTime.class, new KshstShaRegLaborTimePK(cid, empId));
	}

	/*
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.
	 * ShainRegularWorkTimeRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ShainRegularLaborTime> find(String cid, String empId) {
		Optional<KshstShaRegLaborTime> optEntity = this.queryProxy().find(new KshstShaRegLaborTimePK(cid, empId), KshstShaRegLaborTime.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param emplRegWorkHour
	 *            the empl reg work hour
	 * @return the kshst sha reg labor time
	 */
	private KshstShaRegLaborTime toEntity(ShainRegularLaborTime emplRegWorkHour) {
		KshstShaRegLaborTime entity = new KshstShaRegLaborTime();
		emplRegWorkHour.saveToMemento(new JpaShainRegularLaborTimeSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the shain regular work time
	 */
	private ShainRegularLaborTime toDomain(KshstShaRegLaborTime entity) {
		return new ShainRegularLaborTime(new JpaShainRegularLaborTimeGetMemento(entity));
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<ShainRegularLaborTime> toDomain(List<KshstShaRegLaborTime> entities) {
		if (entities == null || entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> new ShainRegularLaborTime(new JpaShainRegularLaborTimeGetMemento(entity))).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository#findAll(java.lang.String)
	 */
	@Override
	public List<ShainRegularLaborTime> findAll(String cid) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaRegLaborTime> cq = cb.createQuery(KshstShaRegLaborTime.class);
		Root<KshstShaRegLaborTime> root = cq.from(KshstShaRegLaborTime.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList
				.add(cb.equal(root.get(KshstShaRegLaborTime_.kshstShaRegLaborTimePK).get(KshstShaRegLaborTimePK_.cid), cid));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<KshstShaRegLaborTime> resultList = em.createQuery(cq).getResultList();

		return this.toDomain(resultList);
	}
}
