package nts.uk.ctx.exio.dom.input.validation.condition.system;

import nts.arc.enums.EnumAdaptor;

/**
 * Enumの定義に基づいて検証するやつ
 */
public class ValidationEnum {
	public static boolean run(String fqn, Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			EnumAdaptor.valueOf((int)value, pvClass);
			return true;
		} catch (Exception ex) {
			//エラー処理
			throw new RuntimeException("Enumの検証　仮置きエクスセプションです。");
		}
	}
}
