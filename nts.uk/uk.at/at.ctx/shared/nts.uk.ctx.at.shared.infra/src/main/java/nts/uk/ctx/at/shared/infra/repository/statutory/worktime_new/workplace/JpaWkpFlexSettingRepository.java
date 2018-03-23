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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpFlexSet_;

/**
 * The Class JpaWkpFlexSettingRepository.
 */
@Stateless
public class JpaWkpFlexSettingRepository extends JpaRepository implements WkpFlexSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSetting)
	 */
	@Override
	public void add(WkpFlexSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSetting)
	 */
	@Override
	public void update(WkpFlexSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSetting)
	 */
	@Override
	public void delete(String cid, String wkpId, int year) {
		commandProxy().remove(KshstWkpFlexSet.class, new KshstWkpFlexSetPK(cid, wkpId, year));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpFlexSettingRepository#find(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<WkpFlexSetting> find(String cid, String wkpId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpFlexSet> cq = cb.createQuery(KshstWkpFlexSet.class);
		Root<KshstWkpFlexSet> root = cq.from(KshstWkpFlexSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstWkpFlexSet_.kshstWkpFlexSetPK).get(KshstWkpFlexSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstWkpFlexSet_.kshstWkpFlexSetPK).get(KshstWkpFlexSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstWkpFlexSet_.kshstWkpFlexSetPK).get(KshstWkpFlexSetPK_.wkpId), wkpId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha flex set
	 */
	private KshstWkpFlexSet toEntity(WkpFlexSetting domain) {
		JpaWkpFlexSettingSetMemento memento = new JpaWkpFlexSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the shain flex setting
	 */
	private WkpFlexSetting toDomain(KshstWkpFlexSet entities) {
		if (entities == null) {
			return null;
		}
		return new WkpFlexSetting(new JpaWkpFlexSettingGetMemento(entities));
	}

}
