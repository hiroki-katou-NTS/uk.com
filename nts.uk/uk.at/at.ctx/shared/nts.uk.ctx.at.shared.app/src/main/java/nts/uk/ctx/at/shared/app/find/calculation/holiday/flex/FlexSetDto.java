package nts.uk.ctx.at.shared.app.find.calculation.holiday.flex;

import lombok.Data;

/**
 * @author phongtq 
 * フレックス勤務の設定 
 * The class Flex Set Dto
 */

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
	
	/** 法定労働控除時間計算 */
	public int flexDeductTimeCalc;
	
	/** 非勤務日計算 */
	public int flexNonworkingDayCalc;
}
