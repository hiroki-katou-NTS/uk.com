/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedule;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtItemWorkSchedulePK;

/**
 * The Class JpaOutputItemDailyWorkScheduleRepository.
 */
@Stateless
public class JpaOutputItemDailyWorkScheduleRepository extends JpaRepository implements OutputItemDailyWorkScheduleRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#findByCode(int)
	 */
	@Override
	public Optional<OutputItemDailyWorkSchedule> findByCode(int code) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#add(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void add(OutputItemDailyWorkSchedule domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#update(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void update(OutputItemDailyWorkSchedule domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#delete(nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule)
	 */
	@Override
	public void delete(OutputItemDailyWorkSchedule domain) {
		this.commandProxy().remove(this.toEntity(domain));;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#findByCid(java.lang.String)
	 */
	@Override
	public List<OutputItemDailyWorkSchedule> findByCid(String companyId) {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#deleteByCidAndCode(java.lang.String, int)
	 */
	@Override
	public void deleteByCidAndCode(String companyId, int code) {
		KfnmtItemWorkSchedulePK primaryKey = new KfnmtItemWorkSchedulePK();
		primaryKey.setCid(companyId);
		primaryKey.setItemCode(code);
		this.commandProxy().remove(KfnmtItemWorkSchedule.class, primaryKey);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository#findByCidAndCode(java.lang.String, int)
	 */
	@Override
	public Optional<OutputItemDailyWorkSchedule> findByCidAndCode(String companyId, int code) {
		KfnmtItemWorkSchedulePK key = new KfnmtItemWorkSchedulePK();
		key.setCid(companyId);
		key.setItemCode(code);
		return this.queryProxy().find(key, KfnmtItemWorkSchedule.class).map(entity -> this.toDomain(entity));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the output item daily work schedule
	 */
	private OutputItemDailyWorkSchedule toDomain(KfnmtItemWorkSchedule entity) {
		OutputItemDailyWorkSchedule domain = new OutputItemDailyWorkSchedule(new JpaOutputItemDailyWorkScheduleGetMemento(entity));
		return domain;
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kfnmt item work schedule
	 */
	private KfnmtItemWorkSchedule toEntity(OutputItemDailyWorkSchedule domain) {
		KfnmtItemWorkSchedule entity = new KfnmtItemWorkSchedule();
		domain.saveToMemento(new JpaOutputItemDailyWorkScheduleSetMemento(entity));
		return entity;
	}
	
}
