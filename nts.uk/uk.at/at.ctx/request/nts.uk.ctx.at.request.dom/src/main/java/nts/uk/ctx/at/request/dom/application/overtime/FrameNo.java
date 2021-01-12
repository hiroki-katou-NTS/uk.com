package nts.uk.ctx.at.request.dom.application.overtime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;
/**
 * Refactor5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.残業休出申請
 * @author hoangnd
 *
 */
@IntegerMaxValue(10)
@IntegerMinValue(1)
// 枠NO
public class FrameNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public FrameNo(Integer rawValue) {
		super(rawValue);
	}
}
