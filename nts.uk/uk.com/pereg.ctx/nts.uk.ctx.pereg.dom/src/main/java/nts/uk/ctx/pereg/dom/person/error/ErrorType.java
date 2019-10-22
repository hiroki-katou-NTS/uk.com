package nts.uk.ctx.pereg.dom.person.error;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * エラー区分
 * @author lanlt
 *
 */
@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum ErrorType {
	ERROR(0),
	WARNING(1);
	private final int value;
}
