package nts.uk.ctx.pr.core.dom.wageprovision.statementitem;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 項目名称
 */
@StringMaxLength(20)
public class ItemName extends StringPrimitiveValue<ItemName> {

	private static final long serialVersionUID = 1L;

	public ItemName(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}