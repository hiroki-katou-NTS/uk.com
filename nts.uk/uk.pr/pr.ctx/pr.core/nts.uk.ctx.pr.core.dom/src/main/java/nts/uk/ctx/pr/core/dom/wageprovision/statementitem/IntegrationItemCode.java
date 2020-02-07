package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author thanh.tq 統合コード
 *
 */
@StringMaxLength(2)
@StringCharType(CharType.NUMERIC)
public class IntegrationItemCode extends StringPrimitiveValue<PrimitiveValue<String>> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param rawValue
	 */
	public IntegrationItemCode(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
