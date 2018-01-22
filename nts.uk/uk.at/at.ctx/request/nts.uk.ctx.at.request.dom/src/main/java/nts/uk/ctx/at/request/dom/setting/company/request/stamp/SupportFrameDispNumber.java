package nts.uk.ctx.at.request.dom.setting.company.request.stamp;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 応援枠の表示件数
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 5, min = 1)
public class SupportFrameDispNumber extends IntegerPrimitiveValue<SupportFrameDispNumber>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -27043334976919661L;

	public SupportFrameDispNumber(Integer rawValue) {
		super(rawValue);
	}

}
