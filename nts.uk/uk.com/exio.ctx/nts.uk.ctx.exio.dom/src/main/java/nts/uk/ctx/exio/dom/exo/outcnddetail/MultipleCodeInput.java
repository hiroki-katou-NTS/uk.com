package nts.uk.ctx.exio.dom.exo.outcnddetail;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 複数コード入力
 */
@StringMaxLength(150)
@StringCharType(CharType.ANY_HALF_WIDTH)
public class MultipleCodeInput extends StringPrimitiveValue<PrimitiveValue<String>> {

	private static final long serialVersionUID = 1L;

	public MultipleCodeInput(String rawValue) {
		super(rawValue);
	}
}
