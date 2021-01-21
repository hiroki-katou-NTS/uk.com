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

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLogPK;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLogPK_;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeLog_;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaScheduleExecutionLogRepository.
 */
@Stateless
public class JpaScheduleExecutionLogRepository extends JpaRepository implements ScheduleExecutionLogRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * findById(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<ScheduleExecutionLog> findById(String companyId, String executionId) {
		return this.queryProxy().find(new KscdtScheExeLogPK(companyId, executionId), KscdtScheExeLog.class)
				.map(entity -> this.toDomain(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * findByDateTime(java.lang.String, nts.arc.time.GeneralDateTime,
	 * nts.arc.time.GeneralDateTime)
	 */
	@Override
	public List<ScheduleExecutionLog> findByDateTime(String companyId, GeneralDateTime startDate,
			GeneralDateTime endDate, int exeAtr) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KscdtScheExeLog> cq = criteriaBuilder.createQuery(KscdtScheExeLog.class);
		Root<KscdtScheExeLog> root = cq.from(KscdtScheExeLog.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KscdtScheExeLog_.kscdtScheExeLogPK).get(KscdtScheExeLogPK_.cid), companyId));

		// less than or equal end date
		lstpredicateWhere.add(criteriaBuilder.lessThanOrEqualTo(root.get(KscdtScheExeLog_.exeStrD), endDate));

		// greater than or equal start date
		lstpredicateWhere.add(criteriaBuilder.greaterThanOrEqualTo(root.get(KscdtScheExeLog_.exeStrD), startDate));

		// equal exeAtr = manual
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KscdtScheExeLog_.exeAtr), exeAtr));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by execution start date
		cq.orderBy(criteriaBuilder.desc(root.get(KscdtScheExeLog_.exeStrD)));

		List<KscdtScheExeLog> lstKscmtScheduleExcLog = em.createQuery(cq).getResultList();

		// return data full
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
	@Override
	public void addNew(ScheduleExecutionLog domain) {
		this.commandProxy().insert(this.toEntityNew(domain));
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
	 * @param domain
	 *            the domain
	 * @return the kscmt schedule exc log
	 */
	private KscdtScheExeLog toEntity(ScheduleExecutionLog domain) {
		KscdtScheExeLog entity = new KscdtScheExeLog();

		domain.saveToMemento(new JpaScheduleExecutionLogSetMemento(entity));
		return entity;
	}
	private KscdtScheExeLog toEntityNew(ScheduleExecutionLog domain) {
		KscdtScheExeLog entity = new KscdtScheExeLog();
		domain.saveToMemento(new JpaScheduleExecutionLogSetMemento(entity));
		// Update contractCode - 27/8/2020
		return entity;
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kscmt schedule exc log
	 */
	private KscdtScheExeLog toEntityUpdate(ScheduleExecutionLog domain) {

		// find by key
		Optional<KscdtScheExeLog> opentity = this.queryProxy()
				.find(new KscdtScheExeLogPK(domain.getCompanyId().v(), domain.getExecutionId()), KscdtScheExeLog.class);
		KscdtScheExeLog entity = opentity.get();

		// update entity by domain
		domain.saveToMemento(new JpaScheduleExecutionLogSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the schedule execution log
	 */
	private ScheduleExecutionLog toDomain(KscdtScheExeLog entity) {
		return new ScheduleExecutionLog(new JpaScheduleExecutionLogGetMemento(entity));
	}

}
