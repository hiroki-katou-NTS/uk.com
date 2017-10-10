package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(10)
public class ExternalCD extends StringPrimitiveValue<ExternalCD>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExternalCD(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
