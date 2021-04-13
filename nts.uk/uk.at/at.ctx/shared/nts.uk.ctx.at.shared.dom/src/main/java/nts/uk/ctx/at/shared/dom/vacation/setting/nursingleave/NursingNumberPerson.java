package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 
 * @author quytb
 * 要介護看護対象人数
 *
 */

@IntegerRange(max = 99, min = 0)
public class NursingNumberPerson extends IntegerPrimitiveValue<NursingNumberPerson> {
	
	private static final long serialVersionUID = 2591689184216412962L;
	
	public NursingNumberPerson(Integer rawValue) {
		super(rawValue);
	}
}
