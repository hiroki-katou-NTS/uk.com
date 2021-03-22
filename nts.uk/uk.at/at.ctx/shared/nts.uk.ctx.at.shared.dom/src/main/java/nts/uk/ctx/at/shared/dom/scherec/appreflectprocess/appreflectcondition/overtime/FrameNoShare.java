package nts.uk.ctx.at.shared.dom.application.overtime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;

/**
 * 
 * @author thanh_nx
 *
 */
@IntegerMaxValue(9999)
public class FrameNoShare extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public FrameNoShare(Integer rawValue) {
		super(rawValue);
	}

}
