package nts.uk.ctx.pr.core.app.find.wageprovision.statementitem;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.deductionitemset.DeductionItemSet;

/**
 * 控除項目設定
 */
@Value
public class DeductionItemSetDto {

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 給与項目ID
	 */
	private String salaryItemId;

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

	public static DeductionItemSetDto fromDomain(DeductionItemSet domain) {
		return new DeductionItemSetDto(domain.getCid(), domain.getSalaryItemId(), domain.getDeductionItemAtr().value,
				domain.getBreakdownItemUseAtr().value, domain.getNote().orElse(null));
	}

}
