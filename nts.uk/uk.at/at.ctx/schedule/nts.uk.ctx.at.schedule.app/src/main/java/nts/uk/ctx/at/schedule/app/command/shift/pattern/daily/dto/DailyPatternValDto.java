/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternVal;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternValGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.Days;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DispOrder;

/**
 * The Class DailyPatternValDto.
 */
@Getter
@Setter
public class DailyPatternValDto {

	/** The disp order. */
	private Integer dispOrder;

	/** The work type set cd. */
	private String workTypeSetCd;

	/** The working hours cd. */
	private String workingHoursCd;

	/** The days. */
	private Integer days;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @param patternCode
	 *            the pattern code
	 * @return the daily pattern
	 */
	public DailyPatternVal toDomain() {
		return new DailyPatternVal(new JpaGetMemento(this));
	}

	/**
	 * The Class JpaPatternCalendarSettingGetMemento.
	 */
	private class JpaGetMemento implements DailyPatternValGetMemento {

		/** The setting. */
		private DailyPatternValDto command;

		/**
		 * Instantiates a new jpa pattern calendar setting get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param patternCode
		 *            the pattern code
		 * @param command
		 *            the command
		 */
		public JpaGetMemento(DailyPatternValDto command) {
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#
		 * getDispOrder()
		 */
		@Override
		public DispOrder getDispOrder() {
			return new DispOrder(command.getDispOrder());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#
		 * getWorkTypeSetCd()
		 */
		@Override
		public WorkTypeCode getWorkTypeSetCd() {
			return new WorkTypeCode(command.getWorkTypeSetCd());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#
		 * getWorkingHoursCd()
		 */
		@Override
		public WorkingCode getWorkingHoursCd() {
			return new WorkingCode(command.getWorkingHoursCd());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValGetMemento#
		 * getDays()
		 */
		@Override
		public Days getDays() {
			return new Days(command.getDays());
		}

	}
}
