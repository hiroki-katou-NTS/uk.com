package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;

@Value
public class BreakdownItemSetDto {

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

	/**
	 * 内訳項目コード
	 */
	private int breakdownItemCode;

	/**
	 * 内訳項目名称
	 */
	private String breakdownItemName;

	public static BreakdownItemSetDto fromDomain(BreakdownItemSet domain) {
		return new BreakdownItemSetDto(domain.getSalaryItemId(), domain.getBreakdownItemCode().v(),
				domain.getBreakdownItemName().v());
	}
}
