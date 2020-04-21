package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 打刻ボタン位置NO
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.打刻ボタン位置NO
 * @author laitv
 *
 */
@IntegerRange(min = 1, max = 8)
public class ButtonPositionNo  extends IntegerPrimitiveValue<ButtonPositionNo> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public ButtonPositionNo(Integer rawValue) {
		super(rawValue);
	}
}

