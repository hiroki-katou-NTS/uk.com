package nts.uk.ctx.at.request.dom.setting.request.application.common;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.NUMERIC)
@StringMaxLength(2)
public class RetrictPreTimeDay extends DecimalPrimitiveValue<RetrictPreTimeDay> {
	private static final long serialVersionUID = 1L;

	public RetrictPreTimeDay(BigDecimal rawValue) {
		super(rawValue);
	}

}
