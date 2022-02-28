package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 出力列
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.出力列
 * @author lan_lt
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHABET)
public class OutputColumn extends UpperCaseAlphaNumericPrimitiveValue<OutputColumn>{

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public OutputColumn(String rawValue) {
		super(rawValue);
	}

}
