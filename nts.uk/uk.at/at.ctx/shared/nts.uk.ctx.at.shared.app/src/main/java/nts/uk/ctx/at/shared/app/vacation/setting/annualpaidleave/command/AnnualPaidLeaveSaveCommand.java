/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualLeaveGrantDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplaySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxRemainingDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RetentionYear;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearVacationTimeMaxDay;

/**
 * The Class AnnualPaidLeaveSaveCommand.
 */
@Setter
@Getter
public class AnnualPaidLeaveSaveCommand {

    /** The annual manage. */
    private Integer annualManage;

    /** The add attendance day. */
    private Integer addAttendanceDay;

    /** The max manage semi vacation. */
    private Integer maxManageSemiVacation;

    /** The max number semi vacation. */
    private Integer maxNumberSemiVacation;

    /** The max number company. */
    private Integer maxNumberCompany;

    /** The max grant day. */
    private Double maxGrantDay;

    /** The max remaining day. */
    private Double maxRemainingDay;

    /** The number year retain. */
    private Integer numberYearRetain;

    /** The preemption annual vacation. */
    private Integer preemptionAnnualVacation;

    /** The preemption year leave. */
    private Integer preemptionYearLeave;

    /** The remaining number display. */
    private Integer remainingNumberDisplay;

    /** The next grant day display. */
    private Integer nextGrantDayDisplay;

    /** The time manage type. */
    private Integer timeManageType;

    /** The time unit. */
    private Integer timeUnit;

    /** The manage max day vacation. */
    private Integer manageMaxDayVacation;

    /** The reference. */
    private Integer reference;

    /** The max time day. */
    private Integer maxTimeDay;

    /** The is enough time one day. */
    private Boolean isEnoughTimeOneDay;

    /**
     * To domain.
     *
     * @param companyId the company id
     * @return the annual paid leave setting
     */
    public AnnualPaidLeaveSetting toDomain(String companyId) {
        return new AnnualPaidLeaveSetting(new AnnualPaidLeaveSettingGetMementoImpl(companyId, this));
    }

    /**
     * The Class AnnualPaidLeaveSettingGetMementoImpl.
     */
    private class AnnualPaidLeaveSettingGetMementoImpl implements AnnualPaidLeaveSettingGetMemento {

        /** The company id. */
        private String companyId;

        /** The command. */
        private AnnualPaidLeaveSaveCommand command;

