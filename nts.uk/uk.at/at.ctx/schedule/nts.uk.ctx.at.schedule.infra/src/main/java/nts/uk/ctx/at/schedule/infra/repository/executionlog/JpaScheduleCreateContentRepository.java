/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;
import nts.uk.ctx.at.schedule.infra.entity.executionlog.KscdtScheExeContent;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaScheduleCreateContentRepository.
 */
@Stateless
public class JpaScheduleCreateContentRepository extends JpaRepository
		implements
			ScheduleCreateContentRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository#findByExecutionId(java.lang.String)
	 */
	@Override
	public Optional<ScheduleCreateContent> findByExecutionId(String executionId) {
		return this.queryProxy()
				.find(executionId, KscdtScheExeContent.class)
				.map(entity -> this.toDomain(entity));
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository#
	 * add(nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent)
	 */
	@Override
	public void add(ScheduleCreateContent domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository#
	 * update(nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent)
	 */
	@Override
	public void update(ScheduleCreateContent domain) {
		this.commandProxy().update(this.toEntityUpdate(domain));

	}

	@Override
	public void addNew(ScheduleCreateContent domain) {
		this.commandProxy().insert(this.toEntityNew(domain));
	}

	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt sc create content
	 */
	private KscdtScheExeContent toEntity(ScheduleCreateContent domain){
		KscdtScheExeContent entity = new KscdtScheExeContent();
		domain.saveToMemento(new JpaScheduleCreateContentSetMemento(entity));

		return entity;
	}

	/**
	 * To entity use : Domain KscdtScheExeContent, cid, cd.
	 * @param domain
	 * @return
	 */
	private KscdtScheExeContent toEntityNew(ScheduleCreateContent domain){
		KscdtScheExeContent entity = new KscdtScheExeContent();
		val cid = AppContexts.user().companyId();
		val cd = AppContexts.user().contractCode();
		val rs = entity.toEntityNew(domain,cid,cd);
		return rs;
	}
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kscmt sc create content
	 */
	private KscdtScheExeContent toEntityUpdate(ScheduleCreateContent domain) {
		Optional<KscdtScheExeContent> opEntity = this.queryProxy().find(domain.getExecutionId(),
				KscdtScheExeContent.class);
		KscdtScheExeContent entity = opEntity.get();
		domain.saveToMemento(new JpaScheduleCreateContentSetMemento(entity));
		return entity;
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the schedule create content
	 */
	private ScheduleCreateContent toDomain(KscdtScheExeContent entity) {
		return new ScheduleCreateContent(new JpaScheduleCreateContentGetMemento(entity));
	}
	

}
