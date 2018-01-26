package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.deadlinesetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 月間日数
 * @author Doan Duy Hung
 *
 */
@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class Deadline extends IntegerPrimitiveValue<Deadline> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4206297749663062032L;

	public Deadline(Integer rawValue) {
		super(rawValue);
	}
}
