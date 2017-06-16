/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.company;

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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.company.CompanyWtSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.company.JcwtstCompanyWtSet_;

/**
 * The Class JpaCompanySettingRepository.
 */
@Stateless
public class JpaCompanyWtSettingRepository extends JpaRepository implements CompanyWtSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#create(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.CompanySetting)
	 */
	@Override
	public void create(CompanyWtSetting setting) {
		List<JcwtstCompanyWtSet> entities = this.toEntity(setting);
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
	public void update(CompanyWtSetting setting) {
		List<JcwtstCompanyWtSet> entities = this.toEntity(setting);
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
	public Optional<CompanyWtSetting> find(String companyId, int year) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JcwtstCompanyWtSet> cq = cb.createQuery(JcwtstCompanyWtSet.class);
		Root<JcwtstCompanyWtSet> root = cq.from(JcwtstCompanyWtSet.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(JcwtstCompanyWtSet_.jcwtstCompanyWtSetPK).get(JcwtstCompanyWtSetPK_.cid), companyId));
		predicateList
				.add(cb.equal(root.get(JcwtstCompanyWtSet_.jcwtstCompanyWtSetPK).get(JcwtstCompanyWtSetPK_.yK), year));
		cq.where(predicateList.toArray(new Predicate[] {}));
		//TODO: lay dieu kien start month?

		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the company setting
	 */
	private CompanyWtSetting toDomain(List<JcwtstCompanyWtSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new CompanyWtSetting(new JpaCompanyWtSettingGetMemento(entities));

	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<JcwtstCompanyWtSet> toEntity(CompanyWtSetting domain) {
		List<JcwtstCompanyWtSet> entities = new ArrayList<>();
		JpaCompanyWtSettingSetMemento memento = new JpaCompanyWtSettingSetMemento();
		domain.saveToMemento(memento);
		entities.add(memento.getDeformed());
		entities.add(memento.getFlexSpecified());
		entities.add(memento.getFlexStatutory());
		entities.add(memento.getNormal());
		return entities;
	}

}
