package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;
/**
 * 出力列
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.出力列
 * @author lan_lt
 *
 */
@StringMaxLength(3)
@StringRegEx("[a-zA-Z]")
public class OutputColumn extends UpperCaseAlphaNumericPrimitiveValue<OutputColumn>{

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public OutputColumn(String rawValue) {
		super(rawValue);
	}

}
