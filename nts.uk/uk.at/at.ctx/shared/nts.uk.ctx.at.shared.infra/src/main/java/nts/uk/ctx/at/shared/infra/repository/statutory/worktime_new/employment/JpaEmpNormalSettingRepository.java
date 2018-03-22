/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.employment;

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
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpNormalSet;
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
	public void delete(EmpNormalSetting setting) {
		commandProxy().remove(setting);
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository#findByCidAndEmplCodeAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<EmpNormalSetting> find(String cid, String emplCode, Year year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmpNormalSet> cq = cb.createQuery(KshstEmpNormalSet.class);
		Root<KshstEmpNormalSet> root = cq.from(KshstEmpNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstEmpNormalSet_.kshstEmpNormalSetPK).get(KshstEmpNormalSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstEmpNormalSet_.kshstEmpNormalSetPK).get(KshstEmpNormalSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstEmpNormalSet_.kshstEmpNormalSetPK).get(KshstEmpNormalSetPK_.empCd), emplCode));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst emp normal set
	 */
	private KshstEmpNormalSet toEntity(EmpNormalSetting domain) {
		JpaEmpNormalSettingSetMemento memento = new JpaEmpNormalSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entities
	 * @return the emp normal setting
	 */
	private EmpNormalSetting toDomain(KshstEmpNormalSet entity) {
		if (entity == null) {
			return null;
		}
		return new EmpNormalSetting(new JpaEmpNormalSettingGetMemento(entity));
	}
}
