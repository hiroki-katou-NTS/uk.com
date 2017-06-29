/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.employment;

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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewstEmploymentWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewstEmploymentWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewstEmploymentWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.employment.JewstEmploymentWtSet_;
import nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.WtSettingConstant;

/**
 * The Class JpaEmploymentWtSettingRepository.
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
		List<JewstEmploymentWtSet> entities = this.toEntity(setting);
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
		List<JewstEmploymentWtSet> entities = this.toEntity(setting);
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
	public void remove(String companyId, int year, String employmentCode) {
		this.commandProxy().remove(JewstEmploymentWtSet.class,
				new JewstEmploymentWtSetPK(companyId, year, WtSettingConstant.NORMAL, WtSettingConstant.STATUTORY, employmentCode));
		this.commandProxy().remove(JewstEmploymentWtSet.class,
				new JewstEmploymentWtSetPK(companyId, year, WtSettingConstant.FLEX, WtSettingConstant.STATUTORY, employmentCode));
		this.commandProxy().remove(JewstEmploymentWtSet.class,
				new JewstEmploymentWtSetPK(companyId, year, WtSettingConstant.FLEX, WtSettingConstant.SPECIFIED, employmentCode));
		this.commandProxy().remove(JewstEmploymentWtSet.class,
				new JewstEmploymentWtSetPK(companyId, year, WtSettingConstant.DEFORMED, WtSettingConstant.STATUTORY, employmentCode));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingRepository#find(java.lang.String, int, java.lang.String)
	 */
	@Override
	public Optional<EmploymentWtSetting> find(String companyId, int year, String employmentCode) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JewstEmploymentWtSet> cq = cb.createQuery(JewstEmploymentWtSet.class);
		Root<JewstEmploymentWtSet> root = cq.from(JewstEmploymentWtSet.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(
				root.get(JewstEmploymentWtSet_.jewstEmploymentWtSetPK).get(JewstEmploymentWtSetPK_.cid), companyId));
		predicateList.add(cb.equal(
				root.get(JewstEmploymentWtSet_.jewstEmploymentWtSetPK).get(JewstEmploymentWtSetPK_.yK), year));
		predicateList.add(
				cb.equal(root.get(JewstEmploymentWtSet_.jewstEmploymentWtSetPK).get(JewstEmploymentWtSetPK_.emptCd),
						employmentCode));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.EmploymentWtSettingRepository#findAll(java.lang.String, int)
	 */
	@Override
	public List<String> findAll(String companyId, int year) {
		// Get entity manager
		EntityManager em = this.getEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<JewstEmploymentWtSet> cq = cb.createQuery(JewstEmploymentWtSet.class);
		Root<JewstEmploymentWtSet> root = cq.from(JewstEmploymentWtSet.class);

		// Constructing condition.
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(cb.equal(
				root.get(JewstEmploymentWtSet_.jewstEmploymentWtSetPK).get(JewstEmploymentWtSetPK_.cid), companyId));
		predicateList.add(cb.equal(
				root.get(JewstEmploymentWtSet_.jewstEmploymentWtSetPK).get(JewstEmploymentWtSetPK_.yK), year));
		cq.where(predicateList.toArray(new Predicate[] {}));

		List<JewstEmploymentWtSet> resultList = em.createQuery(cq).getResultList();

		return resultList.stream().map(item -> item.getJewstEmploymentWtSetPK().getEmptCd()).distinct()
				.collect(Collectors.toList());
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<JewstEmploymentWtSet> toEntity(EmploymentWtSetting domain) {
		List<JewstEmploymentWtSet> entities = new ArrayList<JewstEmploymentWtSet>();
		JpaEmploymentWtSettingSetMemento memento = new JpaEmploymentWtSettingSetMemento();
		domain.saveToMemento(memento);
		entities.add(memento.getDeformed());
		entities.add(memento.getFlexSpecified());
		entities.add(memento.getFlexStatutory());
		entities.add(memento.getNormal());
		return entities;
	}

	/**
	 * To domain.
	 *
	 * @param entities the entities
	 * @return the employment wt setting
	 */
	private EmploymentWtSetting toDomain(List<JewstEmploymentWtSet> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		return new EmploymentWtSetting(new JpaEmploymentWtSettingGetMemento(entities));
	}

	/**
	 * Update entity.
	 *
	 * @param entity the entity
	 * @return the jewtst employment wt set
	 */
	private JewstEmploymentWtSet updateEntity(JewstEmploymentWtSet entity) {
		JewstEmploymentWtSet updatedEntity = this.queryProxy().find(entity.getJewstEmploymentWtSetPK(), JewstEmploymentWtSet.class).get();
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
