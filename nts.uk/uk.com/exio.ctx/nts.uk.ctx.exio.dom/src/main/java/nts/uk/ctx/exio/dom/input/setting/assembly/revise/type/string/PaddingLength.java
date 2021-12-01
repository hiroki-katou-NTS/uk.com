package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.string;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 桁埋めの長さ
 */
@SuppressWarnings("serial")
@IntegerRange(min = 0, max = 999)
public class PaddingLength extends IntegerPrimitiveValue<PaddingLength> {

	public PaddingLength(Integer rawValue) {
		super(rawValue);
	}

}
