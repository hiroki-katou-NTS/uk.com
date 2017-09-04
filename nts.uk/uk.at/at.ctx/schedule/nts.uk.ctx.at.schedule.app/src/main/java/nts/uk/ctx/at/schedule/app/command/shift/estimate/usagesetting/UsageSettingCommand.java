/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.estimate.usagesetting;

import lombok.Data;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSetting;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UsageSettingGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.UseClassification;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class UsageSettingCommand.
 */
@Data
public class UsageSettingCommand {

	/** The employment setting. */
	private boolean employmentSetting;

	/** The personal setting. */
	private boolean personalSetting;

	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the usage setting
	 */
	public UsageSetting toDomain(String companyId) {
		return new UsageSetting(new JpaGetMemento(companyId, this));
	}

	/**
	 * The Class JpaGetMemento.
	 */
	private class JpaGetMemento implements UsageSettingGetMemento {

		/** The command. */
		private UsageSettingCommand command;

		/** The company id. */
		private String companyId;

		/**
		 * Instantiates a new jpa get memento.
		 *
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public JpaGetMemento(String companyId, UsageSettingCommand command) {
			this.command = command;
			this.companyId = companyId;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
		 * UsageSettingGetMemento#getCompanyId()
		 */
		@Override
		public CompanyId getCompanyId() {
			return new CompanyId(this.companyId);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
		 * UsageSettingGetMemento#getEmploymentSetting()
		 */
		@Override
		public UseClassification getEmploymentSetting() {
			if (this.command.isEmploymentSetting()) {
				return UseClassification.USE;
			}
			return UseClassification.NOT_USE;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.schedule.dom.shift.estimate.usagesetting.
		 * UsageSettingGetMemento#getPersonalSetting()
		 */
		@Override
		public UseClassification getPersonalSetting() {
			
			if (this.command.isPersonalSetting()) {
				return UseClassification.USE;
			}
			return UseClassification.NOT_USE;
		}

	}

}
