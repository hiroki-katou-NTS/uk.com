/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberDayNursing;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingGetMemento;

/**
 * The Class NursingLeaveSettingDto.
 */
@Setter
@Getter
public class NursingLeaveSettingDto {

    /** The manage type. */
    private Integer manageType;

    /** The nursing category. */
    private Integer nursingCategory;

    /** The start month day. */
    private Integer startMonthDay;

    /** The nursing number leave day. */
    private Integer nursingNumberLeaveDay;

    /** The nursing number person. */
    private Integer nursingNumberPerson;

    /** The special holiday frame. */
    private Integer specialHolidayFrame;
    
    /** The absence work. */
    private Integer absenceWork;
    
    /**
     * To domain.
     *
     * @param companyId the company id
     * @return the nursing vacation setting
     */
    public NursingLeaveSetting toDomain(String companyId) {
        return new NursingLeaveSetting(new JpaNursingVacationSettingGetMemento(companyId, this));
    }

    /**
     * The Class JpaNursingVacationSettingGetMemento.
     */
    private class JpaNursingVacationSettingGetMemento implements NursingLeaveSettingGetMemento {

        /** The company id. */
        private String companyId;
        
        /** The setting. */
        private NursingLeaveSettingDto setting;

        /**
         * Instantiates a new jpa nursing vacation setting get memento.
         *
         * @param companyId the company id
         * @param setting the setting
         */
        public JpaNursingVacationSettingGetMemento(String companyId, NursingLeaveSettingDto setting) {
            this.companyId = companyId;
            this.setting = setting;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getManageType()
         */
        @Override
        public ManageDistinct getManageType() {
            return ManageDistinct.valueOf(this.setting.manageType);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getNursingCategory()
         */
        @Override
        public NursingCategory getNursingCategory() {
            return NursingCategory.valueOf(this.setting.nursingCategory);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getStartMonthDay()
         */
        @Override
        public Integer getStartMonthDay() {
            return this.setting.startMonthDay;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getMaxPersonSetting()
         */
        @Override
        public MaxPersonSetting getMaxPersonSetting() {
            return new MaxPersonSetting(new JpaMaxPersonSettingGetMemento(this.setting.nursingNumberLeaveDay,
                    this.setting.nursingNumberPerson));
        }

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
		 * NursingLeaveSettingGetMemento#getSpecialHolidayFrame()
		 */
		@Override
		public Optional<Integer> getSpecialHolidayFrame() {
			return Optional.of(this.setting.specialHolidayFrame);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
		 * NursingLeaveSettingGetMemento#getWorkAbsence()
		 */
		@Override
		public Optional<Integer> getWorkAbsence() {
			return Optional.of(this.setting.absenceWork);
		}

    }

    /**
     * The Class JpaMaxPersonSettingGetMemento.
     */
    private class JpaMaxPersonSettingGetMemento implements MaxPersonSettingGetMemento {
        
        /** The nursing number leave day. */
        private Integer nursingNumberLeaveDay;
        
        /** The nursing number person. */
        private Integer nursingNumberPerson;
        
        /**
         * Instantiates a new jpa max person setting get memento.
         *
         * @param nursingNumberLeaveDay the nursing number leave day
         * @param nursingNumberPerson the nursing number person
         */
        public JpaMaxPersonSettingGetMemento(Integer nursingNumberLeaveDay, Integer nursingNumberPerson) {
            this.nursingNumberLeaveDay = nursingNumberLeaveDay;
            this.nursingNumberPerson = nursingNumberPerson;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * MaxPersonSettingGetMemento#getNursingNumberLeaveDay()
         */
        @Override
        public NumberDayNursing getNursingNumberLeaveDay() {
            return this.nursingNumberLeaveDay != null ? new NumberDayNursing(this.nursingNumberLeaveDay) : null;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * MaxPersonSettingGetMemento#getNursingNumberPerson()
         */
        @Override
        public NumberDayNursing getNursingNumberPerson() {
            return this.nursingNumberPerson != null ? new NumberDayNursing(this.nursingNumberPerson) : null;
        }
    }
}
