package nts.uk.ctx.at.shared.app.find.calculation.holiday.flex;

import lombok.Data;

@Data
public class FlexSetDto {
	/** 不足計算 */
	public int missCalcHd;

	/** 割増計算 */
	public int premiumCalcHd;

	/** 不足計算 */
	public int missCalcSubhd;

	/** 割増計算 */
	public int premiumCalcSubhd;
}
