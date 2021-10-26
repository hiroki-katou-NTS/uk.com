package nts.uk.ctx.at.aggregation.dom.form9;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 様式９の名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の名称
 * @author lan_lt
 *
 */
@StringMaxLength(40)
public class Form9Name extends StringPrimitiveValue<Form9Name>{

	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public Form9Name(String rawValue) {
		super(rawValue);
	}

}
