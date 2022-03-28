package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 出力行目
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.出力行目
 * @author lan_lt
 *
 */
@IntegerRange(min= 1, max= 1048576)
public class OutputRow extends IntegerPrimitiveValue<OutputRow>{

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;
	
	public OutputRow(Integer rawValue) {
		super(rawValue);
	}

}
