package nts.uk.ctx.exio.dom.input.importableitem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValue;

/**
 *  値のドメイン制約
 */
@AllArgsConstructor
@Getter
public class DomainConstraint {
	
	private CheckMethod checkMethod;
	private String fqn;

	public boolean validate(Object value) {
		switch(this.checkMethod) {
			case PRIMITIVE_VALUE:
				return validatePrimitiveValue(value);
			case ENUM:
				return validateEnum(value);
			default:
				throw new RuntimeException("チェック方法が定義されていません。:" + this.checkMethod);
		}
	}
	
	private boolean validatePrimitiveValue(Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			PrimitiveValue<?> pv = (PrimitiveValue<?>) pvClass.getConstructors()[0].newInstance(value);
			pv.validate();
			return true;
		} catch (Exception ex) {
			//エラー時処理
			throw new RuntimeException("PrimitiveValueの検証　仮置きエクスセプションです。", ex);
		}
	}
	
	private boolean validateEnum(Object value) {
		try {
			Class<?> pvClass = Class.forName(fqn);
			EnumAdaptor.valueOf((int)value, pvClass);
			return true;
		} catch (Exception ex) {
			//エラー処理
			throw new RuntimeException("Enumの検証　仮置きエクスセプションです。", ex);
		}
	}
}
