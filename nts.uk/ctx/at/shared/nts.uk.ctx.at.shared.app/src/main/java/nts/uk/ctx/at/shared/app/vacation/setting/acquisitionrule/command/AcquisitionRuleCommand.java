/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionOrder;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.Category;

/**
 * The Class VacationAcquisitionRuleBaseCommand.
 */
@Getter
@Setter
public class AcquisitionRuleCommand {

	/** The setting classification. */
	public Category settingClassification;

	/** The va ac rule. */
	public List<AcquisitionOrder> vaAcRule;
	
	public AcquisitionRule toDomain(String companyId) {
		return new AcquisitionRule(new AcquisitionRuleGetMementoImpl(companyId, this));
	}

	public class AcquisitionRuleGetMementoImpl implements AcquisitionRuleGetMemento {

		private String companyId;
		private AcquisitionRuleCommand command;
		
		public AcquisitionRuleGetMementoImpl(String companyId, AcquisitionRuleCommand command) {
			this.companyId = companyId;
			this.command = command;
		}

		/**
		 * Gets the settingclassification.
		 *
		 * @return the settingclassification
		 */
		@Override
		public Category getSettingclassification() {
			return this.command.settingClassification;
		}

		/**
		 * Gets the acquisition order.
		 *
		 * @return the acquisition order
		 */
		@Override
		public List<AcquisitionOrder> getAcquisitionOrder() {
			return this.command.vaAcRule;
		}

		/**
		 * Gets the company id.
		 *
		 * @return the company id
		 */
		@Override
		public String getCompanyId() {
			return this.companyId;
		}
	}
}
