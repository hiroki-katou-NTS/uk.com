/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.personallaborcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.personallaborcondition.SingleDayScheduleGetMemento;
import nts.uk.ctx.at.shared.dom.personallaborcondition.TimeZone;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.personallaborcondition.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegory;
import nts.uk.ctx.at.shared.infra.entity.personallaborcondition.KshmtWorkcondCtgegoryPK;

/**
 * The Class JpaSingleDayScheduleGetMemento.
 */
public class JpaSingleDayScheduleWorkCategoryGetMemento implements SingleDayScheduleGetMemento{
	
	/** The entity. */
	private KshmtWorkcondCtgegory entity;
	
	/** The Constant DEFAULT_WORK_TIME_CODE. */
	public static final String DEFAULT_WORK_TIME_CODE = "000"; 
	
	

	/**
	 * Instantiates a new jpa single day schedule work category get memento.
	 *
	 * @param entity the entity
	 */
	public JpaSingleDayScheduleWorkCategoryGetMemento(KshmtWorkcondCtgegory entity) {
		if (entity.getKshmtWorkcondCtgegoryPK() == null) {
			entity.setKshmtWorkcondCtgegoryPK(new KshmtWorkcondCtgegoryPK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleGetMemento#getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.entity.getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleGetMemento#getWorkingHours()
	 */
	@Override
	public List<TimeZone> getWorkingHours() {
		List<TimeZone> timeZones = new ArrayList<>();

		if (this.entity.getUseAtr1() == UseAtr.USE.value) {
			TimeZone timeZone = new TimeZone(UseAtr.USE);
			timeZone.defaultTimeZone(this.entity.getCnt1(), this.entity.getStart1(), this.entity.getEnd1());
			timeZones.add(timeZone);
		}
		if (this.entity.getUseAtr2() == UseAtr.USE.value) {
			TimeZone timeZone = new TimeZone(UseAtr.USE);
			timeZone.defaultTimeZone(this.entity.getCnt2(), this.entity.getStart2(), this.entity.getEnd2());
			timeZones.add(timeZone);
		}
		return timeZones;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.personallaborcondition.
	 * SingleDayScheduleGetMemento#getWorkTimeCode()
	 */
	@Override
	public Optional<WorkTimeCode> getWorkTimeCode() {
		if (StringUtil.isNullOrEmpty(this.entity.getWorktimeCd(), false)) {
			return Optional.empty();
		}
		if (this.entity.getWorktimeCd().equals(DEFAULT_WORK_TIME_CODE)) {
			return Optional.empty();
		}
		return Optional.ofNullable(new WorkTimeCode(this.entity.getWorktimeCd()));
	}

}
