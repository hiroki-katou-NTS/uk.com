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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpDeforLarSet_;

/**
 * The Class JpaWkpDeforLaborSettingRepository.
 */
@Stateless
public class JpaWkpDeforLaborSettingRepository extends JpaRepository implements WkpDeforLaborSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSettingRepository#findEmpDeforLaborSettingByCidAndEmpIdAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<WkpDeforLaborSetting> find(String cid, String wkpId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpDeforLarSet> cq = cb.createQuery(KshstWkpDeforLarSet.class);
		Root<KshstWkpDeforLarSet> root = cq.from(KshstWkpDeforLarSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstWkpDeforLarSet_.kshstWkpDeforLarSetPK).get(KshstWkpDeforLarSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstWkpDeforLarSet_.kshstWkpDeforLarSetPK).get(KshstWkpDeforLarSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstWkpDeforLarSet_.kshstWkpDeforLarSetPK).get(KshstWkpDeforLarSetPK_.wkpId), wkpId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSetting)
	 */
	@Override
	public void add(WkpDeforLaborSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSetting)
	 */
	@Override
	public void update(WkpDeforLaborSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpDeforLaborSetting)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstWkpDeforLarSet.class, new KshstWkpDeforLarSetPK(cid, empId, year));
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain defor labor setting
	 */
	private WkpDeforLaborSetting toDomain(KshstWkpDeforLarSet entity) {
		if (entity == null) {
			return null;
		}
		return new WkpDeforLaborSetting(new JpaWkpDeforLaborSettingGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha defor lar set
	 */
	private KshstWkpDeforLarSet toEntity(WkpDeforLaborSetting domain) {
		JpaWkpDeforLaborSettingSetMemento memento = new JpaWkpDeforLaborSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}

}
