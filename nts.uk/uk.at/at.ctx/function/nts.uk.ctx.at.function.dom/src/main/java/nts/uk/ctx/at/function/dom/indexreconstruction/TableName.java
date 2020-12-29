package nts.uk.ctx.at.function.dom.indexreconstruction;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 	テーブル日本語名
 *	 テーブル物理名
 * @author ngatt-nws
 *
 */
@StringMaxLength(60)
public class TableName extends StringPrimitiveValue<TableName> {

	private static final long serialVersionUID = 1L;
	
	public TableName(String rawValue) {
		super(rawValue);
	}

}
