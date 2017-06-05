/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AcquisitionVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplaySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.HalfDayManage;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.PreemptionPermit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RemainingNumberSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RetentionYear;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.TimeVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.YearVacationTimeMaxDay;

/**
 * The Class ManageAnnualSettingDto.
 */
@Getter
@Setter
public class ManageAnnualSettingDto {

	/** The add attendance day. */
	private Integer addAttendanceDay;

	/** The max manage semi vacation. */
	private Integer maxManageSemiVacation;

	/** The max number semi vacation. */
	private Integer maxNumberSemiVacation;

	/** The max number company. */
	private BigDecimal maxNumberCompany;

	/** The max grant day. */
	private BigDecimal maxGrantDay;

	/** The max remaining day. */
	private Integer maxRemainingDay;

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
	 * @return the manage annual setting
	 */
	public ManageAnnualSetting toDomain(String companyId) {
		return new ManageAnnualSetting(new ManageAnnualSettingGetMementoImpl(companyId, this));
	}

	/**
	 * The Class ManageAnnualSettingGetMementoImpl.
	 */
	public class ManageAnnualSettingGetMementoImpl implements ManageAnnualSettingGetMemento {

	    private String companyId;
	    
		/** The setting. */
		private ManageAnnualSettingDto setting;

		/**
		 * Instantiates a new manage annual setting get memento impl.
		 *
		 * @param setting
		 *            the setting
		 */
		public ManageAnnualSettingGetMementoImpl(String companyId, ManageAnnualSettingDto setting) {
		    this.companyId = companyId;
			this.setting = setting;
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
		 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
		 * ManageAnnualSettingGetMemento#getRemainingNumberSetting()
		 */
		@Override
		public RemainingNumberSetting getRemainingNumberSetting() {
			RemainingNumberSetting remain = new RemainingNumberSetting();
			remain.setWorkDayCalculate(ManageDistinct.valueOf(setting.addAttendanceDay));
			HalfDayManage halfDayManage = HalfDayManage.builder()
					.manageType(ManageDistinct.valueOf(setting.maxManageSemiVacation))
					.reference(MaxDayReference.valueOf(setting.maxNumberSemiVacation))
					.maxNumberUniformCompany(setting.maxNumberCompany == null ? null : new AnnualNumberDay(
					        setting.maxNumberCompany))
					.build();
			remain.setHalfDayManage(halfDayManage);
			if (setting.maxGrantDay != null) {
			    remain.setMaximumDayVacation(new AnnualLeaveGrantDate(setting.maxGrantDay));
			}
			remain.setRemainingDayMaxNumber(setting.maxRemainingDay);
			if (setting.numberYearRetain != null) {
			    remain.setRetentionYear(new RetentionYear(setting.numberYearRetain));
			}
			return remain;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
		 * ManageAnnualSettingGetMemento#getAcquisitionSetting()
		 */
		@Override
		public AcquisitionVacationSetting getAcquisitionSetting() {
			return AcquisitionVacationSetting.builder()
					.yearVacationPriority(ApplyPermission.valueOf(setting.preemptionAnnualVacation))
					.permitType(PreemptionPermit.valueOf(setting.preemptionYearLeave))
					.build();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
		 * ManageAnnualSettingGetMemento#getDisplaySetting()
		 */
		@Override
		public DisplaySetting getDisplaySetting() {
			return DisplaySetting.builder()
			        .remainingNumberDisplay(DisplayDivision.valueOf(setting.remainingNumberDisplay))
					.nextGrantDayDisplay(DisplayDivision.valueOf(setting.nextGrantDayDisplay))
					.build();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.
		 * ManageAnnualSettingGetMemento#getTimeSetting()
		 */
		@Override
		public TimeVacationSetting getTimeSetting() {
			TimeVacationSetting time = new TimeVacationSetting();
			time.setTimeManageType(ManageDistinct.valueOf(setting.timeManageType));
			time.setTimeUnit(TimeVacationDigestiveUnit.valueOf(setting.timeUnit));
			YearVacationTimeMaxDay timeMaxDay = YearVacationTimeMaxDay.builder()
					.manageMaxDayVacation(ManageDistinct.valueOf(setting.manageMaxDayVacation))
					.reference(MaxDayReference.valueOf(setting.reference))
					.maxTimeDay(setting.maxTimeDay == null ? null : new MaxTimeDay(setting.maxTimeDay))
					.build();
			time.setMaxDay(timeMaxDay);
			time.setEnoughTimeOneDay(setting.isEnoughTimeOneDay);
			return time;
		}
	}
}
