/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.workplace;

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
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSet;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSetPK;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSetPK_;
import nts.uk.ctx.at.shared.infra.entity.employment.statutory.worktime.workplace.JwpwtstWorkplaceWtSet_;
import nts.uk.ctx.at.shared.infra.repository.employment.statutory.worktime.WtSettingConstant;

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
	public void remove(String companyId, int year, String workPlaceId) {
		this.commandProxy().remove(JwpwtstWorkplaceWtSet.class, new JwpwtstWorkplaceWtSetPK(companyId, year,
				WtSettingConstant.NORMAL, WtSettingConstant.STATUTORY, workPlaceId));
		this.commandProxy().remove(JwpwtstWorkplaceWtSet.class, new JwpwtstWorkplaceWtSetPK(companyId, year,
				WtSettingConstant.FLEX, WtSettingConstant.STATUTORY, workPlaceId));
		this.commandProxy().remove(JwpwtstWorkplaceWtSet.class, new JwpwtstWorkplaceWtSetPK(companyId, year,
				WtSettingConstant.FLEX, WtSettingConstant.SPECIFIED, workPlaceId));
		this.commandProxy().remove(JwpwtstWorkplaceWtSet.class, new JwpwtstWorkplaceWtSetPK(companyId, year,
				WtSettingConstant.DEFORMED, WtSettingConstant.STATUTORY, workPlaceId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
	 * CompanySettingRepository#find(java.lang.String)
	 */
	@Override
	public Optional<WorkPlaceWtSetting> find(String companyId, int year, String workPlaceId) {
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
		predicateList.add(
				cb.equal(root.get(JwpwtstWorkplaceWtSet_.jwpwtstWorkplaceWtSetPK).get(JwpwtstWorkplaceWtSetPK_.wkpId),
						workPlaceId));
		cq.where(predicateList.toArray(new Predicate[] {}));

		return Optional.ofNullable(this.toDomain(em.createQuery(cq).getResultList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.workplace.
	 * WorkPlaceWtSettingRepository#findAll(java.lang.String, int)
	 */
	@Override
	public List<String> findAll(String companyId, int year) {
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

		List<JwpwtstWorkplaceWtSet> resultList = em.createQuery(cq).getResultList();

		return resultList.stream().map(item -> item.getJwpwtstWorkplaceWtSetPK().getWkpId()).distinct()
				.collect(Collectors.toList());
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
		List<JwpwtstWorkplaceWtSet> entities = new ArrayList<JwpwtstWorkplaceWtSet>();
		JpaWorkplaceWtSettingSetMemento memento = new JpaWorkplaceWtSettingSetMemento();
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
	 * @return the jwpwtst workplace wt set
	 */
	private JwpwtstWorkplaceWtSet updateEntity(JwpwtstWorkplaceWtSet entity) {
		JwpwtstWorkplaceWtSet updatedEntity = this.queryProxy().find(entity.getJwpwtstWorkplaceWtSetPK(), JwpwtstWorkplaceWtSet.class).get();
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
