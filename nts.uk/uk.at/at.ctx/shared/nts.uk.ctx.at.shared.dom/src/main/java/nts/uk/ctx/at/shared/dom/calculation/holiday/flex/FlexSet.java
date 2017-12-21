package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
@AllArgsConstructor
@Getter
public class FlexSet extends AggregateRoot{
	
	/** 会社ID */
	private String companyId;
	
	/** 不足計算 */
	public int missCalcHd;
	
	/** 割増計算 */
	public int premiumCalcHd;
	
	/** 不足計算*/
	public int missCalcSubhd;
	
	/** 割増計算 */
	public int premiumCalcSubhd;
	
	public static FlexSet createFromJavaType(String companyId, int missCalcHd, int premiumCalcHd, int missCalcSubhd, int premiumCalcSubhd){
		return new FlexSet(companyId,missCalcHd, premiumCalcHd, missCalcSubhd, premiumCalcSubhd);
	}
}
