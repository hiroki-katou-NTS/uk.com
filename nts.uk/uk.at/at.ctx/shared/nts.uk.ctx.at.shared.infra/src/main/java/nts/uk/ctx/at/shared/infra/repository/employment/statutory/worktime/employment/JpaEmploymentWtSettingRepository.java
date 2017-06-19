/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.employment;

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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewtstEmploymentWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewtstEmploymentWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewtstEmploymentWtSet_;

/**
 * The Class JpaCompanySettingRepository.
 */
@Stateless
public class JpaEmploymentWtSettingRepository extends JpaRepository implements EmploymentWtSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#create(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.CompanySetting)
	 */
	@Override
	public void create(EmploymentWtSetting setting) {
		List<JewtstEmploymentWtSet> entities = this.toEntity(setting);
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
	public void update(EmploymentWtSetting setting) {
		List<JewtstEmploymentWtSet> entities = this.toEntity(setting);
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
	public Optional<EmploymentWtSetting> find(String companyId, int year) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JewtstEmploymentWtSet> cq = cb.createQuery(JewtstEmploymentWtSet.class);
		Root<JewtstEmploymentWtSet> root = cq.from(JewtstEmploymentWtSet.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(JewtstEmploymentWtSet_.jewtstEmploymentWtSetPK).get(JewtstEmploymentWtSetPK_.cid), companyId));
		predicateList
				.add(cb.equal(root.get(JewtstEmploymentWtSet_.jewtstEmploymentWtSetPK).get(JewtstEmploymentWtSetPK_.yK), year));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the company setting
	 */
	private EmploymentWtSetting toDomain(List<JewtstEmploymentWtSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new EmploymentWtSetting(new JpaEmploymentWtSettingGetMemento(entities));

	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<JewtstEmploymentWtSet> toEntity(EmploymentWtSetting domain) {
		List<JewtstEmploymentWtSet> entities = new ArrayList<>();
		JpaEmploymentWtSettingSetMemento memento = new JpaEmploymentWtSettingSetMemento();
		domain.saveToMemento(memento);
		entities.add(memento.getDeformed());
		entities.add(memento.getFlexSpecified());
		entities.add(memento.getFlexStatutory());
		entities.add(memento.getNormal());
		return entities;
	}

}
