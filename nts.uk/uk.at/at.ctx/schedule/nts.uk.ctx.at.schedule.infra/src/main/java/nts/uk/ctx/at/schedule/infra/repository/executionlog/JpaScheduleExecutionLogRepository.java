/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleExcLog;
import nts.uk.ctx.at.shared.dom.workrule.closure.Period;

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
	 * find(java.lang.String, nts.uk.ctx.at.shared.dom.workrule.closure.Period)
	 */
	@Override
	public List<ScheduleExecutionLog> find(String companyId, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLogRepository#
	 * save(nts.uk.ctx.at.schedule.dom.executionlog.ScheduleExecutionLog)
	 */
	@Override
	public void save(ScheduleExecutionLog domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt schedule exc log
	 */
	private KscmtScheduleExcLog toEntity(ScheduleExecutionLog domain){
		KscmtScheduleExcLog entity = new KscmtScheduleExcLog();
		domain.saveToMemento(new JpaScheduleExecutionLogSetMemento(entity));
		return entity;
	}

}
