/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekTs;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeek;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkcondWeekPK;

/**
 * The Class JpaSingleDayScheduleDayOfSetMemento.
 */
public class JpaSDayScheDayOfSetMemento implements SingleDayScheduleSetMemento {

	/** The history id. */
	private String historyId;

	/** The per work day off atr. */
	private int perWorkDayOffAtr;

	/** The entity. */
	private KshmtWorkcondWeek entity;

	/**
	 * Instantiates a new jpa single day schedule day of set memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSDayScheDayOfSetMemento(String historyId, int perWorkDayOffAtr,
			KshmtWorkcondWeek entity, String employeeId) {
		this.historyId = historyId;
		this.perWorkDayOffAtr = perWorkDayOffAtr;
		if (entity.getKshmtWorkcondWeekPK() == null) {
			entity.setKshmtWorkcondWeekPK(
					new KshmtWorkcondWeekPK(historyId, perWorkDayOffAtr));
		}
		entity.setSid(employeeId);
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkTypeCode(nts.uk.ctx.at.shared.dom.
	 * worktype.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(Optional<WorkTypeCode> workTypeCode) {
		if (workTypeCode != null && workTypeCode.isPresent()){
			this.entity.setWorkTypeCode(workTypeCode.get().v());
		} else {
			this.entity.setWorkTypeCode(null);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkingHours(java.util.List)
	 */
	@Override
	public void setWorkingHours(List<TimeZone> workingHours) {
		List<KshmtWorkcondWeekTs> kshmtWorkcondWeekTss = workingHours.stream().map(item -> {
			KshmtWorkcondWeekTs entity = new KshmtWorkcondWeekTs();
			item.saveToMemento(new JpaTimezoneSetMemento<KshmtWorkcondWeekTs>(historyId,
					this.perWorkDayOffAtr, entity));
			return entity;
		}).collect(Collectors.toList());
		this.entity.setKshmtWorkcondWeekTss(kshmtWorkcondWeekTss);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkTimeCode(java.util.Optional)
	 */
	@Override
	public void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode) {
		if (workTimeCode != null && workTimeCode.isPresent()){
			this.entity.setWorkTimeCode(workTimeCode.get().v());
		} else {
			this.entity.setWorkTimeCode(null);
		}
	}

}
