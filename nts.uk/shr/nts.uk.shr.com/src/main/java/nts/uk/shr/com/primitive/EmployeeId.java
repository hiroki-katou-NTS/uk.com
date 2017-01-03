package nts.uk.shr.com.primitive;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.gul.text.IdentifierUtil;

@StringMaxLength(36)
@StringCharType(CharType.ALPHA_NUMERIC)
public class EmployeeId extends CodePrimitiveValue<EmployeeId>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	public EmployeeId(String rawValue) {
		super(rawValue);
	}
	
	/**
     * create new UserId instance
     * 
     * @return new UserId instance
     */
    public static EmployeeId newPersonId() {
        return new EmployeeId(IdentifierUtil.randomUniqueId());
    }
}
