/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCatPK;

/**
 * The Class JpaSingleDayScheduleGetMemento.
 */
public class JpaSingleDayScheduleWorkCategoryGetMemento implements SingleDayScheduleGetMemento{
	
	/** The entity. */
	private KshmtPerWorkCat entity;
	
	/** The Constant DEFAULT_WORK_TIME_CODE. */
	public static final String DEFAULT_WORK_TIME_CODE = "000"; 
	
	

	/**
	 * Instantiates a new jpa single day schedule work category get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSingleDayScheduleWorkCategoryGetMemento(KshmtPerWorkCat entity) {
		if (entity.getKshmtPerWorkCatPK() == null) {
			entity.setKshmtPerWorkCatPK(new KshmtPerWorkCatPK());
		}
		this.entity = entity;
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.entity.getWorkTypeCode());
	}

	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#getWorkingHours()
	 */
	@Override
	public List<TimeZone> getWorkingHours() {
		List<TimeZone> timeZones = new ArrayList<>();

		
		return timeZones;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.workingcondition.SingleDayScheduleGetMemento#getWorkTimeCode()
	 */
	@Override
	public Optional<WorkTimeCode> getWorkTimeCode() {
		if (StringUtil.isNullOrEmpty(this.entity.getWorkTimeCode(), false)) {
			return Optional.empty();
		}
		if (this.entity.getWorkTimeCode().equals(DEFAULT_WORK_TIME_CODE)) {
			return Optional.empty();
		}
		return Optional.ofNullable(new WorkTimeCode(this.entity.getWorkTimeCode()));
	}

}
