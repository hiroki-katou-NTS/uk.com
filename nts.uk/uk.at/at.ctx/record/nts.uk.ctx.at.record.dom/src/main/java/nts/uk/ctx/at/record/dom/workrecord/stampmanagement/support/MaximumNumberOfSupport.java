package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 最大応援回数
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.最大応援回数
 * @author laitv
 *
 */
@IntegerRange(max = 20, min = 1)
public class MaximumNumberOfSupport extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	public MaximumNumberOfSupport(Integer rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;

}
