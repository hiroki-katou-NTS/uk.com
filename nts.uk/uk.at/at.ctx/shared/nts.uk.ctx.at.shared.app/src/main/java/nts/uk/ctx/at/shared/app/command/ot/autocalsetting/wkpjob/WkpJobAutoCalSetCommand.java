/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalRaisingSalarySettingDto;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalcOfLeaveEarlySettingDto;
import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkpjob.WkpJobAutoCalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * The Class WkpJobAutoCalSetCommand.
 */

/**
 * Sets the rest time.
 *
 * @param restTime
 *            the new rest time
 */
@Setter

/**
 * Gets the rest time.
 *
 * @return the rest time
 */
@Getter
public class WkpJobAutoCalSetCommand {

	/** The wkp id. */
	private String wkpId;

	/** The job id. */
	private String jobId;

	/** The normal OT time. */
	// 残業時間
	private AutoCalOvertimeSettingDto normalOTTime;

	/** The flex OT time. */
	// フレックス超過時間
	private AutoCalFlexOvertimeSettingDto flexOTTime;

	/** The rest time. */
	// 休出時間
	private AutoCalRestTimeSettingDto restTime;

	/** The leave early. */
	// 遅刻早退
	private AutoCalcOfLeaveEarlySettingDto leaveEarly;

	/** The raising salary. */
	// 加給
	private AutoCalRaisingSalarySettingDto raisingSalary;

	/** The set of divergence time. */
	// 乖離時間
	private Integer divergenceTime;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the wkp job auto cal setting
	 */
	public WkpJobAutoCalSetting toDomain(String companyId) {
		return new WkpJobAutoCalSetting(new DtoGetMemento(companyId, this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements WkpJobAutoCalSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private WkpJobAutoCalSetCommand command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(String companyId, WkpJobAutoCalSetCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.
		 * WkpJobAutoCalSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.
		 * WkpJobAutoCalSettingGetMemento#getNormalOTTime()
		 */
		@Override
		public AutoCalOvertimeSetting getNormalOTTime() {
			return this.command.getNormalOTTime().toDomain();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * ComAutoCalSettingGetMemento#getFlexOTTime()
		 */
		@Override
		public AutoCalFlexOvertimeSetting getFlexOTTime() {
			return this.command.getFlexOTTime().toDomain();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * ComAutoCalSettingGetMemento#getRestTime()
		 */
		@Override
		public AutoCalRestTimeSetting getRestTime() {
			return this.command.getRestTime().toDomain();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * WkpJobAutoCalSettingGetMemento#getWkpId()
		 */
		@Override
		public WorkplaceId getWkpId() {
			return new WorkplaceId(this.command.getWkpId());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * WkpJobAutoCalSettingGetMemento#getPositionId()
		 */
		@Override
		public JobTitleId getJobId() {
			return new JobTitleId(this.command.getJobId());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingGetMemento#getLeaveEarly()
		 */
		@Override
		public AutoCalcOfLeaveEarlySetting getLeaveEarly() {
			return new AutoCalcOfLeaveEarlySetting(this.command.leaveEarly.isLate(),
					this.command.leaveEarly.isLeaveEarly());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingGetMemento#getRaisingSalary()
		 */
		@Override
		public AutoCalRaisingSalarySetting getRaisingSalary() {
			return new AutoCalRaisingSalarySetting(this.command.raisingSalary.isSpecificRaisingSalaryCalcAtr(),
					this.command.raisingSalary.isRaisingSalaryCalcAtr());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.com.ComAutoCalSettingGetMemento#getDivergenceTime()
		 */
		@Override
		public AutoCalcSetOfDivergenceTime getDivergenceTime() {
			return new AutoCalcSetOfDivergenceTime(DivergenceTimeAttr.valueOf(this.command.divergenceTime));
		}

	}

}
