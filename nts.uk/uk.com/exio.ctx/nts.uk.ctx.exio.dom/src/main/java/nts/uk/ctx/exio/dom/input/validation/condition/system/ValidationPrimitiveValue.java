package nts.uk.ctx.exio.dom.input.validation.condition.system;

import nts.arc.primitive.PrimitiveValue;

/**
 * PrimitiveValueの定義に基づいて検証するやつ 
 */
public class ValidationPrimitiveValue {
	
	/**
	 * PrimitiveValueを検証 
	 */
	public static boolean run(String fqn, Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			PrimitiveValue<?> pv = (PrimitiveValue<?>) pvClass.getConstructors()[0].newInstance(value);
			pv.validate();
			return true;
		} catch (Exception ex) {
			//エラー時処理
			throw new RuntimeException("PrimitiveValueの検証　仮置きエクスセプションです。");
		}
	}
}
