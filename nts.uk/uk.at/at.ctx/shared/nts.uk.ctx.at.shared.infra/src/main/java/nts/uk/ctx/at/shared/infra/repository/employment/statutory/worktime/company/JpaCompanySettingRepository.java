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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.CompanySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.JcwtstCompanyWtSet_;

/**
 * The Class JpaCompanySettingRepository.
 */
@Stateless
public class JpaCompanySettingRepository extends JpaRepository implements CompanySettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#create(nts.uk.ctx.at.shared.dom.employment.
	 * statutory.worktime.CompanySetting)
	 */
	@Override
	public void create(CompanySetting setting) {
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
	public void update(CompanySetting setting) {
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
	public Optional<CompanySetting> find(String companyId, int year) {
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
	private CompanySetting toDomain(List<JcwtstCompanyWtSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new CompanySetting(new JpaCompanySettingGetMemento(entities));

	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<JcwtstCompanyWtSet> toEntity(CompanySetting domain) {
		List<JcwtstCompanyWtSet> entities = new ArrayList<>();
		JpaCompanySettingSetMemento memento = new JpaCompanySettingSetMemento();
		domain.saveToMemento(memento);
		entities.add(memento.getDeformed());
		entities.add(memento.getFlexSpecified());
		entities.add(memento.getFlexStatutory());
		entities.add(memento.getNormal());
		return entities;
	}

}
