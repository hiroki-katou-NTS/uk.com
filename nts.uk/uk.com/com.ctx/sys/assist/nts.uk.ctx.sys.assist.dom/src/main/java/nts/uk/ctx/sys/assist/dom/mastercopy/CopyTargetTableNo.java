package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

//コピー対象テーブルNO
@IntegerRange(min=1,max=999)
public class CopyTargetTableNo extends IntegerPrimitiveValue<CopyTargetTableNo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CopyTargetTableNo(Integer rawValue) {
		super(rawValue);
	}

}
