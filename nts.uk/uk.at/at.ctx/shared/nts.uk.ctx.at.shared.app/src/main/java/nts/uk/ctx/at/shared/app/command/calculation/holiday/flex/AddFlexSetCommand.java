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

	/** 不足計算 */
	public int missCalcHd;

	/** 割増計算 */
	public int premiumCalcHd;

	/** 不足計算 */
	public int missCalcSubhd;

	/** 割増計算 */
	public int premiumCalcSubhd;
	
	/** 非勤務日計算 */
	public int flexDeductTimeCalc;
	
	/** 非勤務日計算 */
	public int flexNonworkingDayCalc;

	public FlexSet toDomain(String companyId) {
		return FlexSet.createFromJavaType(companyId, this.missCalcHd, this.premiumCalcHd, this.missCalcSubhd,
				this.premiumCalcSubhd, this.flexDeductTimeCalc, this.flexNonworkingDayCalc);
	}
}
