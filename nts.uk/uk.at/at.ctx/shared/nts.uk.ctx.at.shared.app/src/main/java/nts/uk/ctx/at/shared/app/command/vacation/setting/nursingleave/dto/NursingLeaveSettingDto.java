/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingNumberLeaveDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingNumberPerson;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.TimeCareNursingSet;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * The Class NursingLeaveSettingDto.
 */
@Setter
@Getter
@Builder
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
    
    /** The nursing number leave day 2. */
    private Integer nursingNumberLeaveDay2;

    /** The nursing number person 2. */
    private Integer nursingNumberPerson2;

    /** The special holiday frame. */
    private Integer specialHolidayFrame;

    /** The absence work. */
    private Integer absenceWork;
    
    private Integer timeDigestiveUnit;
    
    private Integer manageDistinct;

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
		 * NursingLeaveSettingGetMemento#getSpecialHolidayFrame()
		 */
		@Override
		public Optional<Integer> getHdspFrameNo() {
			return Optional.of(this.setting.specialHolidayFrame);
		}

		/*
		 * (non-Javadoc)
		 *
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
		 * NursingLeaveSettingGetMemento#getWorkAbsence()
		 */
		@Override
		public Optional<Integer> getAbsenceFrameNo() {
			return Optional.of(this.setting.absenceWork);
		}

		@Override
		public Integer getStartMonthDay() {
			return this.setting.startMonthDay;
		}

		@Override
		public TimeCareNursingSet getTimeCareNursingSet() {
			return new TimeCareNursingSet(
					EnumAdaptor.valueOf(this.setting.timeDigestiveUnit, TimeDigestiveUnit.class),
					EnumAdaptor.valueOf(this.setting.manageDistinct, ManageDistinct.class ));
		}

		@Override
		public List<MaxPersonSetting> getMaxPersonSetting() {
			 return MaxPersonSetting.getList(new JpaMaxPersonSettingGetMemento(this.setting.nursingNumberLeaveDay,
	                    this.setting.nursingNumberPerson));
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
        
        /** The nursing number leave day. */
        private Integer nursingNumberLeaveDay2;

        /** The nursing number person. */
        private Integer nursingNumberPerson2;


        /**
         * Instantiates a new jpa max person setting get memento.
         *
         * @param nursingNumberLeaveDay the nursing number leave day
         * @param nursingNumberLeaveDay2 the nursing number person
         */
        public JpaMaxPersonSettingGetMemento(Integer nursingNumberLeaveDay, Integer nursingNumberLeaveDay2) {
            this.nursingNumberLeaveDay = nursingNumberLeaveDay;
            this.nursingNumberLeaveDay2 = nursingNumberLeaveDay2;
        }

		@Override
		public NursingNumberLeaveDay getNursingNumberLeaveDay() {
			 return this.nursingNumberLeaveDay != null ? new NursingNumberLeaveDay(this.nursingNumberLeaveDay) : null;
		}

		@Override
		public NursingNumberLeaveDay getNursingNumberLeaveDay2() {
			 return this.nursingNumberLeaveDay2 != null ? new NursingNumberLeaveDay(this.nursingNumberLeaveDay2) : null;
		}

		@Override
		public NursingNumberPerson getNursingNumberPerson() {			
			return this.nursingNumberPerson != null ? new NursingNumberPerson(this.nursingNumberPerson) : null;
		}

		@Override
		public NursingNumberPerson getNursingNumberPerson2() {
			return this.nursingNumberPerson2 != null ? new NursingNumberPerson(this.nursingNumberPerson2) : null;
		}
    }
}
