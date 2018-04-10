/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSet_;

/**
 * The Class JpaEmpNormalSettingRepository.
 */
@Stateless
public class JpaEmpNormalSettingRepository extends JpaRepository implements EmpNormalSettingRepository {

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting)
	 */
	@Override
	public void add(EmpNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting)
	 */
	@Override
	public void update(EmpNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting)
	 */
	@Override
	public void delete(String cid, String emplCode, int year) {
		commandProxy().remove(KshstEmpNormalSet.class, new KshstEmpNormalSetPK(cid, emplCode, year));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#findByCidAndEmplCodeAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<EmpNormalSetting> find(String cid, String emplCode, int year) {
		Optional<KshstEmpNormalSet> optEntity = this.queryProxy().find(new KshstEmpNormalSetPK(cid, emplCode, year), KshstEmpNormalSet.class);

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
	 * @return the kshst emp normal set
	 */
	private KshstEmpNormalSet toEntity(EmpNormalSetting domain) {
		KshstEmpNormalSet entity = new KshstEmpNormalSet();
		domain.saveToMemento(new JpaEmpNormalSettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entities
	 * @return the emp normal setting
	 */
	private EmpNormalSetting toDomain(KshstEmpNormalSet entity) {
		return new EmpNormalSetting(new JpaEmpNormalSettingGetMemento(entity));
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the list
	 */
	private List<EmpNormalSetting> toDomain(List<KshstEmpNormalSet> entities) {
		if (entities == null ||entities.isEmpty()) {
			return Collections.emptyList();
		}
		return entities.stream().map(entity -> new EmpNormalSetting(new JpaEmpNormalSettingGetMemento(entity))).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#findList(java.lang.String, java.lang.String)
	 */
	@Override
	public List<EmpNormalSetting> findList(String cid, String emplCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmpNormalSet> cq = cb.createQuery(KshstEmpNormalSet.class);
		Root<KshstEmpNormalSet> root = cq.from(KshstEmpNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstEmpNormalSet_.kshstEmpNormalSetPK).get(KshstEmpNormalSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstEmpNormalSet_.kshstEmpNormalSetPK).get(KshstEmpNormalSetPK_.empCd), emplCode));
		
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		return this.toDomain(em.createQuery(cq).getResultList());	
	}
}
