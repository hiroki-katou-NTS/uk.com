package nts.uk.ctx.at.schedule.dom.shift.specificdaysetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 特定日項目NO
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.特定日設定.特定日項目NO
 */
@IntegerRange( min = 1, max = 10 )
public class SpecificDateItemNo extends IntegerPrimitiveValue<SpecificDateItemNo> {
	
	/** serialVersionUID **/
	private static final long serialVersionUID = 1L;

	public SpecificDateItemNo(Integer rawValue) {
		
		super(rawValue);
		
	}
	
}
