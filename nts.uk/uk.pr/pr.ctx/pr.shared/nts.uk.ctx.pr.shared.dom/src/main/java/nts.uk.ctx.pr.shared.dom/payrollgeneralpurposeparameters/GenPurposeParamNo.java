package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 汎用パラメータNo
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(6)
@ZeroPaddedCode
public class GenPurposeParamNo extends CodePrimitiveValue<GenPurposeParamNo> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new sequence code.
     *
     * @param rawValue the raw value
     */
    public GenPurposeParamNo(String rawValue) {
        super(rawValue);
    }

}