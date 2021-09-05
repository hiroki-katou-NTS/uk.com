package nts.uk.ctx.exio.dom.input.importableitem;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import nts.arc.enums.EnumAdaptor;
import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.HalfIntegerPrimitiveValue;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.gul.reflection.ClassReflection;
import nts.uk.ctx.exio.dom.input.errors.ErrorMessage;

/**
 * 受入値の検証方法
 */
@RequiredArgsConstructor
public enum CheckMethod {
	
	PRIMITIVE_VALUE(1) {
		@Override
		@SneakyThrows
		public Optional<ErrorMessage> validate(Class<?> pvClass, Object value) {
			
			if (value == null) {
				return Optional.empty();
			}
			
			if (ClassReflection.isSubclass(pvClass, IntegerPrimitiveValue.class)) {
				value = Integer.parseInt(value.toString());
			}
			else if (ClassReflection.isSubclass(pvClass, DecimalPrimitiveValue.class)) {
				value = new BigDecimal(value.toString());
			}
			else if (ClassReflection.isSubclass(pvClass, HalfIntegerPrimitiveValue.class)) {
				value = new Double(value.toString());
			}
			
			PrimitiveValue<?> pv = (PrimitiveValue<?>) pvClass.getConstructors()[0].newInstance(value);
			try {
				pv.validate();
			} catch (Exception ex) {
				return Optional.of(new ErrorMessage("受入データが正しくありません。"));
			}
			
			return Optional.empty();
		}
	},
	
	ENUM(2) {
		@Override
		public Optional<ErrorMessage> validate(Class<?> pvClass, Object value) {
			
			if (value == null) {
				return Optional.empty();
			}
			
			try {
				value = Integer.parseInt(value.toString());
				EnumAdaptor.valueOf((int)value, pvClass);
			} catch (Exception ex) {
				return Optional.of(new ErrorMessage("受入データが正しくありません。"));
			}

			return Optional.empty();
		}
	},
	;
	
	public final int value;
	
	public abstract Optional<ErrorMessage> validate(Class<?> pvClass, Object value);
}
