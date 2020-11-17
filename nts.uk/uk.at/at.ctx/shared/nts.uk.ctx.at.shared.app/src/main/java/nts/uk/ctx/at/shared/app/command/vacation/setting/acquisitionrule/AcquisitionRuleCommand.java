/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.acquisitionrule;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.HoursHoliday;

/**
 * The Class AcquisitionRuleCommand.
 */
@Getter
@Setter
public class AcquisitionRuleCommand {

	/** The category. */
	public int category;
	
	/** The annualHoliday. */
	public AnnualHoliday annualHoliday;
	


	/**
	 * To domain.
	 *
	 * @param companyId
	 *            the company id
	 * @return the acquisition rule
	 */
	public AcquisitionRule toDomain(String companyId) {
		return new AcquisitionRule(new AcquisitionRuleGetMementoImpl(companyId, this));
	}

	/**
	 * The Class AcquisitionRuleGetMementoImpl.
	 */
	public class AcquisitionRuleGetMementoImpl implements AcquisitionRuleGetMemento {

		/** The company id. */
		private String companyId;

		/** The command. */
		private AcquisitionRuleCommand command;

		/**
		 * Instantiates a new acquisition rule get memento impl.
		 *
		 * @param companyId
		 *            the company id
		 * @param command
		 *            the command
		 */
		public AcquisitionRuleGetMementoImpl(String companyId, AcquisitionRuleCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
		 * AcquisitionRuleGetMemento#getCategory()
		 */
		@Override
		public SettingDistinct getCategory() {
			return SettingDistinct.valueOf(this.command.category);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
		 * AcquisitionRuleGetMemento#getCompanyId()
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}

		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
		 * AcquisitionRuleGetMemento#getAnnualHoliday()
		 */
		@Override
		public AnnualHoliday getAnnualHoliday() {
			return this.command.annualHoliday;
		}

		/*
		 * (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
		 * AcquisitionRuleGetMemento#getHoursHoliday()
		 */
	
	}
}
