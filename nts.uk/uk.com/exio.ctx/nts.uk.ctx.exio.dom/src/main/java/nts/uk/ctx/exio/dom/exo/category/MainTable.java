package nts.uk.ctx.exio.dom.exo.category;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(60)
public class MainTable extends StringPrimitiveValue<MainTable> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainTable(String rawValue) {
		super(rawValue);
	}

}
