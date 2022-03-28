package nts.uk.ctx.at.shared.dom.worktime.predset;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.shared.就業規則.就業時間帯.共通設定.所定時間.勤務NO
 * @author Doan Duy Hung
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(10)
public class WorkNo extends IntegerPrimitiveValue<WorkNo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WorkNo(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}
}
