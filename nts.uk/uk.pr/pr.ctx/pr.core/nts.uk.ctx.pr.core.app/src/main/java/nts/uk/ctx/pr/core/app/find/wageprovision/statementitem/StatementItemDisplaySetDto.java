package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.StatementItemDisplaySet;

@Value
public class StatementItemDisplaySetDto {

	/**
	 * ゼロ表示区分
	 */
	private int zeroDisplayAtr;

	/**
	 * 項目名表示
	 */
	private Integer itemNameDisplay;

	public static StatementItemDisplaySetDto fromDomain(StatementItemDisplaySet domain) {
		return new StatementItemDisplaySetDto(domain.getZeroDisplayAtr().value,
				domain.getItemNameDisplay().map(i -> i.value).orElse(null));
	}
}
