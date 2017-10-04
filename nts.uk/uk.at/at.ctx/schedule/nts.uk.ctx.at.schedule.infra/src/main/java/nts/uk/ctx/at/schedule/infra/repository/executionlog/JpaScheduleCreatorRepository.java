/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscmtScheduleCreator;

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
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreatorRepository#save(
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreator)
	 */
	@Override
	public void save(ScheduleCreator domain) {
		this.commandProxy().insert(this.toEntity(domain));
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
	private KscmtScheduleCreator toEntity(ScheduleCreator domain){
		KscmtScheduleCreator entity = new KscmtScheduleCreator();
		domain.saveToMemento(new JpaScheduleCreatorSetMemento(entity));
		return entity;
	}
	
	

}
