package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min=1,max=999)
public class MasterCopyCategoryOrder extends IntegerPrimitiveValue<MasterCopyCategoryOrder> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MasterCopyCategoryOrder(Integer rawValue) {
		super(rawValue);
	}

}
