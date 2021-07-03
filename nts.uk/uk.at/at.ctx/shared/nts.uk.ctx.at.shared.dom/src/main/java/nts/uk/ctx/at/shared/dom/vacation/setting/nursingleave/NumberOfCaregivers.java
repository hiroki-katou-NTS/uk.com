package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 要介護看護対象人数
 * @author yuri_tamakoshi
 *
 */
@IntegerRange(min = 0, max = 99)
public class NumberOfCaregivers extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		public NumberOfCaregivers(int rawValue) {
			super(rawValue);
		}
}
