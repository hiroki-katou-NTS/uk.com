/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.salarydetail.aggregate.command.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.pr.report.app.salarydetail.aggregate.find.dto.SalaryItemDto;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItem;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemName;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.TaxDivision;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;

/**
 * The Class SalaryAggregateItemSaveDto.
 */

/**
 * Instantiates a new salary aggregate item save dto.
 */
@Data
public class SalaryAggregateItemSaveDto {

	/** The salary aggregate item code. */
	private String salaryAggregateItemCode;

	/** The salary aggregate item name. */
	private String salaryAggregateItemName;

	/** The sub item codes. */
	private List<SalaryItemDto> subItemCodes;

	/** The tax division. */
	private int taxDivision;

	/** The category code. */
	private String categoryCode;

	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the salary aggregate item
	 */
	public SalaryAggregateItem toDomain(String companyCode) {
		return new SalaryAggregateItem(new SalaryAggregateItemGetMementoImpl(companyCode, this));
	}

	/**
	 * The Class SalaryAggregateItemGetMementoImpl.
	 */
	public class SalaryAggregateItemGetMementoImpl implements SalaryAggregateItemGetMemento {

		/** The company code. */
		private String companyCode;

		/** The dto. */
		private SalaryAggregateItemSaveDto dto;

		/**
		 * Instantiates a new salary aggregate item get memento impl.
		 *
		 * @param companyCode the company code
		 * @param dto the dto
		 */
		public SalaryAggregateItemGetMementoImpl(String companyCode, SalaryAggregateItemSaveDto dto) {
			this.dto = dto;
			this.companyCode = companyCode;
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento#getCompanyCode()
		 */
		@Override
		public CompanyCode getCompanyCode() {
			return new CompanyCode(this.companyCode);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento#getSalaryAggregateItemCode()
		 */
		@Override
		public SalaryAggregateItemCode getSalaryAggregateItemCode() {
			return new SalaryAggregateItemCode(this.dto.salaryAggregateItemCode);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento#getSalaryAggregateItemName()
		 */
		@Override
		public SalaryAggregateItemName getSalaryAggregateItemName() {
			return new SalaryAggregateItemName(this.dto.salaryAggregateItemName);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento#getSubItemCodes()
		 */
		@Override
		public Set<SalaryItem> getSubItemCodes() {
			return this.dto.getSubItemCodes().stream().map(itemCode -> {
				SalaryItem salaryItem = new SalaryItem();
				salaryItem.setSalaryItemCode(itemCode.getSalaryItemCode());
				salaryItem.setSalaryItemName(itemCode.getSalaryItemName());
				return salaryItem;
			}).collect(Collectors.toSet());
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento#getTaxDivision()
		 */
		@Override
		public TaxDivision getTaxDivision() {
			return TaxDivision.valueOf(this.dto.taxDivision);
		}

		/* (non-Javadoc)
		 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemGetMemento#getItemCategory()
		 */
		@Override
		public int getItemCategory() {
			return 1;
		}

	}

}
