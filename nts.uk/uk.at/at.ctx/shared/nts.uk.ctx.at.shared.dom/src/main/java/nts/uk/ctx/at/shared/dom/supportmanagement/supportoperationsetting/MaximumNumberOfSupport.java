package nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 最大応援回数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.応援管理.応援の運用設定.最大応援回数
 * @author laitv
 *
 */
@IntegerRange(min = 1, max = 20)
public class MaximumNumberOfSupport extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{

	private static final long serialVersionUID = 1L;
	
	public MaximumNumberOfSupport(Integer rawValue) {
		super(rawValue);
	}
	
	public static int getMax() {
		IntegerRange annotation = MaximumNumberOfSupport.class.getAnnotation(IntegerRange.class);
		return annotation.max();
	}
}
