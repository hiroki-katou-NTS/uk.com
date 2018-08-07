package nts.uk.ctx.at.function.dom.monthlyworkschedule;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * The Class MonthlyOutputItemSettingName.
 */
@StringMaxLength(20)
//出力項目設定名称
public class MonthlyOutputItemSettingName extends StringPrimitiveValue<MonthlyOutputItemSettingName>{

	/**
	 * Instantiates a new monthly output item setting name.
	 *
	 * @param rawValue the raw value
	 */
	public MonthlyOutputItemSettingName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -79662585620624116L;

}
