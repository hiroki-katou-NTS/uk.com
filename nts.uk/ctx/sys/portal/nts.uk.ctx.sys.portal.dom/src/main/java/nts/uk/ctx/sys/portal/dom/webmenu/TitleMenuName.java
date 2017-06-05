package nts.uk.ctx.sys.portal.dom.webmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author sonnh
 *
 */
@StringMaxLength(30)
public class TitleMenuName extends StringPrimitiveValue<TitleMenuName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TitleMenuName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
