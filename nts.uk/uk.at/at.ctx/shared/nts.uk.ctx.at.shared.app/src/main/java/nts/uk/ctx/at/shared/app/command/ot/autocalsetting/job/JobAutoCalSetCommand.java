/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.job;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalRaisingSalarySettingDto;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalRestTimeSettingDto;
import nts.uk.ctx.at.shared.app.command.ot.autocalsetting.AutoCalcOfLeaveEarlySettingDto;
import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.JobTitleId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * The Class JobAutoCalSetCommand.
 */
@Setter
@Getter
public class JobAutoCalSetCommand {

	/** The job ID. */
	private String jobId;

	/** The normal OT time. */
	private AutoCalOvertimeSettingDto normalOTTime;

	/** The flex OT time. */
	private AutoCalFlexOvertimeSettingDto flexOTTime;

	/** The rest time. */
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
	 * @return the job auto cal set command
	 */
	public JobAutoCalSetting toDomain(String companyId) {
		return new JobAutoCalSetting(new DtoGetMemento(companyId, this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements JobAutoCalSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private JobAutoCalSetCommand command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public DtoGetMemento(String companyId, JobAutoCalSetCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.
		 * JobAutoCalSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * JobAutoCalSettingGetMemento#getPositionId()
		 */
		@Override
		public JobTitleId getPositionId() {
			return new JobTitleId(this.command.getJobId());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
		 * ComAutoCalSettingGetMemento#getNormalOTTime()
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
