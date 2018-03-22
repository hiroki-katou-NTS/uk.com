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
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employment.KshstEmpFlexSet_;

/**
 * The Class JpaEmpFlexSettingRepository.
 */
@Stateless
public class JpaEmpFlexSettingRepository extends JpaRepository implements EmpFlexSettingRepository {
	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#add(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting)
	 */
	@Override
	public void add(EmpFlexSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting)
	 */
	@Override
	public void update(EmpFlexSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#delete(nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting)
	 */
	@Override
	public void delete(EmpFlexSetting setting) {
		commandProxy().remove(setting);
	}

	
	/* 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSettingRepository#findByCidAndEmplCodeAndYear(java.lang.String, java.lang.String, nts.uk.ctx.at.shared.dom.common.Year)
	 */
	@Override
	public Optional<EmpFlexSetting> findByCidAndEmplCodeAndYear(String cid, String emplCode, Year year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstEmpFlexSet> cq = cb.createQuery(KshstEmpFlexSet.class);
		Root<KshstEmpFlexSet> root = cq.from(KshstEmpFlexSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstEmpFlexSet_.kshstEmpFlexSetPK).get(KshstEmpFlexSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstEmpFlexSet_.kshstEmpFlexSetPK).get(KshstEmpFlexSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstEmpFlexSet_.kshstEmpFlexSetPK).get(KshstEmpFlexSetPK_.empCd), emplCode));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst emp flex set
	 */
	private KshstEmpFlexSet toEntity(EmpFlexSetting domain) {
		JpaEmpFlexSettingSetMemento memento = new JpaEmpFlexSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the emp flex setting
	 */
	private EmpFlexSetting toDomain(List<KshstEmpFlexSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new EmpFlexSetting(new JpaEmpFlexSettingGetMemento(entities.get(0)));
	}

}
