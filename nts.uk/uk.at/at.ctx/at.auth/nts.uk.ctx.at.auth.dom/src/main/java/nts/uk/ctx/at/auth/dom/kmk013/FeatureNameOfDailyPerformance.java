/**
 * 
 */
package nts.uk.ctx.at.auth.dom.kmk013;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author tutk
 *
 */
@StringMaxLength(60)
public class FeatureNameOfDailyPerformance extends StringPrimitiveValue<FeatureNameOfDailyPerformance>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public FeatureNameOfDailyPerformance(String rawValue) {
		super(rawValue);
	}

}
