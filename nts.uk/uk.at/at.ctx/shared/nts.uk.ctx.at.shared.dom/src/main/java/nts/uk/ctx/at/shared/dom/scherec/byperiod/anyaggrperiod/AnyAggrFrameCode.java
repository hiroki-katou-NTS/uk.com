package nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * 任意集計枠コード
 * @author shuichu_ishida
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class AnyAggrFrameCode extends CodePrimitiveValue<AnyAggrFrameCode> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param code コード
	 */
	public AnyAggrFrameCode(String code) {
		super(code);
	}
}
