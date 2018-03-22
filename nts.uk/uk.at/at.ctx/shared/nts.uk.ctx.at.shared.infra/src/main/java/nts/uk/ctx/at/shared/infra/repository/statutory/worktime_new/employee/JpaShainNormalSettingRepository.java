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
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.employee.KshstShaNormalSet_;

/**
 * The Class JpaShainNormalSettingRepository.
 */
@Stateless
public class JpaShainNormalSettingRepository extends JpaRepository implements ShainNormalSettingRepository {

	@Override
	public void add(ShainNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}
	@Override
	public void update(ShainNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	@Override
	public void delete(ShainNormalSetting setting) {
		commandProxy().remove(setting);
	}

	@Override
	public Optional<ShainNormalSetting> find(String cid, String empId, Year year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstShaNormalSet> cq = cb.createQuery(KshstShaNormalSet.class);
		Root<KshstShaNormalSet> root = cq.from(KshstShaNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstShaNormalSet_.kshstShaNormalSetPK).get(KshstShaNormalSetPK_.cid), cid));
		predicateList.add(cb.equal(root.get(KshstShaNormalSet_.kshstShaNormalSetPK).get(KshstShaNormalSetPK_.year), year));
		predicateList.add(cb.equal(root.get(KshstShaNormalSet_.kshstShaNormalSetPK).get(KshstShaNormalSetPK_.sid), empId));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getSingleResult()));
	}

	private KshstShaNormalSet toEntity(ShainNormalSetting domain) {
		JpaShainNormalSettingSetMemento memento = new JpaShainNormalSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	private ShainNormalSetting toDomain(KshstShaNormalSet entity) {
		if (entity == null) {
			return null;
		}
		return new ShainNormalSetting(new JpaShainNormalSettingGetMemento(entity));
	}
}
