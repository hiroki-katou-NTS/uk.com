/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.aggregate.find.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemName;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemSetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.TaxDivision;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;

/**
 * Instantiates a new salary aggregate item find dto.
 */
@Getter
@Setter
public class SalaryAggregateItemFindDto implements SalaryAggregateItemSetMemento {

	/** The salary aggregate item code. */
	private String salaryAggregateItemCode;

	/** The salary aggregate item name. */
	private String salaryAggregateItemName;

	/** The tax division. */
	private TaxDivision taxDivision;

	/** The sub item codes. */
	private List<SalaryItemDto> subItemCodes;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setSalaryAggregateItemCode(nts.uk.ctx.pr.
	 * report.dom.salarydetail.aggregate.SalaryAggregateItemCode)
	 */
	@Override
	public void setSalaryAggregateItemCode(SalaryAggregateItemCode salaryAggregateItemCode) {
		this.salaryAggregateItemCode = salaryAggregateItemCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setSalaryAggregateItemName(nts.uk.ctx.pr.
	 * report.dom.salarydetail.aggregate.SalaryAggregateItemName)
	 */
	@Override
	public void setSalaryAggregateItemName(SalaryAggregateItemName salaryAggregateItemName) {
		this.salaryAggregateItemName = salaryAggregateItemName.v();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setSubItemCodes(java.util.Set)
	 */
	@Override
	public void setSubItemCodes(Set<SalaryItem> subItemCodes) {
		this.subItemCodes = subItemCodes.stream().map(item -> {
			SalaryItemDto salaryItemDto = new SalaryItemDto();
			salaryItemDto.setSalaryItemCode(item.getSalaryItemCode());
			salaryItemDto.setSalaryItemName(item.getSalaryItemName());
			return salaryItemDto;
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		// No thing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setTaxDivision(nts.uk.ctx.pr.report.dom.
	 * salarydetail.aggregate.TaxDivision)
	 */
	@Override
	public void setTaxDivision(TaxDivision taxDivision) {
		this.taxDivision = taxDivision;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemSetMemento#setItemCategory(int)
	 */
	@Override
	public void setItemCategory(int itemCategory) {
		// No thing

	}

}
