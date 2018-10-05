package nts.uk.ctx.exio.dom.exo.condset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * 外部出力条件名称
 */
@StringMaxLength(20)
public class ExternalOutputConditionName extends StringPrimitiveValue<ExternalOutputConditionName> {

	private static final long serialVersionUID = 1L;

	public ExternalOutputConditionName(String rawValue) {
		super(rawValue);
	}
}