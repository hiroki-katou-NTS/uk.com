/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDayScheduleSetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.personallaborcondition.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegory;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegoryPK;

/**
 * The Class JpaSingleDayScheduleSetMemento.
 */
public class JpaSingleDayScheduleWorkCategorySetMemento implements SingleDayScheduleSetMemento{
	
	/** The entity. */
	private KshmtWorkcondCtgegory entity;
	
	/** The Constant DEFAULT_WORK_TIME_CODE. */
	public static final String DEFAULT_WORK_TIME_CODE = "000"; 
	
	/** The Constant DEFAULT_TIMES_ONE. */
	public static final int DEFAULT_TIMES_ONE = 1; 
	
	/** The Constant DEFAULT_TIMES_TWO. */
	public static final int DEFAULT_TIMES_TWO = 2; 
	
	
	
	/**
	 * Instantiates a new jpa single day schedule set memento.
	 *
	 * @param entity the entity
	 */
	public JpaSingleDayScheduleWorkCategorySetMemento(KshmtWorkcondCtgegory entity) {
		if(entity.getKshmtWorkcondCtgegoryPK() == null){
			entity.setKshmtWorkcondCtgegoryPK(new KshmtWorkcondCtgegoryPK());
		}
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
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		this.entity.setWorktypeCd(workTypeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkingHours(java.util.List)
	 */
	@Override
	public void setWorkingHours(List<TimeZone> workingHours) {

		// default time zone
		this.entity.defaultTimeZone();

		workingHours.forEach(workingHour -> {
			// times one set use
			if (workingHour.getTimes() == DEFAULT_TIMES_ONE
					&& workingHour.getUseAtr().equals(UseAtr.USE)) {
				this.entity.setCnt1(DEFAULT_TIMES_ONE);
				this.entity.setStart1(workingHour.getStart().valueAsMinutes());
				this.entity.setEnd1(workingHour.getEnd().valueAsMinutes());
			}
			// times two set use
			if (workingHour.getTimes() == DEFAULT_TIMES_TWO
					&& workingHour.getUseAtr().equals(UseAtr.USE)) {
				this.entity.setCnt2(DEFAULT_TIMES_TWO);
				this.entity.setStart2(workingHour.getStart().valueAsMinutes());
				this.entity.setEnd2(workingHour.getEnd().valueAsMinutes());
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleSetMemento#setWorkTimeCode(java.util.Optional)
	 */
	@Override
	public void setWorkTimeCode(Optional<WorkTimeCode> workTimeCode) {
		if (workTimeCode.isPresent()) {
			this.entity.setWorktimeCd(workTimeCode.get().v());
		} else {
			this.entity.setWorktimeCd(DEFAULT_WORK_TIME_CODE);
		}
	}

}
