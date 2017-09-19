package nts.uk.shr.com.primitive.dynamic.types;

import lombok.Value;

@Value
public class IntegerConstraintDescriptor {

	private final int min;
	private final int max;
}
