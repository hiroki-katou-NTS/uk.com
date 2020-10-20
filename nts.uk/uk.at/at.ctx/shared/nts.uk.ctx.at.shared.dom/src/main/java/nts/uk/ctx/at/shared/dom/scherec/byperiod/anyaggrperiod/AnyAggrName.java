package nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 任意集計名称
 * @author shuichu_ishida
 */
@StringMaxLength(30)
public class AnyAggrName extends StringPrimitiveValue<AnyAggrName> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param name 名称
	 */
	public AnyAggrName(String name) {
		super(name);
	}
}
