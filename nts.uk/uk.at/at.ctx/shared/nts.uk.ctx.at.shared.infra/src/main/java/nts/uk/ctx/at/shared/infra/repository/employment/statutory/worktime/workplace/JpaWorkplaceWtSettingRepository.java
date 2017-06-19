/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.workplace;

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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSet_;

/**
 * The Class JpaWorkplaceWtSettingRepository.
 */
@Stateless
public class JpaWorkplaceWtSettingRepository extends JpaRepository implements WorkPlaceWtSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#create(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.CompanySetting)
	 */
	@Override
	public void create(WorkPlaceWtSetting setting) {
		List<JwpwtstWorkplaceWtSet> entities = this.toEntity(setting);
		commandProxy().insertAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#update(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.CompanySetting)
	 */
	@Override
	public void update(WorkPlaceWtSetting setting) {
		List<JwpwtstWorkplaceWtSet> entities = this.toEntity(setting);
		commandProxy().updateAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId, int year) {
		this.commandProxy().remove(JcwtstCompanyWtSet.class, new JcwtstCompanyWtSetPK(companyId, year, 0, 0));
		this.commandProxy().remove(JcwtstCompanyWtSet.class, new JcwtstCompanyWtSetPK(companyId, year, 1, 0));
		this.commandProxy().remove(JcwtstCompanyWtSet.class, new JcwtstCompanyWtSetPK(companyId, year, 1, 1));
		this.commandProxy().remove(JcwtstCompanyWtSet.class, new JcwtstCompanyWtSetPK(companyId, year, 2, 0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#find(java.lang.String)
	 */
	@Override
	public Optional<WorkPlaceWtSetting> find(String companyId, int year) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JwpwtstWorkplaceWtSet> cq = cb.createQuery(JwpwtstWorkplaceWtSet.class);
		Root<JwpwtstWorkplaceWtSet> root = cq.from(JwpwtstWorkplaceWtSet.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(
				root.get(JwpwtstWorkplaceWtSet_.jwpwtstWorkplaceWtSetPK).get(JwpwtstWorkplaceWtSetPK_.cid), companyId));
		predicateList.add(cb.equal(
				root.get(JwpwtstWorkplaceWtSet_.jwpwtstWorkplaceWtSetPK).get(JwpwtstWorkplaceWtSetPK_.yK), year));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the work place wt setting
	 */
	private WorkPlaceWtSetting toDomain(List<JwpwtstWorkplaceWtSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new WorkPlaceWtSetting(new JpaWorkplaceWtSettingGetMemento(entities));

	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<JwpwtstWorkplaceWtSet> toEntity(WorkPlaceWtSetting domain) {
		List<JwpwtstWorkplaceWtSet> entities = new ArrayList<>();
		JpaWorkplaceWtSettingSetMemento memento = new JpaWorkplaceWtSettingSetMemento();
		domain.saveToMemento(memento);
		entities.add(memento.getDeformed());
		entities.add(memento.getFlexSpecified());
		entities.add(memento.getFlexStatutory());
		entities.add(memento.getNormal());
		return entities;
	}

}
