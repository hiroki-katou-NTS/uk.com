package nts.uk.ctx.pr.report.app.salarydetail.aggregate.find.dto;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Data;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemCode;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemName;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemSetMemento;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.TaxDivision;
import nts.uk.ctx.pr.report.dom.salarydetail.item.SalaryItem;

@Data
public class SalaryAggregateItemFindDto implements SalaryAggregateItemSetMemento {

	/** The code. */
	private String salaryAggregateItemCode;

	/** The name. */
	private String salaryAggregateItemName;

	/** The sub item codes. */
	private List<SalaryItemDto> subItemCodes;

	@Override
	public void setSalaryAggregateItemCode(SalaryAggregateItemCode salaryAggregateItemCode) {
		this.salaryAggregateItemCode = salaryAggregateItemCode.v();
	}

	@Override
	public void setSalaryAggregateItemName(SalaryAggregateItemName salaryAggregateItemName) {
		this.salaryAggregateItemName = salaryAggregateItemName.v();

	}

	@Override
	public void setSubItemCodes(Set<SalaryItem> subItemCodes) {
		this.subItemCodes = subItemCodes.stream().map(item -> {
			SalaryItemDto salaryItemDto = new SalaryItemDto();
			salaryItemDto.setSalaryItemCode(item.getSalaryItemCode());
			salaryItemDto.setSalaryItemName(item.getSalaryItemName());
			return salaryItemDto;
		}).collect(Collectors.toList());
	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// No thing

	}

	@Override
	public void setTaxDivision(TaxDivision taxDivision) {
		// No thing

	}

	@Override
	public void setItemCategory(int itemCategory) {
		// No thing

	}

}
