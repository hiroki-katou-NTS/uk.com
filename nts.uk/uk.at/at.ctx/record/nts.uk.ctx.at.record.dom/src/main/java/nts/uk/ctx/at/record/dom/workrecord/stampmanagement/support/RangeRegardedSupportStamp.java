package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 応援打刻の同一とみなす範囲
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.応援打刻の同一とみなす範囲
 * @author laitv
 *
 */
@IntegerRange(max = 60, min = 1)
public class RangeRegardedSupportStamp extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	public RangeRegardedSupportStamp(Integer rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;

}
