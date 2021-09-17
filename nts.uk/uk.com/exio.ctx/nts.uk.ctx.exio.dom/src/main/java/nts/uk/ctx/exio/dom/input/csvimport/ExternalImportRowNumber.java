package nts.uk.ctx.exio.dom.input.csvimport;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 
 * 受入行番号
 *
 */
@SuppressWarnings("serial")
@IntegerMinValue(1)
@IntegerMaxValue(999999999)
public class ExternalImportRowNumber extends IntegerPrimitiveValue<ExternalImportRowNumber> {

	public ExternalImportRowNumber(Integer rawValue) {
		super(rawValue);
	}
}
