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

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtWorkplaceWorkSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtWorkplaceWorkSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.basicworkregister.KscmtWorkplaceWorkSet_;


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
		List<KscmtWorkplaceWorkSet> entities = this.toEntity(workplaceBasicWork);
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
		List<KscmtWorkplaceWorkSet> entities = this.toEntity(workplaceBasicWork);
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

		CriteriaDelete<KscmtWorkplaceWorkSet> cd = bd.createCriteriaDelete(KscmtWorkplaceWorkSet.class);

		// Root
		Root<KscmtWorkplaceWorkSet> root = cd.from(KscmtWorkplaceWorkSet.class);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KscmtWorkplaceWorkSet_.kscmtWorkplaceWorkSetPK).get(KscmtWorkplaceWorkSetPK_.workplaceId), workplaceId));
	
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
		CriteriaQuery<KscmtWorkplaceWorkSet> cq = bd.createQuery(KscmtWorkplaceWorkSet.class);
		
		// Root
		Root<KscmtWorkplaceWorkSet> root = cq.from(KscmtWorkplaceWorkSet.class);
		cq.select(root);

		// Predicate where clause
		List<Predicate> predicateList = new ArrayList<>();
		predicateList.add(bd.equal(
				root.get(KscmtWorkplaceWorkSet_.kscmtWorkplaceWorkSetPK).get(KscmtWorkplaceWorkSetPK_.workplaceId),
				workplaceId));
		
		// Set Where clause to SQL Query
		cq.where(predicateList.toArray(new Predicate[] {}));
		
		// Create Query
		TypedQuery<KscmtWorkplaceWorkSet> query = em.createQuery(cq);

		if (CollectionUtil.isEmpty(query.getResultList())) {
			return Optional.empty();
		}
		
		return Optional.of(this.toDomain(query.getResultList()));
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository#findAlreadySet()
	 */
	@Override
	public List<String> findSetting() {
		
		// Get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder bd = em.getCriteriaBuilder();
		CriteriaQuery<String> cq = bd.createQuery(String.class);
		
		// Root
		Root<KscmtWorkplaceWorkSet> root = cq.from(KscmtWorkplaceWorkSet.class);
		cq.select(root.get(KscmtWorkplaceWorkSet_.kscmtWorkplaceWorkSetPK).get(KscmtWorkplaceWorkSetPK_.workplaceId)).distinct(true);
		TypedQuery<String> query = em.createQuery(cq);
		
		return query.getResultList();
	}

	/**
	 * To domain.
	 *
	 * @param entities the entity
	 * @return the workplace basic work
	 */
	private WorkplaceBasicWork toDomain(List<KscmtWorkplaceWorkSet> entities) {
		return new WorkplaceBasicWork(new JpaWorkplaceBasicWorkGetMemento(entities));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the list
	 */
	private List<KscmtWorkplaceWorkSet> toEntity(WorkplaceBasicWork domain) {
		return domain.getBasicWorkSetting().stream().map(basic -> {
			KscmtWorkplaceWorkSet entity = new KscmtWorkplaceWorkSet();
			basic.saveToMemento(new JpaBWSettingWorkplaceSetMemento(entity));
			entity.getKscmtWorkplaceWorkSetPK().setWorkplaceId(domain.getWorkplaceId());
			return entity;
		}).collect(Collectors.toList());
	}
	
	/**
	 * Update entity.
	 *
	 * @param entity the entity
	 * @return the kwbmt workplace work set
	 */
	private KscmtWorkplaceWorkSet updateEntity(KscmtWorkplaceWorkSet entity) {
		KscmtWorkplaceWorkSet entityToUpdate = this.queryProxy()
				.find(entity.getKscmtWorkplaceWorkSetPK(), KscmtWorkplaceWorkSet.class).get();
		entityToUpdate.setWorktypeCode(entity.getWorktypeCode());
		entityToUpdate.setWorkingCode(StringUtils.isEmpty(entity.getWorkingCode()) ? null : entity.getWorkingCode());
		entityToUpdate.getKscmtWorkplaceWorkSetPK()
				.setWorkdayDivision(entity.getKscmtWorkplaceWorkSetPK().getWorkdayDivision());
		entityToUpdate.getKscmtWorkplaceWorkSetPK().setWorkplaceId(entity.getKscmtWorkplaceWorkSetPK().getWorkplaceId());
		return entityToUpdate;
	}


}
