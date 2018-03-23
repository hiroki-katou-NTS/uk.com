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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaFlexSet_;

/**
 * The Class JpaShainFlexSettingRepository.
 */
@Stateless
public class JpaShainFlexSettingRepository extends JpaRepository implements ShainFlexSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting)
	 */
	@Override
	public void add(ShainFlexSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting)
	 */
	@Override
	public void update(ShainFlexSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstShaFlexSet.class, new KshstShaFlexSetPK(cid, empId, year));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSettingRepository#find(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<ShainFlexSetting> find(String cid, String empId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaFlexSet> cq = cb.createQuery(KshstShaFlexSet.class);
		Root<KshstShaFlexSet> root = cq.from(KshstShaFlexSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstShaFlexSet_.kshstShaFlexSetPK).get(KshstShaFlexSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstShaFlexSet_.kshstShaFlexSetPK).get(KshstShaFlexSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstShaFlexSet_.kshstShaFlexSetPK).get(KshstShaFlexSetPK_.sid), empId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha flex set
	 */
	private KshstShaFlexSet toEntity(ShainFlexSetting domain) {
		JpaShainFlexSettingSetMemento memento = new JpaShainFlexSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the shain flex setting
	 */
	private ShainFlexSetting toDomain(KshstShaFlexSet entities) {
		if (entities == null) {
			return null;
		}
		return new ShainFlexSetting(new JpaShainFlexSettingGetMemento(entities));
	}

}
