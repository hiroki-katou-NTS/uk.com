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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSet_;

/**
 * The Class JpaShainNormalSettingRepository.
 */
@Stateless
public class JpaShainNormalSettingRepository extends JpaRepository implements ShainNormalSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting)
	 */
	@Override
	public void add(ShainNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting)
	 */
	@Override
	public void update(ShainNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#find(java.lang.String, java.lang.String, int)
	 */
	@Override
	public Optional<ShainNormalSetting> find(String cid, String empId, int year) {
		Optional<KshstShaNormalSet> optEntity = this.queryProxy().find(new KshstShaNormalSetPK(cid, empId, year), KshstShaNormalSet.class);

		// Check exist
		if (!optEntity.isPresent()) {
			return Optional.empty();
		}

		return Optional.ofNullable(this.toDomain(optEntity.get()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst sha normal set
	 */
	private KshstShaNormalSet toEntity(ShainNormalSetting domain) {
		KshstShaNormalSet entity = new KshstShaNormalSet();
		domain.saveToMemento(new JpaShainNormalSettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the shain normal setting
	 */
	private ShainNormalSetting toDomain(KshstShaNormalSet entity) {
		return new ShainNormalSetting(new JpaShainNormalSettingGetMemento(entity));
	}
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#delete(java.lang.String, java.lang.String, int)
	 */
	@Override
	public void delete(String cid, String empId, int year) {
		commandProxy().remove(KshstShaNormalSet.class, new KshstShaNormalSetPK(cid, empId, year));
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<ShainNormalSetting> toDomain(List<KshstShaNormalSet> entities) {
		if (entities == null ||entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> new ShainNormalSetting(new JpaShainNormalSettingGetMemento(entity))).collect(Collectors.toList());
	}	
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository#findList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<ShainNormalSetting> findList(String cid, String empId) {
		
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaNormalSet> cq = cb.createQuery(KshstShaNormalSet.class);
		Root<KshstShaNormalSet> root = cq.from(KshstShaNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstShaNormalSet_.kshstShaNormalSetPK).get(KshstShaNormalSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstShaNormalSet_.kshstShaNormalSetPK).get(KshstShaNormalSetPK_.sid), empId));
		
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		return this.toDomain(em.createQuery(cq).getResultList());	
	}
}
