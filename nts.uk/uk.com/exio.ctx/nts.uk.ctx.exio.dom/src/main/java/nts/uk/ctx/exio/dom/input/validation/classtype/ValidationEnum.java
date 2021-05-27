package nts.uk.ctx.exio.dom.input.validation.classtype;

import nts.arc.enums.EnumAdaptor;

public class ValidationEnum {
	public static void run(String fqn, Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			EnumAdaptor.valueOf((int)value, pvClass.getClass());
		} catch (Exception ex) {
			//エラー処理
			throw new RuntimeException("Enumの検証　仮置きエクスセプションです。");
		}
	}
}
