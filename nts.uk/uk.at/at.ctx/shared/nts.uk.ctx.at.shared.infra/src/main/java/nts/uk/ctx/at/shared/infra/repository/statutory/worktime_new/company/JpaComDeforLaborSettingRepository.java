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
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComDeforLarSet_;

/**
 * The Class JpaComDeforLaborSettingRepository.
 */
@Stateless
public class JpaComDeforLaborSettingRepository extends JpaRepository
		implements ComDeforLaborSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository#create(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting)
	 */
	@Override
	public void create(ComDeforLaborSetting setting) {
		commandProxy().insert(this.toEntity(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository#update(nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting)
	 */
	@Override
	public void update(ComDeforLaborSetting setting) {
		commandProxy().update(this.toEntity(setting));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository#remove(java.lang.String, int)
	 */
	@Override
	public void remove(String companyId, int year) {
		KshstComDeforLarSetPK key = new KshstComDeforLarSetPK();
		key.setCid(companyId);
		key.setYear(year);
		commandProxy().remove(KshstComDeforLarSetPK.class, key);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSettingRepository#find(java.lang.String, int)
	 */
	@Override
	public Optional<ComDeforLaborSetting> find(String companyId, int year) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<KshstComDeforLarSet> cq = cb.createQuery(KshstComDeforLarSet.class);
		Root<KshstComDeforLarSet> root = cq.from(KshstComDeforLarSet.class);

		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(root.get(KshstComDeforLarSet_.kshstComDeforLarSetPK).get(KshstComDeforLarSetPK_.cid), companyId));
		predicateList.add(cb.equal(root.get(KshstComDeforLarSet_.kshstComDeforLarSetPK).get(KshstComDeforLarSetPK_.year), year));

		cq.where(predicateList.toArray(new Predicate[] {}));
		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}


	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst com defor lar set
	 */
	private KshstComDeforLarSet toEntity(ComDeforLaborSetting domain) {
		JpaComDeforLaborSettingSetMemento memento = new JpaComDeforLaborSettingSetMemento();
		domain.saveToMemento(memento);
		return memento.getEntity();
	}
	
	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the com defor labor setting
	 */
	private ComDeforLaborSetting toDomain(List<KshstComDeforLarSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new ComDeforLaborSetting(new JpaComDeforLaborSettingGetMemento(entities.get(0)));
	}
}
