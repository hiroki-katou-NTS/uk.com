package nts.uk.ctx.exio.dom.exo.condset;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/*
 * 外部出力ファイル名
 */
@StringMaxLength(200)
public class ExternalOutputFileName extends StringPrimitiveValue<ExternalOutputFileName> {

	private static final long serialVersionUID = 1L;

	public ExternalOutputFileName(String rawValue) {
		super(rawValue);
	}
}