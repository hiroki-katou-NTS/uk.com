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
@Getter
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
				return self.paymentType;
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
				return self.category;
			}
		});
	}
}
