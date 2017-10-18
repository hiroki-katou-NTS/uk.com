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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchExecutionLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchExecutionLogPK;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchExecutionLogPK_;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtSchExecutionLog_;

/**
 * The Class JpaScheduleExecutionLogRepository.
 */
@Stateless
public class JpaScheduleExecutionLogRepository extends JpaRepository
		implements ScheduleExecutionLogRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ScheduleExecutionLog> findById(String companyId, String executionId) {
		return this.queryProxy()
				.find(new KscmtSchExecutionLogPK(companyId, executionId), KscmtSchExecutionLog.class)
				.map(entity -> this.toDomain(entity));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * find(java.lang.String, nts.uk.ctx.at.shared.dom.workrule.closure.Period)
	 */
	@Override
	public List<ScheduleExecutionLog> find(String companyId, GeneralDateTime startDate,GeneralDateTime endDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KscmtSchExecutionLog> cq = criteriaBuilder
				.createQuery(KscmtSchExecutionLog.class);
		Root<KscmtSchExecutionLog> root = cq.from(KscmtSchExecutionLog.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtSchExecutionLog_.kscmtSchExecutionLogPK)
						.get(KscmtSchExecutionLogPK_.cid), companyId));
		lstpredicateWhere.add(criteriaBuilder
				.lessThanOrEqualTo(root.get(KscmtSchExecutionLog_.exeStrD), endDate));
		lstpredicateWhere.add(criteriaBuilder
				.greaterThanOrEqualTo(root.get(KscmtSchExecutionLog_.exeStrD), startDate));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(KscmtSchExecutionLog_.exeStrD)));

		List<KscmtSchExecutionLog> lstKscmtScheduleExcLog = em.createQuery(cq).getResultList();
		
		// check empty
		if (CollectionUtil.isEmpty(lstKscmtScheduleExcLog)) {
			return null;
		}
		return lstKscmtScheduleExcLog.stream().map(item -> {
			return new ScheduleExecutionLog(new JpaScheduleExecutionLogGetMemento(item));
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * save(nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog)
	 */
	@Override
	public void add(ScheduleExecutionLog domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * save(nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog)
	 */
	@Override
	public void update(ScheduleExecutionLog domain) {
		this.commandProxy().update(this.toEntityUpdate(domain));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule exc log
	 */
	private KscmtSchExecutionLog toEntity(ScheduleExecutionLog domain){
		KscmtSchExecutionLog entity = new KscmtSchExecutionLog();
		domain.saveToMemento(new JpaScheduleExecutionLogSetMemento(entity));
		return entity;
	}
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule exc log
	 */
	private KscmtSchExecutionLog toEntityUpdate(ScheduleExecutionLog domain){
		Optional<KscmtSchExecutionLog> opentity = this.queryProxy().find(
				new KscmtSchExecutionLogPK(domain.getCompanyId().v(), domain.getExecutionId()),
				KscmtSchExecutionLog.class);
		KscmtSchExecutionLog entity = new KscmtSchExecutionLog();
		if(opentity.isPresent()){
			entity = opentity.get();
		}
		domain.saveToMemento(new JpaScheduleExecutionLogSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the schedule execution log
	 */
	private ScheduleExecutionLog toDomain(KscmtSchExecutionLog entity){
		return new ScheduleExecutionLog(new JpaScheduleExecutionLogGetMemento(entity));
	}


}
