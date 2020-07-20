package nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.shortworktime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * refactor 4
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.短時間勤務.短時間勤務枠NO
 * @author Doan Duy Hung
 *
 */
@IntegerRange(max = 2, min = 1)
public class ShortWorkTimeFrameNo extends IntegerPrimitiveValue<ShortWorkTimeFrameNo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4014000581983586950L;

	public ShortWorkTimeFrameNo(int rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
