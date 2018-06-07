package nts.uk.ctx.at.shared.dom.remainingnumber.base;

import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.constraint.HalfIntegerRange;

/**
 * 管理データ日数
 * @author shuichu_ishida
 */
@HalfIntegerRange(min = 0, max = 1.0)
public class ManagementDays extends HalfIntegerPrimitiveValue<ManagementDays> {

	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ
	 * @param days 日数
	 */
	public ManagementDays(Double days){
		super(days);
	}
}
