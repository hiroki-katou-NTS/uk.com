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
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet_;

/**
 * The Class JpaWkpNormalSettingRepository.
 */
@Stateless
public class JpaWkpNormalSettingRepository extends JpaRepository implements WkpNormalSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpNormalSetting)
	 */
	@Override
	public void add(WkpNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpNormalSetting)
	 */
	@Override
	public void update(WkpNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpNormalSettingRepository#find(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Optional<WkpNormalSetting> find(String cid, String wkpId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstWkpNormalSet> cq = cb.createQuery(KshstWkpNormalSet.class);
		Root<KshstWkpNormalSet> root = cq.from(KshstWkpNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstWkpNormalSet_.kshstWkpNormalSetPK).get(KshstWkpNormalSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstWkpNormalSet_.kshstWkpNormalSetPK).get(KshstWkpNormalSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstWkpNormalSet_.kshstWkpNormalSetPK).get(KshstWkpNormalSetPK_.wkpId), wkpId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha normal set
	 */
	private KshstWkpNormalSet toEntity(WkpNormalSetting domain) {
		JpaWkpNormalSettingSetMemento memento = new JpaWkpNormalSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain normal setting
	 */
	private WkpNormalSetting toDomain(KshstWkpNormalSet entity) {
		if (entity == null) {
			return null;
		}
		return new WkpNormalSetting(new JpaWkpNormalSettingGetMemento(entity));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.WkpNormalSettingRepository#delete(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstWkpNormalSet.class, new KshstWkpNormalSetPK(cid, empId, year));
	}

	@Override
	public List<WkpNormalSetting> findListByCid(String cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<WkpNormalSetting> findByCidAndWkpId(String cid, String wkpId) {
		// TODO Auto-generated method stub
		return null;
	}
}
