/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.shift.basicworkregister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceId;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KwbmtWorkplaceWorkSet_;


/**
 * The Class JpaWorkplaceBasicWorkRepository.
 */
@Stateless
public class JpaWorkplaceBasicWorkRepository extends JpaRepository implements WorkplaceBasicWorkRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#insert(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.WorkplaceBasicWork)
	 */
	@Override
	public void insert(WorkplaceBasicWork workplaceBasicWork) {
		List<KwbmtWorkplaceWorkSet> entities = this.toEntity(workplaceBasicWork);
		commandProxy().insertAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * basicworkregister.WorkplaceBasicWork)
	 */
	@Override
	public void update(WorkplaceBasicWork workplaceBasicWork) {
		List<KwbmtWorkplaceWorkSet> entities = this.toEntity(workplaceBasicWork);
		commandProxy()
				.updateAll(entities.stream().map(entity -> this.updateEntity(entity)).collect(Collectors.toList()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#remove(java.lang.String)
	 */
	@Override
	public void remove(String workplaceId) {
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();

		CriteriaDelete<KwbmtWorkplaceWorkSet> cd = bd.createCriteriaDelete(KwbmtWorkplaceWorkSet.class);

		// Root
		Root<KwbmtWorkplaceWorkSet> root = cd.from(KwbmtWorkplaceWorkSet.class);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KwbmtWorkplaceWorkSet_.kwbmtWorkplaceWorkSetPK).get(KwbmtWorkplaceWorkSetPK_.workplaceId), workplaceId));
	
		// Set Where clause to SQL Query
		cd.where(predicateList.toArray(new Predicate[] {}));
		em.createQuery(cd).executeUpdate();
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkplaceBasicWorkRepository#findAll(java.lang.String)
	 */
	@Override
	public Optional<WorkplaceBasicWork> findById(String workplaceId) {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<KwbmtWorkplaceWorkSet> cq = bd.createQuery(KwbmtWorkplaceWorkSet.class);
		
		// Root
		Root<KwbmtWorkplaceWorkSet> root = cq.from(KwbmtWorkplaceWorkSet.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KwbmtWorkplaceWorkSet_.kwbmtWorkplaceWorkSetPK).get(KwbmtWorkplaceWorkSetPK_.workplaceId),
				workplaceId));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		// Create Query
		TypedQuery<KwbmtWorkplaceWorkSet> query = em.createQuery(cq);

		if (CollectionUtil.isEmpty(query.getResultList())) {
			return Optional.empty();
		}
		
		return Optional.of(this.toDomain(query.getResultList()));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository#findAlreadySet()
	 */
	@Override
	public List<WorkplaceId> findSetting() {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<String> cq = bd.createQuery(String.class);
		
		// Root
		Root<KwbmtWorkplaceWorkSet> root = cq.from(KwbmtWorkplaceWorkSet.class);
		cq.select(root.get(KwbmtWorkplaceWorkSet_.kwbmtWorkplaceWorkSetPK).get(KwbmtWorkplaceWorkSetPK_.workplaceId)).distinct(true);
		TypedQuery<String> query = em.createQuery(cq);
		
		return query.getResultList().stream().map(item -> {
			return new WorkplaceId(item);
		}).collect(Collectors.toList());
	}

	/**
	 * To domain.
	 *
	 * @param entities the entity
	 * @return the workplace basic work
	 */
	private WorkplaceBasicWork toDomain(List<KwbmtWorkplaceWorkSet> entities) {
		return new WorkplaceBasicWork(new JpaWorkplaceBasicWorkGetMemento(entities));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<KwbmtWorkplaceWorkSet> toEntity(WorkplaceBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KwbmtWorkplaceWorkSet entity = new KwbmtWorkplaceWorkSet();
			basic.saveToMemento(new JpaBWSettingWorkplaceSetMemento(entity));
			entity.getKwbmtWorkplaceWorkSetPK().setWorkplaceId(domain.getWorkplaceId().v());
			return entity;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Update entity.
	 *
	 * @param entity the entity
	 * @return the kwbmt workplace work set
	 */
	private KwbmtWorkplaceWorkSet updateEntity(KwbmtWorkplaceWorkSet entity) {
		KwbmtWorkplaceWorkSet entityToUpdate = this.queryProxy()
				.find(entity.getKwbmtWorkplaceWorkSetPK(), KwbmtWorkplaceWorkSet.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(entity.getWorkingCode());
		entityToUpdate.getKwbmtWorkplaceWorkSetPK()
				.setWorkdayDivision(entity.getKwbmtWorkplaceWorkSetPK().getWorkdayDivision());
		entityToUpdate.getKwbmtWorkplaceWorkSetPK().setWorkplaceId(entity.getKwbmtWorkplaceWorkSetPK().getWorkplaceId());
		return entityToUpdate;
	}


}
