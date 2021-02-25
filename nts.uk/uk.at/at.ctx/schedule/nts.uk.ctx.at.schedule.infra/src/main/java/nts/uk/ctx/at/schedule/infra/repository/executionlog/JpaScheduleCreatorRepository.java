/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTarget;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTargetPK;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTargetPK_;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeTarget_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaScheduleCreatorRepository.
 */
@Stateless
public class JpaScheduleCreatorRepository extends JpaRepository
		implements ScheduleCreatorRepository {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#findAll
	 * (java.lang.String)
	 */
	@Override
	public List<ScheduleCreator> findAll(String executionId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KscdtScheExeTarget> cq = criteriaBuilder
				.createQuery(KscdtScheExeTarget.class);
		Root<KscdtScheExeTarget> root = cq.from(KscdtScheExeTarget.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtScheExeTarget_.kscdtScheExeTargetPK).get(KscdtScheExeTargetPK_.exeId),
				executionId));

		cq.where(lstpredicateWhere.toArray(new Predicate[]{}));

		cq.orderBy(criteriaBuilder.desc(
				root.get(KscdtScheExeTarget_.kscdtScheExeTargetPK).get(KscdtScheExeTargetPK_.sid)));

		// create query
		TypedQuery<KscdtScheExeTarget> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#countdAll(java.lang.String)
	 */
	@Override
	public int countdAll(String executionId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		Root<KscdtScheExeTarget> root = cq.from(KscdtScheExeTarget.class);

		// select count (*) root
		cq.select(criteriaBuilder.count(root));

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal execution id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtScheExeTarget_.kscdtScheExeTargetPK).get(KscdtScheExeTargetPK_.exeId), executionId));

		cq.where(lstpredicateWhere.toArray(new Predicate[]{}));

		// create query
		TypedQuery<Long> query = em.createQuery(cq);

		// exclude select
		return query.getSingleResult().intValue();
	}

	/**
	 * Find by id.
	 *
	 * @param executionId the execution id
	 * @param employeeId the employee id
	 * @return the optional
	 */
	public Optional<KscdtScheExeTarget> findById(String executionId, String employeeId) {
		return this.queryProxy().find(new KscdtScheExeTargetPK(executionId, employeeId),
				KscdtScheExeTarget.class);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#save(
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator)
	 */
	@Override
	public void add(ScheduleCreator domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#save(
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator)
	 */
	@Override
	public void update(ScheduleCreator domain) {
		this.commandProxy().update(this.toEntityUpdate(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#saveAll
	 * (java.util.List)
	 */
	@Override
	public void saveAll(List<ScheduleCreator> domains) {
		this.commandProxy().insertAll(
				domains.stream().map(domain -> this.toEntity(domain)).collect(Collectors.toList()));
	}
	@Override
	public void saveAllNew(List<ScheduleCreator> domains) {
		this.commandProxy().insertAll(
				domains.stream().map(domain -> this.toEntityNew(domain)).collect(Collectors.toList()));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#
	 * countByStatus(java.lang.String, int)
	 */
	@Override
	public int countByStatus(String executionId, int executionStatus) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		Root<KscdtScheExeTarget> root = cq.from(KscdtScheExeTarget.class);

		// select count (*) root
		cq.select(criteriaBuilder.count(root));

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal execution id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtScheExeTarget_.kscdtScheExeTargetPK).get(KscdtScheExeTargetPK_.exeId),
				executionId));

		// equal execution status
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KscdtScheExeTarget_.exeStatus), executionStatus));

		cq.where(lstpredicateWhere.toArray(new Predicate[]{}));

		// create query
		TypedQuery<Long> query = em.createQuery(cq);

		// exclude select
		return query.getSingleResult().intValue();
	}

	@Override
	public Optional<ScheduleCreator> findByExecutionIdAndSId(String executionId, String sId) {
		return this.queryProxy().find(new KscdtScheExeTargetPK(executionId, sId), KscdtScheExeTarget.class).map(this::toDomain);
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule creator
	 */
	private KscdtScheExeTarget toEntity(ScheduleCreator domain){
		KscdtScheExeTarget entity = new KscdtScheExeTarget();
		domain.saveToMemento(new JpaScheduleCreatorSetMemento(entity));
		return entity;
	}
	private KscdtScheExeTarget toEntityNew(ScheduleCreator domain){
		KscdtScheExeTarget entity = new KscdtScheExeTarget();
		domain.saveToMemento(new JpaScheduleCreatorSetMemento(entity));
		val cd = AppContexts.user().contractCode();
		val cid = AppContexts.user().companyId();
		entity.setContractCd(cd);
		entity.setCid(cid);
		return entity;
	}
	
	/**
	 * To entity update.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule creator
	 */
	private KscdtScheExeTarget toEntityUpdate(ScheduleCreator domain) {
		Optional<KscdtScheExeTarget> opEntity = this.findById(domain.getExecutionId(),
				domain.getEmployeeId());
		KscdtScheExeTarget entity = opEntity.get();

		domain.saveToMemento(new JpaScheduleCreatorSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the schedule creator
	 */
	private ScheduleCreator toDomain(KscdtScheExeTarget entity){
		return new ScheduleCreator(new JpaScheduleCreatorGetMemento(entity));
	}

}
