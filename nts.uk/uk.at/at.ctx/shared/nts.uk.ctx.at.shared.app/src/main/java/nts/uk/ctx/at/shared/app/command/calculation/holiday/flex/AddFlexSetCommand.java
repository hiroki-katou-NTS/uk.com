package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;
/**
 * @author phongtq
 * The class Add Flex Set Command
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;

@Data
@AllArgsConstructor
public class AddFlexSetCommand {

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

	public FlexSet toDomain(String companyId) {
		return FlexSet.createFromJavaType(companyId, this.missCalcHd, this.premiumCalcHd, this.isDeductPred,
				this.premiumCalcSubhd, this.calcSetTimeSubhd, this.flexNoworkingCalc);
	}
}
