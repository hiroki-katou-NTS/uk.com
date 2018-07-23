package nts.uk.ctx.sys.assist.dom.storage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

//データ保存補足説明
@StringMaxLength(40)
public class Explanation extends StringPrimitiveValue<Explanation>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Explanation(String rawValue) {
		super(rawValue);
	}

}
