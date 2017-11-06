/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.authormanage;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author danpv
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
