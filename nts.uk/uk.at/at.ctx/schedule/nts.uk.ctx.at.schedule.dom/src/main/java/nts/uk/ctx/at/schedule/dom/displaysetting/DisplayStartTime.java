package nts.uk.ctx.at.schedule.dom.displaysetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 表示の開始時刻
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.表示設定.表示の開始時刻
 * @author hiroko_miura
 *
 */
@IntegerRange(min =0, max =12)
public class DisplayStartTime extends IntegerPrimitiveValue<DisplayStartTime> {
	
	private static final long serialVersionUID = 1L;
	
	public DisplayStartTime (int rawValue) {
		super (rawValue);
	}
}
