package nts.uk.ctx.at.record.dom.executionstatusmanage.optionalperiodprocess.periodinfor;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * エラーメッセージ
 * @author phongtq
 *
 */
@StringMaxLength(500)
public class ErrorMess extends StringPrimitiveValue<PrimitiveValue<String>>{


	private static final long serialVersionUID = 1L;

	public ErrorMess(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
