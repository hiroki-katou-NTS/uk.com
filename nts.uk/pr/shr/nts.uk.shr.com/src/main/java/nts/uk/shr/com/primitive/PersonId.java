package nts.uk.shr.com.primitive;

import nts.arc.primitive.constraint.StringMaxLengh;
import nts.gul.text.IdentifierUtil;

@StringMaxLengh(36)
public class PersonId extends CodePrimitiveValue<PersonId>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	public PersonId(String rawValue) {
		super(rawValue);
	}
	
	/**
     * create new UserId instance
     * 
     * @return new UserId instance
     */
    public static PersonId newPersonId() {
        return new PersonId(IdentifierUtil.randomUniqueId());
    }
}
