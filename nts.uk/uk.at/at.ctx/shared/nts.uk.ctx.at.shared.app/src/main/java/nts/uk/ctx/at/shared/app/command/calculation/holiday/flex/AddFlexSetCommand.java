package nts.uk.ctx.at.shared.app.command.calculation.holiday.flex;
/**
 * @author phongtq
 * The class Add Flex Set Command
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.calculation.holiday.flex.FlexSet;

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

	public FlexSet toDomain(String companyId) {
		return FlexSet.createFromJavaType(companyId, this.missCalcHd, this.premiumCalcHd, this.missCalcSubhd,
				this.premiumCalcSubhd);
	}
}
