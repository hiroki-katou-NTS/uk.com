package nts.uk.ctx.exio.dom.input.validation.classtype;

import nts.arc.primitive.PrimitiveValue;

public class ValidationPrimitiveValue {
	
	public static void run(String fqn, Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			PrimitiveValue<?> pv = (PrimitiveValue<?>) pvClass.getConstructors()[0].newInstance(value);
			pv.validate();
		} catch (Exception ex) {
			//エラー時処理
		}
	}
}
