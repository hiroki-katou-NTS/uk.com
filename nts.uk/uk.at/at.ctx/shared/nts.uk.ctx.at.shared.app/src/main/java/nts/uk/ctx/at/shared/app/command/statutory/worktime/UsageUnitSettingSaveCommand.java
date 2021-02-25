/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingGetMemento;

/**
 * The Class UsageUnitSettingSaveCommand.
 */
@Getter
@Setter
public class UsageUnitSettingSaveCommand {
	/** The employee. */
	// 社員の労働時間と日数の管理をする
	private boolean employee;

	/** The work place. */
	// 職場の労働時間と日数の管理をする
	private boolean workPlace;

	/** The employment. */
	// 雇用の労働時間と日数の管理をする
	private boolean employment;

	/**
	 * To domain.
	 *
	 * @param companyId the company id
	 * @return the usage unit setting
	 */

	public UsageUnitSetting toDomain(String companyId) {
		return new UsageUnitSetting(new UsageUnitSettingGetMementoImpl(this, companyId));
	}

	/**
	 * The Class UsageUnitSettingGetMementoImpl.
	 */
	class UsageUnitSettingGetMementoImpl implements UsageUnitSettingGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private UsageUnitSettingSaveCommand command;

		/**
		 * Instantiates a new usage unit setting get memento impl.
		 *
		 * @param command the command
		 * @param companyId the company id
		 */
		public UsageUnitSettingGetMementoImpl(UsageUnitSettingSaveCommand command,
				String companyId) {
			this.command = command;
			this.companyId = companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
		 * UsageUnitSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
		 * UsageUnitSettingGetMemento#getEmployee()
		 */
		@Override
		public boolean getEmployee() {
			return command.isEmployee();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
		 * UsageUnitSettingGetMemento#getWorkPlace()
		 */
		@Override
		public boolean getWorkPlace() {
			return command.isWorkPlace();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.employment.statutory.worktime.
		 * UsageUnitSettingGetMemento#getEmployment()
		 */
		@Override
		public boolean getEmployment() {
			return command.isEmployment();
		}
	}
}
