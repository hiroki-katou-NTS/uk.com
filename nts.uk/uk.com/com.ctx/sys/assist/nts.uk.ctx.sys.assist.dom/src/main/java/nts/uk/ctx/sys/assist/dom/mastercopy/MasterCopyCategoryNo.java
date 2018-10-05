package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

//マスタコピーカテゴリNO
@IntegerRange(min=1,max=999)
public class MasterCopyCategoryNo extends IntegerPrimitiveValue<MasterCopyCategoryNo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MasterCopyCategoryNo(Integer rawValue) {
		super(rawValue);
	}

}
