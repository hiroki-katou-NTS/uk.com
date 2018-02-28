/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.acquisitionrule;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
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

	/** The va ac rule. */
	public List<AcquisitionOrder> vaAcRule;
	
	/** The annualHoliday. */
	public AnnualHoliday annualHoliday;
	
	/** The hoursHoliday. */
	public HoursHoliday hoursHoliday;

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
		public ManageDistinct getCategory() {
			return ManageDistinct.valueOf(this.command.category);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.
		 * AcquisitionRuleGetMemento#getAcquisitionOrder()
		 */
		@Override
		public List<AcquisitionOrder> getAcquisitionOrder() {
			return this.command.vaAcRule;
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
		@Override
		public HoursHoliday getHoursHoliday() {
			return this.command.hoursHoliday;
		}
	}
}
