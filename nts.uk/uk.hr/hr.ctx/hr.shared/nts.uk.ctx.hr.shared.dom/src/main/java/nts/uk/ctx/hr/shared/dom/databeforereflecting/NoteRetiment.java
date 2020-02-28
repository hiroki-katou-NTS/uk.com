package nts.uk.ctx.hr.shared.dom.databeforereflecting;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(100)
public class NoteRetiment extends StringPrimitiveValue<NoteRetiment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoteRetiment(String rawValue) {
		super(rawValue);
	}

}
