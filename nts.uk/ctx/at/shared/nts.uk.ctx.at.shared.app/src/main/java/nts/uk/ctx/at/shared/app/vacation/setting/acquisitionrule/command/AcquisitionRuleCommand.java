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

	
	/** The category. */
	public Category category;

	/** The va ac rule. */
	public List<AcquisitionOrder> vaAcRule;
	
	public AcquisitionRule toDomain(String companyId, List<AcquisitionOrder> oldAcquisitionOrders) {
		return new AcquisitionRule(new AcquisitionRuleGetMementoImpl(companyId, this, oldAcquisitionOrders));
	}

	public class AcquisitionRuleGetMementoImpl implements AcquisitionRuleGetMemento {

		private String companyId;
		private AcquisitionRuleCommand command;
		private List<AcquisitionOrder> oldAcquisitionOrders;
		
		public AcquisitionRuleGetMementoImpl(String companyId, AcquisitionRuleCommand command,
				List<AcquisitionOrder> oldAcquisitionOrders) {
			this.companyId = companyId;
			this.command = command;
			this.oldAcquisitionOrders = oldAcquisitionOrders;
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento#getCategory()
		 */
		@Override
		public Category getCategory() {
			return this.command.category;
		}

		/**
		 * Gets the acquisition order.
		 *
		 * @return the acquisition order
		 */
		@Override
		public List<AcquisitionOrder> getAcquisitionOrder() {
			if (this.command.category == Category.Setting){
				return this.command.vaAcRule;
			}
			return this.oldAcquisitionOrders;
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
