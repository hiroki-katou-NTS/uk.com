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
@StringMaxLength(200)
public class FeatureDescriptionOfDailyPerformance extends StringPrimitiveValue<FeatureDescriptionOfDailyPerformance>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public FeatureDescriptionOfDailyPerformance(String rawValue) {
		super(rawValue);
	}

}
