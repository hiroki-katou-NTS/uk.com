/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSet;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime.company.KcwstCompanyWtSet_;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaCompanyWtSettingRepository.
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
		List<KcwstCompanyWtSet> entities = this.toEntity(setting);
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
		List<KcwstCompanyWtSet> entities = this.toEntity(setting);
		commandProxy()
				.updateAll(entities.stream().map(entity -> this.updateEntity(entity)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String companyId, int year) {
		this.commandProxy().remove(KcwstCompanyWtSet.class,
				new KcwstCompanyWtSetPK(companyId, year, WtSettingConstant.NORMAL, WtSettingConstant.STATUTORY));
		this.commandProxy().remove(KcwstCompanyWtSet.class,
				new KcwstCompanyWtSetPK(companyId, year, WtSettingConstant.FLEX, WtSettingConstant.STATUTORY));
		this.commandProxy().remove(KcwstCompanyWtSet.class,
				new KcwstCompanyWtSetPK(companyId, year, WtSettingConstant.FLEX, WtSettingConstant.SPECIFIED));
		this.commandProxy().remove(KcwstCompanyWtSet.class,
				new KcwstCompanyWtSetPK(companyId, year, WtSettingConstant.DEFORMED, WtSettingConstant.STATUTORY));
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
		CriteriaQuery<KcwstCompanyWtSet> cq = cb.createQuery(KcwstCompanyWtSet.class);
		Root<KcwstCompanyWtSet> root = cq.from(KcwstCompanyWtSet.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(
				cb.equal(root.get(KcwstCompanyWtSet_.jcwstCompanyWtSetPK).get(KcwstCompanyWtSetPK_.cid), companyId));
		predicateList
				.add(cb.equal(root.get(KcwstCompanyWtSet_.jcwstCompanyWtSetPK).get(KcwstCompanyWtSetPK_.yK), year));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the company wt setting
	 */
	private CompanyWtSetting toDomain(List<KcwstCompanyWtSet> entities) {
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
	private List<KcwstCompanyWtSet> toEntity(CompanyWtSetting domain) {
		List<KcwstCompanyWtSet> entities = new ArrayList<KcwstCompanyWtSet>();
		JpaCompanyWtSettingSetMemento memento = new JpaCompanyWtSettingSetMemento();
		domain.saveToMemento(memento);
		entities.add(memento.getDeformed());
		entities.add(memento.getFlexSpecified());
		entities.add(memento.getFlexStatutory());
		entities.add(memento.getNormal());
		return entities;
	}

	/**
	 * Update entity.
	 *
	 * @param entity the entity
	 * @return the jcwtst company wt set
	 */
	private KcwstCompanyWtSet updateEntity(KcwstCompanyWtSet entity) {
		KcwstCompanyWtSet updatedEntity = this.queryProxy().find(entity.getJcwstCompanyWtSetPK(), KcwstCompanyWtSet.class).get();
		updatedEntity.setDailyTime(entity.getDailyTime());
		updatedEntity.setWeeklyTime(entity.getWeeklyTime());
		updatedEntity.setStrWeek(entity.getStrWeek());
		updatedEntity.setJanTime(entity.getJanTime());
		updatedEntity.setFebTime(entity.getFebTime());
		updatedEntity.setMarTime(entity.getMarTime());
		updatedEntity.setAprTime(entity.getAprTime());
		updatedEntity.setMayTime(entity.getMayTime());
		updatedEntity.setJunTime(entity.getJunTime());
		updatedEntity.setJulTime(entity.getJulTime());
		updatedEntity.setAugTime(entity.getAugTime());
		updatedEntity.setSepTime(entity.getSepTime());
		updatedEntity.setOctTime(entity.getOctTime());
		updatedEntity.setNovTime(entity.getNovTime());
		updatedEntity.setDecTime(entity.getDecTime());
		return updatedEntity;
	}

}
