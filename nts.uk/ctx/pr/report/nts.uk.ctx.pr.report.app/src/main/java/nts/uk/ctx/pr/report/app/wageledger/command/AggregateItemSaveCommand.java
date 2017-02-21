/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemName;

/**
 * The Class AggregateItemSaveCommand.
 */

/**
 * Checks if is creates the mode.
 *
 * @return true, if is creates the mode
 */
@Getter

/**
 * Sets the creates the mode.
 *
 * @param isCreateMode the new creates the mode
 */
@Setter
public class AggregateItemSaveCommand {
	
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;
	
	/** The show name zero value. */
	private Boolean showNameZeroValue;
	
	/** The show value zero value. */
	private Boolean showValueZeroValue;
	
	/** The sub items. */
	private List<String> subItems;
	
	/** The is create mode. */
	private boolean isCreateMode;
	
	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the WL aggregate item
	 */
	public WLAggregateItem toDomain(String companyCode) {
		return new WLAggregateItem(new AggregateItemGetMementoImpl(this, companyCode));
	}
	
	/**
	 * The Class AggregateItemGetMementoImpl.
	 */
	public class AggregateItemGetMementoImpl implements WLAggregateItemGetMemento {
		
		/** The command. */
		private AggregateItemSaveCommand command;
		
		/** The company code. */
		private String companyCode;
		
		/**
		 * Instantiates a new aggregate item get memento impl.
		 *
		 * @param command the command
		 * @param companyCode the company code
		 */
		public AggregateItemGetMementoImpl(AggregateItemSaveCommand command, String companyCode) {
			super();
			this.command = command;
			this.companyCode = companyCode;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getSubItems()
		 */
		@Override
		public Set<String> getSubItems() {
			return new HashSet<String>(this.command.getSubItems());
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getShowValueZeroValue()
		 */
		@Override
		public Boolean getShowValueZeroValue() {
			return this.command.getShowValueZeroValue();
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getShowNameZeroValue()
		 */
		@Override
		public Boolean getShowNameZeroValue() {
			return this.command.getShowNameZeroValue();
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getPaymentType()
		 */
		@Override
		public PaymentType getPaymentType() {
			return this.command.getPaymentType();
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getName()
		 */
		@Override
		public WLAggregateItemName getName() {
			return new WLAggregateItemName(this.command.getName());
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getCompanyCode()
		 */
		@Override
		public CompanyCode getCompanyCode() {
			return new CompanyCode(this.companyCode);
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getCode()
		 */
		@Override
		public WLAggregateItemCode getCode() {
			return new WLAggregateItemCode(this.command.getCode());
		}
		
		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento
		 * #getCategory()
		 */
		@Override
		public WLCategory getCategory() {
			return this.command.getCategory();
		}
	}
}
