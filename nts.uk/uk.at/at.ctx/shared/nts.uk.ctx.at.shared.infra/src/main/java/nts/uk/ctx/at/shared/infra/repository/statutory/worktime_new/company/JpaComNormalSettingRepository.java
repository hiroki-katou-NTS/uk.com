/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSet_;

/**
 * The Class JpaCompanyWtSettingRepository.
 */
@Stateless
public class JpaComNormalSettingRepository extends JpaRepository
		implements ComNormalSettingRepository {

	@Override
	public void create(ComNormalSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	@Override
	public void update(ComNormalSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	@Override
	public void remove(String companyId, int year) {
		KshstComNormalSetPK key = new KshstComNormalSetPK();
		key.setCid(companyId);
		key.setYear(year);
		commandProxy().remove(KshstComNormalSet.class, key);
	}

	@Override
	public Optional<ComNormalSetting> find(String companyId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstComNormalSet> cq = cb.createQuery(KshstComNormalSet.class);
		Root<KshstComNormalSet> root = cq.from(KshstComNormalSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstComNormalSet_.kshstComNormalSetPK).get(KshstComNormalSetPK_.cid), companyId));
		predicateList.add(cb.equal(root.get(KshstComNormalSet_.kshstComNormalSetPK).get(KshstComNormalSetPK_.year), year));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	private KshstComNormalSet toEntity(ComNormalSetting domain) {
		JpaComNormalSettingSetMemento memento = new JpaComNormalSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	private ComNormalSetting toDomain(List<KshstComNormalSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new ComNormalSetting(new JpaComNormalSettingGetMemento(entities.get(0)));
	}
}
