package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 打刻時刻補正時間
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.打刻時刻補正時間
 * @author laitv
 *
 */

@IntegerMinValue(1)
@IntegerMaxValue(60)
public class CorrectionInterval extends IntegerPrimitiveValue<PrimitiveValue<Integer>> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public CorrectionInterval(Integer rawValue) {
		super(rawValue);
	}
}
