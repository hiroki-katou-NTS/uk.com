/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleErrLog;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleErrLogPK_;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleErrLog_;

/**
 * The Class JpaScheduleErrorLogRepository.
 */
@Stateless
public class JpaScheduleErrorLogRepository extends JpaRepository
		implements ScheduleErrorLogRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository#
	 * findByExecutionId(java.lang.String)
	 */
	@Override
	public List<ScheduleErrorLog> findByExecutionId(String executionId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KscmtScheduleErrLog> cq = criteriaBuilder.createQuery(KscmtScheduleErrLog.class);
		Root<KscmtScheduleErrLog> root = cq.from(KscmtScheduleErrLog.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscmtScheduleErrLog_.kscmtScheduleErrLogPK).get(KscmtScheduleErrLogPK_.exeId), executionId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder
				.desc(root.get(KscmtScheduleErrLog_.kscmtScheduleErrLogPK).get(KscmtScheduleErrLogPK_.ymd)));

		List<KscmtScheduleErrLog> lstKscmtScheduleErrLog = em.createQuery(cq).getResultList();
		// check empty
		if (CollectionUtil.isEmpty(lstKscmtScheduleErrLog)) {
			return null;
		}
		return lstKscmtScheduleErrLog.stream().map(item -> {
			return new ScheduleErrorLog(new JpaScheduleErrorLogGetMemento(item));
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository#
	 * distinctErrorByExecutionId(java.lang.String)
	 */
	@Override
	public Integer distinctErrorByExecutionId(String executionId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
		Root<KscmtScheduleErrLog> root = cq.from(KscmtScheduleErrLog.class);

		// select root
		cq.select(criteriaBuilder.countDistinct(root.get(KscmtScheduleErrLog_.kscmtScheduleErrLogPK)
				.get(KscmtScheduleErrLogPK_.sid)));

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscmtScheduleErrLog_.kscmtScheduleErrLogPK)
						.get(KscmtScheduleErrLogPK_.exeId), executionId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		int cntError = em.createQuery(cq).getSingleResult().intValue();
		return cntError;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogRepository#add(
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog)
	 */
	@Override
	public void add(ScheduleErrorLog domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule err log
	 */
	private KscmtScheduleErrLog toEntity(ScheduleErrorLog domain) {
		KscmtScheduleErrLog entity = new KscmtScheduleErrLog();
		domain.saveToMemento(new JpaScheduleErrorLogSetMemento(entity));
		return entity;
	}
	

}
