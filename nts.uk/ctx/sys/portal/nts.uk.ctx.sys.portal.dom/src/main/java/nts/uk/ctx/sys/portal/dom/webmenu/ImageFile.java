package nts.uk.ctx.sys.portal.dom.webmenu;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(36)
public class ImageFile extends StringPrimitiveValue<ImageFile>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ImageFile(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
