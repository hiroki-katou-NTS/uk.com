package nts.uk.ctx.sys.assist.dom.mastercopy;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

//コピー対象テーブル名称
@StringMaxLength(40)
public class CopyTargetTableName extends StringPrimitiveValue<CopyTargetTableName> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CopyTargetTableName(String rawValue) {
		super(rawValue);
	}

}
