package nts.uk.ctx.at.request.dom.setting.company.displayname;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 表示名
 */
@StringMaxLength(12)
public class DispName extends StringPrimitiveValue<DispName>{

	public DispName(String rawValue) {
		super(rawValue);
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

}
