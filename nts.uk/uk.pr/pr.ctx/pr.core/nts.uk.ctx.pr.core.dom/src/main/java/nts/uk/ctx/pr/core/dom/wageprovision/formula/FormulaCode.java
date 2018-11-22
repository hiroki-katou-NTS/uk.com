package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

<<<<<<< HEAD
/**
 * 
 * @author HungTT - 計算式コード
 *
 */

@StringMaxLength(3)
@StringCharType(CharType.ALPHA_NUMERIC)
=======
@StringCharType(CharType.NUMERIC)
@StringMaxLength(3)
>>>>>>> pj/pr/team_G/QMM019
@ZeroPaddedCode
public class FormulaCode extends CodePrimitiveValue<FormulaCode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormulaCode(String rawValue) {
		super(rawValue);
	}

}
