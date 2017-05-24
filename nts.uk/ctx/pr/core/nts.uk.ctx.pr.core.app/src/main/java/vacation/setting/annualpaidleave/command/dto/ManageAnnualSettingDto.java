/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package vacation.setting.annualpaidleave.command.dto;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.DisplaySetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.PreemptionPermit;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.RetentionYear;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.TimeVacationSetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.YearVacationAge;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.YearVacationTimeMaxDay;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.YearVacationTimeUnit;

/**
 * The Class ManageAnnualSettingDto.
 */
@Getter
public class ManageAnnualSettingDto {
    
    /** The add attendance day. */
    private ManageDistinct addAttendanceDay;
    
    /** The max manage semi vacation. */
    private ManageDistinct maxManageSemiVacation;
    
    /** The max number semi vacation. */
    private MaxDayReference maxNumberSemiVacation;
    
    /** The max number company. */
    private BigDecimal maxNumberCompany;
    
    /** The max grant day. */
    private BigDecimal maxGrantDay;
    
    /** The max remaining day. */
    private Integer maxRemainingDay;
    
    /** The number year retain. */
    private RetentionYear numberYearRetain;
    
    /** The preemption annual vacation. */
    private ApplyPermission preemptionAnnualVacation;
    
    /** The preemption year leave. */
    private PreemptionPermit preemptionYearLeave;
    
    /** The remaining number display. */
    private DisplayDivision remainingNumberDisplay;
    
    /** The next grant day display. */
    private DisplayDivision nextGrantDayDisplay;
    
    /** The time manage type. */
    private ManageDistinct timeManageType;
    
    /** The time unit. */
    private YearVacationTimeUnit timeUnit;
    
    /** The manage max day vacation. */
    private ManageDistinct manageMaxDayVacation;
    
    /** The reference. */
    private MaxDayReference reference;
    
    /** The max time day. */
    private MaxTimeDay maxTimeDay;
    
    /** The is enough time one day. */
    private Boolean isEnoughTimeOneDay;
    
    /**
     * To domain.
     *
     * @return the manage annual setting
     */
    public ManageAnnualSetting toDomain() {
        return new ManageAnnualSetting(new ManageAnnualSettingGetMementoImpl(this));
    }
    
    /**
     * The Class ManageAnnualSettingGetMementoImpl.
     */
    public class ManageAnnualSettingGetMementoImpl implements ManageAnnualSettingGetMemento {
        
        /** The setting. */
        private ManageAnnualSettingDto setting;
        
        /**
         * Instantiates a new manage annual setting get memento impl.
         *
         * @param setting the setting
         */
        public ManageAnnualSettingGetMementoImpl(ManageAnnualSettingDto setting) {
            this.setting = setting;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento#getRemainingNumberSetting()
         */
        @Override
        public RemainingNumberSetting getRemainingNumberSetting() {
            RemainingNumberSetting remain = new RemainingNumberSetting();
            remain.setWorkDayCalculate(setting.addAttendanceDay);
            HalfDayManage halfDayManage = HalfDayManage.builder()
                    .manageType(setting.maxManageSemiVacation)
                    .reference(setting.maxNumberSemiVacation)
                    .maxNumberUniformCompany(new AnnualNumberDay(setting.maxNumberCompany))
                    .build();
            remain.setHalfDayManage(halfDayManage);
            remain.setMaximumDayVacation(new YearVacationAge(setting.maxGrantDay));
            remain.setRemainingDayMaxNumber(setting.maxRemainingDay);
            remain.setRetentionYear(setting.numberYearRetain);
            return remain;
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento#getAcquisitionSetting()
         */
        @Override
        public AcquisitionVacationSetting getAcquisitionSetting() {
            return AcquisitionVacationSetting.builder()
                    .yearVacationPriority(setting.preemptionAnnualVacation)
                    .permitType(setting.preemptionYearLeave)
                    .build();
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento#getDisplaySetting()
         */
        @Override
        public DisplaySetting getDisplaySetting() {
            return DisplaySetting.builder()
                    .remainingNumberDisplay(setting.remainingNumberDisplay)
                    .nextGrantDayDisplay(setting.nextGrantDayDisplay)
                    .build();
        }

        /* (non-Javadoc)
         * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento#getTimeSetting()
         */
        @Override
        public TimeVacationSetting getTimeSetting() {
            TimeVacationSetting time = new TimeVacationSetting();
            time.setTimeManageType(setting.timeManageType);
            time.setTimeUnit(setting.timeUnit);
            YearVacationTimeMaxDay timeMaxDay = YearVacationTimeMaxDay.builder()
                    .manageMaxDayVacation(setting.manageMaxDayVacation)
                    .reference(setting.reference)
                    .maxTimeDay(setting.maxTimeDay)
                    .build();
            time.setMaxDay(timeMaxDay);
            time.setEnoughTimeOneDay(setting.isEnoughTimeOneDay);
            return time;
        }
    }
}
