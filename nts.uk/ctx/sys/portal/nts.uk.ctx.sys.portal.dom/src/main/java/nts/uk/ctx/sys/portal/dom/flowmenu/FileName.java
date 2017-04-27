/**
 * author hieult
 */
package nts.uk.ctx.sys.portal.dom.flowmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(36)
public class FileName extends StringPrimitiveValue<FileName> {

	private static final long serialVersionUID = 1L;

	public FileName(String rawValue) {
		super(rawValue);
	}

}
