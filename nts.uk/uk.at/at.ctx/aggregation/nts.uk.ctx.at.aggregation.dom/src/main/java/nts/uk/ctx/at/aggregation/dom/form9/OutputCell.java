package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.UpperCaseAlphaNumericPrimitiveValue;
import nts.arc.primitive.constraint.StringRegEx;
/**
 * 出力セル
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.出力セル
 * @author lan_lt
 *
 */
@StringRegEx("[a-zA-Z]{1,3}[1-9]{1}[0-9]{0,6}")
public class OutputCell extends UpperCaseAlphaNumericPrimitiveValue<OutputCell> {
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public OutputCell(String rawValue) {
		super(rawValue);
	}
	
}
