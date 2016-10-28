package nts.uk.shr.com.primitive;

import nts.arc.primitive.PrimitiveValueUtil;
import nts.arc.primitive.StringPrimitiveValue;
import nts.gul.text.StringUtil;

/**
 * CodePrimitiveValue
 * @param <S> type
 */
public class CodePrimitiveValue<S> extends StringPrimitiveValue<CodePrimitiveValue<S>> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs.
	 * @param rawValue raw value (will be padded with space until max length)
	 */
	public CodePrimitiveValue(String rawValue) {
		super(rawValue, param -> padSpace(rawValue, param));
	}
    
    /**
     * Returns true if this code is equal otherCode.
     * (if otherCode is not padded, will be padded with space before compare)
     * 
     * @param otherCode other code
     * @return result
     */
    public boolean equals(String otherCode) {
        String otherCodePadded = padSpace(otherCode, this.getClass());
        return this.v().equals(otherCodePadded);
    }
    
	private static String padSpace(String rawCode, Object[] param) {
		Class codeClass = param[1].getClass();
        return padSpace(rawCode, codeClass);
	}
    
    private static String padSpace(String rawCode, Class codeClass) {
        int maxLength = PrimitiveValueUtil.getStringMaxLength(codeClass);
        if (maxLength <= 0) {
            return rawCode;
        }
        return StringUtil.padRight(rawCode, maxLength, ' ');
    }
}
