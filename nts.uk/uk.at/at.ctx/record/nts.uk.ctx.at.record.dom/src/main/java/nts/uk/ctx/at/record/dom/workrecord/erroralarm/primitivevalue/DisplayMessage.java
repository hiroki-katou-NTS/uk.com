/**
 * 2:19:07 PM Jul 21, 2017
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author hungnm
 *
 */
/* エラーアラームメッセージ */
@StringMaxLength(200)
public class DisplayMessage extends StringPrimitiveValue<DisplayMessage> {

	private static final long serialVersionUID = 1L;
	
	public DisplayMessage(String rawValue) {
		super(rawValue);
	}

}
