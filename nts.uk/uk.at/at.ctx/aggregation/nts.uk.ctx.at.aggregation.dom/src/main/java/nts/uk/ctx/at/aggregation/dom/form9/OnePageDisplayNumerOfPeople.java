package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * １ページ表示人数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.１ページ表示人数
 * @author lan_lt
 * 
 */
@IntegerRange(min = 0, max = 9999999)
public class OnePageDisplayNumerOfPeople extends IntegerPrimitiveValue<OnePageDisplayNumerOfPeople>{

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public OnePageDisplayNumerOfPeople(Integer rawValue) {
		super(rawValue);
	}

}
