package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import lombok.Value;

/**
 * 控除項目設定
 */
@Value
public class DeductionItemSetCommand {

	/**
	 * 控除項目区分
	 */
	private int deductionItemAtr;

	/**
	 * 内訳項目利用区分
	 */
	private int breakdownItemUseAtr;

	/**
	 * 備考
	 */
	private String note;

}
