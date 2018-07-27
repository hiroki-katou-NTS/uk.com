package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selection.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 
 * @author tuannv
 *
 */
@StringMaxLength(500)
public class MemoSelection extends StringPrimitiveValue<MemoSelection> {

	private static final long serialVersionUID = 1L;

	public MemoSelection(String rawValue) {
		super(rawValue);
	}

}
