package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMaxValue;
import nts.arc.primitive.constraint.DecimalMinValue;

/**
 * 特定加給時間項目No
 * @author keisuke_hoshina
 *
 */
@DecimalMaxValue("10")
@DecimalMinValue("1")
public class SpecBonusPayNumber extends DecimalPrimitiveValue<SpecBonusPayNumber>{
	/**
	 *  The Constant serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SpecBonusPayNumber(BigDecimal rawValue) {
		super(rawValue);
	}
}
