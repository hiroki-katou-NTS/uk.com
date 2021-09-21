package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 出力セル
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.出力セル
 * @author lan_lt
 *
 */
@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
public class OutputCell extends StringPrimitiveValue<OutputCell> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public OutputCell(String rawValue) {
		super(rawValue);
	}
	
}
