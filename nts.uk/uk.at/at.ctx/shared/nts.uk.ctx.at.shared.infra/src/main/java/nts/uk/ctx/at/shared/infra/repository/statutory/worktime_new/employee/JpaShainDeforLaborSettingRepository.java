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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaDeforLarSet_;

/**
 * The Class JpaShainDeforLaborSettingRepository.
 */
@Stateless
public class JpaShainDeforLaborSettingRepository extends JpaRepository implements ShainDeforLaborSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#findEmpDeforLaborSettingByCidAndEmpIdAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<ShainDeforLaborSetting> find(String cid, String empId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaDeforLarSet> cq = cb.createQuery(KshstShaDeforLarSet.class);
		Root<KshstShaDeforLarSet> root = cq.from(KshstShaDeforLarSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstShaDeforLarSet_.kshstShaDeforLarSetPK).get(KshstShaDeforLarSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstShaDeforLarSet_.kshstShaDeforLarSetPK).get(KshstShaDeforLarSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstShaDeforLarSet_.kshstShaDeforLarSetPK).get(KshstShaDeforLarSetPK_.sid), empId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting)
	 */
	@Override
	public void add(ShainDeforLaborSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting)
	 */
	@Override
	public void update(ShainDeforLaborSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstShaDeforLarSet.class, new KshstShaDeforLarSetPK(cid, empId, year));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain defor labor setting
	 */
	private ShainDeforLaborSetting toDomain(KshstShaDeforLarSet entity) {
		if (entity == null) {
			return null;
		}
		return new ShainDeforLaborSetting(new JpaShainDeforLaborSettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha defor lar set
	 */
	private KshstShaDeforLarSet toEntity(ShainDeforLaborSetting domain) {
		KshstShaDeforLarSet entity = new KshstShaDeforLarSet();
		domain.saveToMemento(new JpaShainDeforLaborSettingSetMemento(entity));
		return entity;
	}

}
