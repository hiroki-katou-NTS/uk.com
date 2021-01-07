package nts.uk.ctx.at.function.dom.indexreconstruction;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.DecimalMantissaMaxLength;
import nts.arc.primitive.constraint.DecimalRange;

/**
 * 	断片化率
 */
@DecimalRange(min = "0", max = "100")
@DecimalMantissaMaxLength(2)
public class FragmentationRate extends DecimalPrimitiveValue<FragmentationRate> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public FragmentationRate(BigDecimal rawValue) {
		super(rawValue);
	}
}
