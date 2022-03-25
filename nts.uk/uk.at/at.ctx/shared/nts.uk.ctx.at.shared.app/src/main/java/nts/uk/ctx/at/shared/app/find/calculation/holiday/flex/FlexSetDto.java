package nts.uk.ctx.at.shared.app.find.calculation.holiday.flex;

import lombok.Data;

/**
 * @author phongtq 
 * フレックス勤務の設定 
 * The class Flex Set Dto
 */

@Data
public class FlexSetDto {
	/** 半日休日の計算方法.不足計算 */
	public int missCalcHd;
	
	/** 半日休日の計算方法.割増計算 */
	public int premiumCalcHd;
	
	/** 代休取得時の計算方法.所定から控除するかどうか */
	public int isDeductPred;
	
	/** 代休取得時の計算方法.割増計算 */
	public int premiumCalcSubhd;
	
	/** 代休取得時の計算方法.時間代休時の計算設定 */
	public int calcSetTimeSubhd;

	/** 非勤務日計算.設定 */
	public int flexNoworkingCalc;
}
