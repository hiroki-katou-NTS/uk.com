package nts.uk.ctx.exio.dom.input.importableitem;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.PrimitiveValue;

/**
 * 受入値の検証方法
 */
@RequiredArgsConstructor
public enum CheckMethod {
	
	PRIMITIVE_VALUE(1) {
		@Override
		public boolean validate(Class<?> pvClass, Object value) {
			try {
				PrimitiveValue<?> pv = (PrimitiveValue<?>) pvClass.getConstructors()[0].newInstance(value);
				pv.validate();
				return true;
			} catch (Exception ex) {
				//エラー時処理
				throw new RuntimeException("PrimitiveValueの検証　仮置きエクスセプションです。", ex);
			}
		}
	},
	
	ENUM(2) {
		@Override
		public boolean validate(Class<?> pvClass, Object value) {
			try {
				EnumAdaptor.valueOf((int)value, pvClass);
				return true;
			} catch (Exception ex) {
				//エラー処理
				throw new RuntimeException("Enumの検証　仮置きエクスセプションです。", ex);
			}
		}
	},
	;
	
	public final int value;
	
	public abstract boolean validate(Class<?> pvClass, Object value);
}
