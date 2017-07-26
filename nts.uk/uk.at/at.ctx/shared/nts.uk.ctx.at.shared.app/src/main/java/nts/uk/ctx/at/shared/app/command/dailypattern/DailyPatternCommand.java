/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.dailypattern;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPattern;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternVal;
import nts.uk.ctx.at.shared.dom.dailypattern.PatternCode;
import nts.uk.ctx.at.shared.dom.dailypattern.PatternName;

/**
 * The Class PatternCalendarCommand.
 */
@Setter
@Getter
public class DailyPatternCommand {

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/** The list daily pattern val. */
	private List<DailyPatternVal> listDailyPatternVal;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the daily pattern
	 */
	public DailyPattern toDomain(String companyId) {
		return new DailyPattern(new JpaPatternCalendarSettingGetMemento(companyId, this));
	}

	/**
	 * The Class JpaPatternCalendarSettingGetMemento.
	 */
	private class JpaPatternCalendarSettingGetMemento implements DailyPatternGetMemento {

		/** The company id. */
		private String companyId;

		/** The setting. */
		private DailyPatternCommand command;

		/**
		 * Instantiates a new jpa pattern calendar setting get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param setting
		 *            the setting
		 */
		public JpaPatternCalendarSettingGetMemento(String companyId, DailyPatternCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
		 * getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
		 * getPatternCode()
		 */
		@Override
		public PatternCode getPatternCode() {
			return new PatternCode(this.command.getPatternCode());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
		 * getPatternName()
		 */
		@Override
		public PatternName getPatternName() {
			return new PatternName(this.command.getPatternName());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento#
		 * getListDailyPatternVal()
		 */
		@Override
		public List<DailyPatternVal> getListDailyPatternVal() {
			return this.command.getListDailyPatternVal();
		}

	}

}
