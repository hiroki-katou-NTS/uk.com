package nts.uk.shr.com.primitive;

import nts.arc.primitive.StringPrimitiveValue;

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
	 * @param rawValue raw value
	 */
	public CodePrimitiveValue(String rawValue) {
		super(rawValue == null ? "" : rawValue.trim());
	}
    
    /**
     * Returns true if this code is equal otherCode.
     * 
     * @param otherCode other code
     * @return result
     */
    public boolean equals(String otherCode) {
        return this.v().equals(otherCode.trim());
    }
    
    @Override
    protected String getRawValueToBeValidated() {
    	return this.v().trim();
    }
}
