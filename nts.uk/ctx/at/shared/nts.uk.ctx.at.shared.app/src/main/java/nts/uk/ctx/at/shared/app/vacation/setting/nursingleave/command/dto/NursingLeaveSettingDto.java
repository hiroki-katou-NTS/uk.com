/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.nursingleave.command.dto;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSettingGetMemento;

/**
 * The Class NursingLeaveSettingDto.
 */
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

    /** The work type codes. */
    private List<String> workTypeCodes;

    /**
     * To domain.
     *
     * @param companyId the company id
     * @return the nursing vacation setting
     */
    public NursingVacationSetting toDomain(String companyId) {
        return new NursingVacationSetting(new JpaNursingVacationSettingGetMemento(companyId, this));
    }

    /**
     * The Class JpaNursingVacationSettingGetMemento.
     */
    private class JpaNursingVacationSettingGetMemento implements NursingVacationSettingGetMemento {

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

        /**
         * Gets the company id.
         *
         * @return the company id
         */
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

        /**
         * Gets the manage type.
         *
         * @return the manage type
         */
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

        /**
         * Gets the nursing category.
         *
         * @return the nursing category
         */
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

        /**
         * Gets the start month day.
         *
         * @return the start month day
         */
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

        /**
         * Gets the max person setting.
         *
         * @return the max person setting
         */
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

        /**
         * Gets the work type codes.
         *
         * @return the work type codes
         */
        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getWorkTypeCodes()
         */
        @Override
        public List<String> getWorkTypeCodes() {
            return this.setting.workTypeCodes;
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
        public Integer getNursingNumberLeaveDay() {
            return this.nursingNumberLeaveDay;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * MaxPersonSettingGetMemento#getNursingNumberPerson()
         */
        @Override
        public Integer getNursingNumberPerson() {
            return this.nursingNumberPerson;
        }
    }
}
