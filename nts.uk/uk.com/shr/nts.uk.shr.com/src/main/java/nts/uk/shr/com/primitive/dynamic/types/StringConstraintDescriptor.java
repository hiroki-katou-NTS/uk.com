package nts.uk.shr.com.primitive.dynamic.types;

import java.util.Map;

import lombok.Value;
import nts.arc.primitive.constraint.CharType;

@Value
public class StringConstraintDescriptor {

	private final int maxLength;
	private final CharType charType;
	private final char paddingCharacter;
	private final boolean isPaddingLeft;
	
}
