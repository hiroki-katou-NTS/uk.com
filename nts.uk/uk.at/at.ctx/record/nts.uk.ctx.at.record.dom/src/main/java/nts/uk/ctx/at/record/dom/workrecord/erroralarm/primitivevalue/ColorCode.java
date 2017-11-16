/**
 * 2:20:15 PM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
/* カラーコード */
@StringMaxLength(7)
public class ColorCode extends StringPrimitiveValue<ColorCode> {
	
	private static final long serialVersionUID = 1L;

	public ColorCode(String rawValue) {
		super(rawValue);
	}
}
