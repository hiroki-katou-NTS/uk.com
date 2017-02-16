/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.find.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemName;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento;

/**
 * The Class AggregateItemDto.
 */
@Builder
public class AggregateItemDto implements WLAggregateItemSetMemento{
	
	/** The code. */
	public String code;
	
	/** The name. */
	public String name;
	
	/** The payment type. */
	public PaymentType paymentType;
	
	/** The category. */
	public WLCategory category;
	
	/** The show name zero value. */
	public Boolean showNameZeroValue;
	
	/** The show value zero value. */
	public Boolean showValueZeroValue;
	
	/** The sub items. */
	public List<SubItemDto> subItems;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setCompanyCode(nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setCategory(nts.uk.ctx.pr.report.dom.wageledger.WLCategory)
	 */
	@Override
	public void setCategory(WLCategory category) {
		this.category = category;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setPaymentType(nts.uk.ctx.pr.report.dom.wageledger.PaymentType)
	 */
	@Override
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setCode(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public void setCode(WLAggregateItemCode code) {
		this.code = code.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setName(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemName)
	 */
	@Override
	public void setName(WLAggregateItemName name) {
		this.name = name.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setShowNameZeroValue(java.lang.Boolean)
	 */
	@Override
	public void setShowNameZeroValue(Boolean showNameZeroValue) {
		this.showNameZeroValue = showNameZeroValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setShowValueZeroValue(boolean)
	 */
	@Override
	public void setShowValueZeroValue(boolean showValueZeroValue) {
		this.showValueZeroValue = showValueZeroValue;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemSetMemento
	 * #setSubItems(java.util.Set)
	 */
	@Override
	public void setSubItems(Set<String> subItems) {
		this.subItems = subItems.stream()
				.map(item -> SubItemDto.builder().code(item).build())
				.collect(Collectors.toList());
	}
}