        /**
         * Instantiates a new annual paid leave setting get memento impl.
         *
         * @param companyId the company id
         * @param command the command
         */
        public AnnualPaidLeaveSettingGetMementoImpl(String companyId, AnnualPaidLeaveSaveCommand command) {
            this.companyId = companyId;
            this.command = command;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getYearManageType()
         */
        @Override
        public ManageDistinct getYearManageType() {
            return ManageDistinct.valueOf(command.annualManage);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getAcquisitionSetting()
         */
        @Override
        public AcquisitionSetting getAcquisitionSetting() {
            AcquisitionSetting setting = AcquisitionSetting.builder()
                    .permitType(ApplyPermission.valueOf(this.command.preemptionYearLeave))
                    .annualPriority(AnnualPriority.valueOf(this.command.preemptionAnnualVacation))
                    .build();
            return setting;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getManageAnnualSetting()
         */
        @Override
        public ManageAnnualSetting getManageAnnualSetting() {
            return new ManageAnnualSetting(new ManageAnnualSettingGetMementoImpl(this.companyId, this.command));
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * AnnualPaidLeaveSettingGetMemento#getTimeSetting()
         */
        @Override
        public TimeVacationSetting getTimeSetting() {
            return new TimeVacationSetting(new TimeVacationSettingGetMementoIpml(this.companyId, this.command));
        }
    }

    /**
     * The Class ManageAnnualSettingGetMementoImpl.
     */
    private class ManageAnnualSettingGetMementoImpl implements ManageAnnualSettingGetMemento {
        
        /** The company id. */
        private String companyId;
        
        /** The command. */
        private AnnualPaidLeaveSaveCommand command;

        /**
         * Instantiates a new manage annual setting get memento impl.
         *
         * @param companyId the company id
         * @param command the command
         */
        public ManageAnnualSettingGetMementoImpl(String companyId, AnnualPaidLeaveSaveCommand command) {
            this.companyId = companyId;
            this.command = command;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * ManageAnnualSettingGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * ManageAnnualSettingGetMemento#getMaxGrantDay()
         */
        @Override
        public AnnualLeaveGrantDay getMaxGrantDay() {
            return this.command.maxGrantDay == null ? null : new AnnualLeaveGrantDay(this.command.maxGrantDay);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * ManageAnnualSettingGetMemento#getHalfDayManage()
         */
        @Override
        public HalfDayManage getHalfDayManage() {
            HalfDayManage halfDay = HalfDayManage.builder()
                    .manageType(ManageDistinct.valueOf(this.command.maxManageSemiVacation))
                    .reference(MaxDayReference.valueOf(this.command.maxNumberSemiVacation))
                    .maxNumberUniformCompany(new AnnualNumberDay(this.command.maxNumberCompany))
                    .build();
            return halfDay;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * ManageAnnualSettingGetMemento#getIsWorkDayCalculate()
         */
        @Override
        public boolean getIsWorkDayCalculate() {
            return this.command.addAttendanceDay == 1 ? true : false;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * ManageAnnualSettingGetMemento#getRemainingNumberSetting()
         */
        @Override
        public RemainingNumberSetting getRemainingNumberSetting() {
            RemainingNumberSetting remain = RemainingNumberSetting.builder()
                    .retentionYear(new RetentionYear(this.command.numberYearRetain))
                    .remainingDayMaxNumber(this.command.maxRemainingDay != null ? new MaxRemainingDay(
                            this.command.maxRemainingDay) : null)
                    .build();
            return remain;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * ManageAnnualSettingGetMemento#getDisplaySetting()
         */
        @Override
        public DisplaySetting getDisplaySetting() {
            DisplaySetting display = DisplaySetting.builder()
                    .nextGrantDayDisplay(DisplayDivision.valueOf(this.command.nextGrantDayDisplay))
                    .remainingNumberDisplay(DisplayDivision.valueOf(this.command.remainingNumberDisplay))
                    .build();
            return display;
        }
    }

    /**
     * The Class TimeVacationSettingGetMementoIpml.
     */
    private class TimeVacationSettingGetMementoIpml implements TimeVacationSettingGetMemento {
        
        /** The company id. */
        private String companyId;
        
        /** The command. */
        private AnnualPaidLeaveSaveCommand command;

        /**
         * Instantiates a new time vacation setting get memento ipml.
         *
         * @param companyId the company id
         * @param command the command
         */
        public TimeVacationSettingGetMementoIpml(String companyId, AnnualPaidLeaveSaveCommand command) {
            this.companyId = companyId;
            this.command = command;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * TimeVacationSettingGetMemento#getCompanyId()
         */
        @Override
        public String getCompanyId() {
            return this.companyId;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * TimeVacationSettingGetMemento#getTimeManageType()
         */
        @Override
        public ManageDistinct getTimeManageType() {
            return ManageDistinct.valueOf(this.command.timeManageType);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * TimeVacationSettingGetMemento#getTimeUnit()
         */
        @Override
        public TimeVacationDigestiveUnit getTimeUnit() {
            return TimeVacationDigestiveUnit.valueOf(this.command.timeUnit);
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * TimeVacationSettingGetMemento#getMaxYearDayLeave()
         */
        @Override
        public YearVacationTimeMaxDay getMaxYearDayLeave() {
            YearVacationTimeMaxDay timeMaxDay = YearVacationTimeMaxDay.builder()
                    .manageType(ManageDistinct.valueOf(this.command.manageMaxDayVacation))
                    .reference(MaxDayReference.valueOf(this.command.reference))
                    .maxNumberUniformCompany(new MaxTimeDay(this.command.maxTimeDay))
                    .build();
            return timeMaxDay;
        }

        /*
         * (non-Javadoc)
         * 
         * @see nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.
         * TimeVacationSettingGetMemento#isEnoughTimeOneDay()
         */
        @Override
        public boolean isEnoughTimeOneDay() {
            return this.command.isEnoughTimeOneDay;
        }

    }
}
