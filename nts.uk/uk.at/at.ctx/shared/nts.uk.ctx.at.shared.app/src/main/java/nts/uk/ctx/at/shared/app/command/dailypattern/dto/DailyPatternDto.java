package nts.uk.ctx.at.shared.app.command.dailypattern.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPattern;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternVal;

/**
 * The Class DailyPatternDto.
 */
@Getter
@Setter
public class DailyPatternDto implements DailyPatternSetMemento {

	/** The pattern code. */
	private String patternCode;

	/** The pattern name. */
	private String patternName;

	/** The list daily pattern val. */
	private List<DailyPatternVal> listDailyPatternVal;
	
	public DailyPatternDto() {
	}
	
	/**
	 * To domain.
	 *
	 * @param companyId the company id
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
		private DailyPatternDto setting;

		/**
		 * Instantiates a new jpa pattern calendar setting get memento.
		 *
		 * @param companyId the company id
		 * @param setting the setting
		 */
		public JpaPatternCalendarSettingGetMemento(String companyId, DailyPatternDto setting) {
			this.companyId = companyId;
			this.setting = setting;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
		 * getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
		 * getPatternCode()
		 */
		@Override
		public String getPatternCode() {
			return this.setting.patternCode;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#
		 * getPatternName()
		 */
		@Override
		public String getPatternName() {
			return this.setting.patternName;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento#getListDailyPatternVal()
		 */
		@Override
		public List<DailyPatternVal> getListDailyPatternVal() {
			return this.setting.listDailyPatternVal;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String setCompanyId) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setPatternCode(java.lang.String)
	 */
	@Override
	public void setPatternCode(String setPatternCode) {
		this.patternCode = setPatternCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(String setPatternName) {
		this.patternName = setPatternName;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento#setListDailyPatternVal(java.util.List)
	 */
	@Override
	public void setListDailyPatternVal(List<DailyPatternVal> setListDailyPatternVal) {
		this.listDailyPatternVal = setListDailyPatternVal;
	}

}
