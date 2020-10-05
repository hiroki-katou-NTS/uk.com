/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.calculationattribute.AutoCalcSetOfDivergenceTime;
import nts.uk.ctx.at.shared.dom.calculationattribute.enums.DivergenceTimeAttr;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalFlexOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalOvertimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalcOfLeaveEarlySetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com.ComAutoCalSettingGetMemento;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.AutoCalRaisingSalarySetting;

/**
 * The Class ComAutoCalSetCommand.
 */
@Setter
@Getter
public class ComAutoCalSetCommand {

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
	 * @param companyId the company id
	 * @return the com auto cal setting
	 */
	public ComAutoCalSetting toDomain(String companyId) {
		return new ComAutoCalSetting(new DtoGetMemento(companyId, this));
	}

	/**
	 * The Class DtoGetMemento.
	 */
	private class DtoGetMemento implements ComAutoCalSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private ComAutoCalSetCommand command;

		/**
		 * Instantiates a new dto get memento.
		 *
		 * @param companyId the company id
		 * @param command the command
		 */
		public DtoGetMemento(String companyId, ComAutoCalSetCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesGetMemento#
		 * getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
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
