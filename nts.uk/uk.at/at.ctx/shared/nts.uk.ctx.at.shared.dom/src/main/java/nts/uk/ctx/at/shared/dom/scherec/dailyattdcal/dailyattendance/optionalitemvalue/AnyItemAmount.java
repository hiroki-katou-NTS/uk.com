package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue;


import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 日次任意金額
 * @author tutk
 *
 */
@IntegerRange(min = -999999, max = 999999)
public class AnyItemAmount extends IntegerPrimitiveValue<AnyItemAmount>{

	private static final long serialVersionUID = 1L;
	
	public AnyItemAmount(Integer rawValue) {
		super(rawValue.compareTo(999999) > 0 ? 999999 
						        			 : rawValue.compareTo(-999999) < 0 ? -999999 
								   		             						   : rawValue);
		
	}
}