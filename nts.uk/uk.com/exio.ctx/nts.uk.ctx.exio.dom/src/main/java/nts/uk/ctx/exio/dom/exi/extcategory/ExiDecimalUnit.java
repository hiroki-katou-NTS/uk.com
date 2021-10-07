package nts.uk.ctx.exio.dom.exi.extcategory;

import lombok.AllArgsConstructor;
/**
 * 	小数部単位区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ExiDecimalUnit {
	/**	制限無し */
	NO_LIMIT(0),
	/**	少数不可（項目として少数が存在するが不可な項目 */
	NO_DECIMAL(1),
	/**	０．５単位のみ可（．５か．０のみ可能） */
	YES_DECIMAL(2);
	
	public final Integer value;
}
