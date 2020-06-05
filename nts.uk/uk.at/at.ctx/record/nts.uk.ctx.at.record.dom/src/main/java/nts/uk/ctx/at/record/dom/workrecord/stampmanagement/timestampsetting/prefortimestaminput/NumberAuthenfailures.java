package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 指認証失敗回数
 * path : UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻設定.打刻入力の前準備.指認証失敗回数
 * @author laitv
 *
 */
@IntegerRange(min = 1, max = 10)
public class NumberAuthenfailures extends IntegerPrimitiveValue<NumberAuthenfailures> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public NumberAuthenfailures(Integer rawValue) {
		super(rawValue);
	}
}

