package nts.uk.ctx.at.shared.app.dailypattern.command.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPattern;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternGetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSetting;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternSettingGetMemento;
import nts.uk.ctx.at.shared.dom.dailypattern.NumberDayDaily;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternCalendarDto.
 */
// TODO: Auto-generated Javadoc

/**
 * Gets the pattern calendar number day.
 *
 * @return the pattern calendar number day
 */
@Getter

/**
 * Sets the pattern calendar number day.
 *
 * @param patternCalendarNumberDay the new pattern calendar number day
 */
@Setter

public class DailyPatternDto implements DailyPatternSetMemento{
	
	/** The pattern code. */
	private String patternCode;
	
	/** The pattern name. */
	private String patternName;
	
	/** The work type codes. */
	private List<String> workTypeCodes;
	
	/** The work house codes. */
	private List<String> workHouseCodes;
	
	/** The pattern calendar number day. */
	private Integer patternCalendarNumberDay;


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

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getPatternCode()
		 */
		@Override
		public String getPatternCode() {
			return this.setting.patternCode;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getPatternName()
		 */
		@Override
		public String getPatternName() {
			return this.setting.patternName;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getWorkTypeCodes()
		 */
		@Override
		public List<String> getWorkTypeCodes() {
			return this.setting.workTypeCodes;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getWorkHouseCodes()
		 */
		@Override
		public List<String> getWorkHouseCodes() {
			return this.setting.workHouseCodes;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarGetMemento#getCalendarSetting()
		 */
		@Override
		public DailyPatternSetting getCalendarSetting() {
			return new DailyPatternSetting( new JpaCalendarSettingGetMemento(this.setting.patternCalendarNumberDay));
		}

        
    }
    
    /**
     * The Class JpaCalendarSettingGetMemento.
     */
    private class JpaCalendarSettingGetMemento implements DailyPatternSettingGetMemento {
        
        /** The number day calendar. */
        private Integer numberDayCalendar;
        
        /**
         * Instantiates a new jpa calendar setting get memento.
         *
         * @param numberDayCalendar the number day calendar
         */
        public JpaCalendarSettingGetMemento(Integer numberDayCalendar) {
            this.numberDayCalendar = numberDayCalendar;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.patterncalendar
         * CalendarSettingGetMemento#getNumberDayCalendar()
         */

		@Override
		public NumberDayDaily getNumberDayCalendar() {
			 return this.numberDayCalendar != null ? new NumberDayDaily(this.numberDayCalendar) : null;
		}
    }
    
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String setCompanyId) {
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setPatternCode(java.lang.String)
	 */
	@Override
	public void setPatternCode(String setPatternCode) {
		this.patternCode = setPatternCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setPatternName(java.lang.String)
	 */
	@Override
	public void setPatternName(String setPatternName) {
		this.patternName = setPatternName;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setWorkTypeCodes(java.util.List)
	 */
	@Override
	public void setWorkTypeCodes(List<String> setWorkTypeCodes) {
		this.workTypeCodes = setWorkTypeCodes;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#setWorkHouseCodes(java.util.List)
	 */
	@Override
	public void setWorkHouseCodes(List<String> setWorkHouseCodes) {
		this.workHouseCodes = setWorkHouseCodes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarSetMemento#
	 * setCalendarSetting(nts.uk.ctx.at.shared.dom.patterncalendar.
	 * CalendarSetting)
	 */
	@Override
	public void setCalendarSetting(DailyPatternSetting setCalendarSetting) {
			if (setCalendarSetting.getNumberDayCalendar() != null) {
	            this.patternCalendarNumberDay = setCalendarSetting.getNumberDayCalendar().v();
	        }
	}
}
