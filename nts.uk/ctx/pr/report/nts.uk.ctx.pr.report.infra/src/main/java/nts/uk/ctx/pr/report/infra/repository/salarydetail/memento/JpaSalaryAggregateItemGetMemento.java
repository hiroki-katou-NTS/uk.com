/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemName;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.TaxDivision;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHead;

/**
 * The Class JpaSalaryAggregateItemGetMemento.
 */
public class JpaSalaryAggregateItemGetMemento implements SalaryAggregateItemGetMemento {

	/** The agger head. */

	/**
	 * Gets the agger head.
	 *
	 * @return the agger head
	 */
	@Getter
	private QlsptPaylstAggreHead aggerHead;

	/**
	 * Instantiates a new jpa salary aggregate item get memento.
	 *
	 * @param aggerHead
	 *            the agger head
	 */
	public JpaSalaryAggregateItemGetMemento(QlsptPaylstAggreHead aggerHead) {
		this.aggerHead = aggerHead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getCompanyCode()
	 */
	@Override
	public CompanyCode getCompanyCode() {
		return new CompanyCode(this.aggerHead.getQlsptPaylstAggreHeadPK().getCcd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getSalaryAggregateItemCode()
	 */
	@Override
	public SalaryAggregateItemCode getSalaryAggregateItemCode() {
		return new SalaryAggregateItemCode(this.aggerHead.getQlsptPaylstAggreHeadPK().getAggregateCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getSalaryAggregateItemName()
	 */
	@Override
	public SalaryAggregateItemName getSalaryAggregateItemName() {
		return new SalaryAggregateItemName(this.aggerHead.getAggregateName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getSubItemCodes()
	 */
	@Override
	public Set<SalaryItem> getSubItemCodes() {
		return this.aggerHead.getQlsptPaylstAggreDetailList().stream().map(item -> {
			SalaryItem salaryItem = new SalaryItem();
			salaryItem.setSalaryItemCode(item.getQlsptPaylstAggreDetailPK().getItemCd());
			salaryItem.setSalaryItemName("基本給 " + item.getQlsptPaylstAggreDetailPK().getItemCd());
			return salaryItem;
		}).collect(Collectors.toSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getTaxDivision()
	 */
	@Override
	public TaxDivision getTaxDivision() {
		return TaxDivision.valueOf(this.aggerHead.getQlsptPaylstAggreHeadPK().getCtgAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getItemCategory()
	 */
	@Override
	public int getItemCategory() {
		return 1;
	}

}
