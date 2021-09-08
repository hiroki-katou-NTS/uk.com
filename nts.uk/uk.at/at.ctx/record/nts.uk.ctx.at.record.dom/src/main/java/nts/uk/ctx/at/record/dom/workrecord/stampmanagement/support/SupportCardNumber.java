package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 応援カード番号
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.応援カード番号
 * @author laitv
 *
 */
@IntegerRange(max = 999999, min = 0)
public class SupportCardNumber extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	public SupportCardNumber(Integer rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;

}
