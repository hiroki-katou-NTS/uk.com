/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.Set;
import java.util.stream.Collectors;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemName;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead;

/**
 * The Class JpaWLAggregateItemGetMemento.
 */
public class JpaWLAggregateItemGetMemento implements WLAggregateItemGetMemento {

	/** The entity. */
	private QlsptLedgerAggreHead entity;

	/**
	 * Instantiates a new jpa WL aggregate item get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWLAggregateItemGetMemento(QlsptLedgerAggreHead entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.entity.getQlsptLedgerAggreHeadPK().getCcd());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getCategory()
	 */
	@Override
	public WLCategory getCategory() {
		return WLCategory.valueOf(this.entity.getQlsptLedgerAggreHeadPK().getCtgAtr());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getPaymentType()
	 */
	@Override
	public PaymentType getPaymentType() {
		return PaymentType.valueOf(this.entity.getQlsptLedgerAggreHeadPK().getPayBonusAtr());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getCode()
	 */
	@Override
	public WLAggregateItemCode getCode() {
		return new WLAggregateItemCode(this.entity.getQlsptLedgerAggreHeadPK().getAggregateCd());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getName()
	 */
	@Override
	public WLAggregateItemName getName() {
		return new WLAggregateItemName(this.entity.getAggregateName());
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getShowNameZeroValue()
	 */
	@Override
	public Boolean getShowNameZeroValue() {
		return this.entity.getDispNameZeroAtr() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getShowValueZeroValue()
	 */
	@Override
	public Boolean getShowValueZeroValue() {
		return this.entity.getDispZeroAtr() == 1;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemGetMemento#
	 * getSubItems()
	 */
	@Override
	public Set<String> getSubItems() {
		return this.entity.getQlsptLedgerAggreDetailList().stream()
				.map(item -> item.getQlsptLedgerAggreDetailPK().getItemCd())
				.collect(Collectors.toSet());
	}

}
