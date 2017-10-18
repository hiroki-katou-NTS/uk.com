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

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchCreator;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchCreatorPK;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchCreatorPK_;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchCreator_;

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

		CriteriaQuery<KscmtSchCreator> cq = criteriaBuilder.createQuery(KscmtSchCreator.class);
		Root<KscmtSchCreator> root = cq.from(KscmtSchCreator.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KscmtSchCreator_.kscmtSchCreatorPK).get(KscmtSchCreatorPK_.exeId), executionId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		cq.orderBy(criteriaBuilder.desc(root.get(KscmtSchCreator_.kscmtSchCreatorPK).get(KscmtSchCreatorPK_.sid)));

		// create query
		TypedQuery<KscmtSchCreator> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))		.collect(Collectors.toList());
	}

	/**
	 * Find by id.
	 *
	 * @param executionId the execution id
	 * @param employeeId the employee id
	 * @return the optional
	 */
	public Optional<KscmtSchCreator> findById(String executionId, String employeeId) {
		return this.queryProxy().find(new KscmtSchCreatorPK(executionId, employeeId),
				KscmtSchCreator.class);
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
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule creator
	 */
	private KscmtSchCreator toEntity(ScheduleCreator domain){
		KscmtSchCreator entity = new KscmtSchCreator();
		domain.saveToMemento(new JpaScheduleCreatorSetMemento(entity));
		return entity;
	}
	
	/**
	 * To entity update.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule creator
	 */
	private KscmtSchCreator toEntityUpdate(ScheduleCreator domain){
		Optional<KscmtSchCreator> opEntity = this.findById(domain.getExecutionId(), domain.getEmployeeId());
		KscmtSchCreator entity = new KscmtSchCreator();
		
		if(opEntity.isPresent()){
			entity = opEntity.get();
		}
		
		domain.saveToMemento(new JpaScheduleCreatorSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the schedule creator
	 */
	private ScheduleCreator toDomain(KscmtSchCreator entity){
		return new ScheduleCreator(new JpaScheduleCreatorGetMemento(entity));
	}

}
