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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComFlexSet_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComNormalSet;

/**
 * The Class JpaComFlexSettingRepository.
 */
@Stateless
public class JpaComFlexSettingRepository extends JpaRepository implements ComFlexSettingRepository {
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting)
	 */
	@Override
	public void create(ComFlexSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting)
	 */
	@Override
	public void update(ComFlexSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository#remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int year) {
		KshstComFlexSetPK key = new KshstComFlexSetPK();
		key.setCid(companyId);
		key.setYear(year);
		commandProxy().remove(KshstComNormalSet.class, key);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSettingRepository#find(java.lang.String, int)
	 */
	@Override
	public Optional<ComFlexSetting> find(String companyId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstComFlexSet> cq = cb.createQuery(KshstComFlexSet.class);
		Root<KshstComFlexSet> root = cq.from(KshstComFlexSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstComFlexSet_.kshstComFlexSetPK).get(KshstComFlexSetPK_.cid), companyId));
		predicateList.add(cb.equal(root.get(KshstComFlexSet_.kshstComFlexSetPK).get(KshstComFlexSetPK_.year), year));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst com flex set
	 */
	private KshstComFlexSet toEntity(ComFlexSetting domain) {
		JpaComFlexSettingSetMemento memento = new JpaComFlexSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the com flex setting
	 */
	private ComFlexSetting toDomain(List<KshstComFlexSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new ComFlexSetting(new JpaComFlexSettingGetMemento(entities.get(0)));
	}

}
