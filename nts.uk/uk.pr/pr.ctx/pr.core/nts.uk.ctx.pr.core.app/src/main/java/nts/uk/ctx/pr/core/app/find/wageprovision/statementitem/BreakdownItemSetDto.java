package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;

@Value
public class BreakdownItemSetDto {

	/**
	 * 内訳項目コード
	 */
	private String breakdownItemCode;

	/**
	 * 内訳項目名称
	 */
	private String breakdownItemName;

	public static BreakdownItemSetDto fromDomain(BreakdownItemSet domain) {
		return new BreakdownItemSetDto(domain.getBreakdownItemCode().v(), domain.getBreakdownItemName().v());
	}
}
