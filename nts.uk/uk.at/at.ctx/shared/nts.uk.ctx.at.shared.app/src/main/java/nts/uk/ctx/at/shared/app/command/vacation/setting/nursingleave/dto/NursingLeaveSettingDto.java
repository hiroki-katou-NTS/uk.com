/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingGetMemento;
import nts.uk.shr.com.time.calendar.MonthDay;

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
        public MonthDay getStartMonthDay() {
            //return this.setting.startMonthDay;
        	int month = this.setting.startMonthDay / 100;
        	int day = this.setting.startMonthDay % 100;
        	return new MonthDay(month, day);
        }

        /*
         * (non-Javadoc)
         *
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * NursingVacationSettingGetMemento#getMaxPersonSetting()
         */
        @Override
//        public MaxPersonSetting getMaxPersonSetting() {
//            return new MaxPersonSetting(new JpaMaxPersonSettingGetMemento(this.setting.nursingNumberLeaveDay,
//                    this.setting.nursingNumberPerson));
//        }
        public List<MaxPersonSetting> getMaxPersonSetting() {
        	List<MaxPersonSetting> maxPersonSetting = new ArrayList<>();

        	maxPersonSetting.add(MaxPersonSetting.of(new ChildCareNurseUpperLimit(this.setting.nursingNumberLeaveDay), new NumberOfCaregivers(1)));
        	maxPersonSetting.add(MaxPersonSetting.of(new ChildCareNurseUpperLimit(this.setting.nursingNumberLeaveDay), new NumberOfCaregivers(2)));

            return maxPersonSetting;
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
        public ChildCareNurseUpperLimit getNursingNumberLeaveDay() {
            return this.nursingNumberLeaveDay != null ? new ChildCareNurseUpperLimit(this.nursingNumberLeaveDay) : null;
        }

        /*
         * (non-Javadoc)
         *
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
         * MaxPersonSettingGetMemento#getNursingNumberPerson()
         */
        @Override
        public NumberOfCaregivers getNursingNumberPerson() {
            return this.nursingNumberPerson != null ? new NumberOfCaregivers(this.nursingNumberPerson) : null;
        }
    }
}
