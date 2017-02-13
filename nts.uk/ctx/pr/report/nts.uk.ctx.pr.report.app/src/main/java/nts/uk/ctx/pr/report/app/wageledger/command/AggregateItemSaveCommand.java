/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.val;
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
public class AggregateItemSaveCommand {
	
	/** The category. */
	public String category;
	
	/** The payment type. */
	public String paymentType;
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The show name zero value. */
	public Boolean showNameZeroValue;
	
	/** The show value zero value. */
	public Boolean showValueZeroValue;
	
	/** The sub items. */
	public List<String> subItems;
	
	/** The is create mode. */
	public boolean isCreateMode;
	
	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the WL aggregate item
	 */
	public WLAggregateItem toDomain(String companyCode) {
		val self = this;
		return new WLAggregateItem(new WLAggregateItemGetMemento() {
			
			@Override
			public Set<String> getSubItems() {
				return new HashSet<String>(self.subItems);
			}
			
			@Override
			public Boolean getShowValueZeroValue() {
				return self.showValueZeroValue;
			}
			
			@Override
			public Boolean getShowNameZeroValue() {
				return self.showNameZeroValue;
			}
			
			@Override
			public PaymentType getPaymentType() {
				return PaymentType.valueOfName(self.paymentType);
			}
			
			@Override
			public WLAggregateItemName getName() {
				return new WLAggregateItemName(self.name);
			}
			
			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(self.code);
			}
			
			@Override
			public WLAggregateItemCode getCode() {
				return new WLAggregateItemCode(self.code);
			}
			
			@Override
			public WLCategory getCategory() {
				return WLCategory.valueOfName(self.category);
			}
		});
	}
}
