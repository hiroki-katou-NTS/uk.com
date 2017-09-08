/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.overtime;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeCalculationMethod;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeNote;
import nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.overtime.KshstOverTime;
import nts.uk.ctx.at.shared.infra.entity.overtime.KshstOverTimePK;
import nts.uk.ctx.at.shared.infra.entity.overtime.KshstOverTimeSet;

/**
 * The Class JpaOvertimeSettingGetMemento.
 */
public class JpaOvertimeSettingGetMemento implements OvertimeSettingGetMemento{
	
	/** The entity overtimes. */
	private List<KshstOverTime> entityOvertimes;
	
	/** The entity. */
	private KshstOverTimeSet entity;

	/**
	 * Instantiates a new jpa overtime setting get memento.
	 *
	 * @param entity the entity
	 * @param entityOvertimes the entity overtimes
	 */
	public JpaOvertimeSettingGetMemento(KshstOverTimeSet entity,
			List<KshstOverTime> entityOvertimes) {
		entityOvertimes.forEach(entityItem -> {
			if (entityItem.getKshstOverTimePK() == null) {
				entityItem.setKshstOverTimePK(new KshstOverTimePK());
			}
		});
		this.entityOvertimes = entityOvertimes;
		this.entity = entity;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#getCompanyId(
	 * )
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#getNote()
	 */
	@Override
	public OvertimeNote getNote() {
		return new OvertimeNote(this.entity.getNote());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#
	 * getCalculationMethod()
	 */
	@Override
	public OvertimeCalculationMethod getCalculationMethod() {
		return OvertimeCalculationMethod.valueOf(this.entity.getCalculationMethod());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.OvertimeSettingGetMemento#getOvertimes(
	 * )
	 */
	@Override
	public List<Overtime> getOvertimes() {
		return this.entityOvertimes.stream()
				.map(entityOvertime -> new Overtime(new JpaOvertimeGetMemento(entityOvertime)))
				.collect(Collectors.toList());
	}

}
