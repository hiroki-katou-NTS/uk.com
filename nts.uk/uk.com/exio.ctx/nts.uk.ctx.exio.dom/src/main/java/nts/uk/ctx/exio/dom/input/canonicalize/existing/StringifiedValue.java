package nts.uk.ctx.exio.dom.input.canonicalize.existing;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.input.importableitem.ItemType;

/**
 * 色々な型の値を文字列表現する
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StringifiedValue {

	@Getter
	private final String value;
	
	private static final String DATE_FORMAT = "yyyyMMdd";
	
	private static final String DATETIME_FORMAT = "yyyyMMdd HHmmss";
	
	public static StringifiedValue nullValue() {
		return new StringifiedValue(null);
	}
	
	public static StringifiedValue of(String value) {
		if (value == null) return nullValue();
		return new StringifiedValue(value);
	}
	
	public static StringifiedValue of(Integer value) {
		if (value == null) return nullValue();
		return of(value.toString());
	}
	
	public static StringifiedValue of(Long value) {
		if (value == null) return nullValue();
		return of(value.toString());
	}
	
	public static StringifiedValue of(Double value) {
		if (value == null) return nullValue();
		return of(value.toString());
	}
	
	public static StringifiedValue of(BigDecimal value) {
		if (value == null) return nullValue();
		return of(value.toString());
	}
	
	public static StringifiedValue of(GeneralDate value) {
		if (value == null) return nullValue();
		return of(value.toString(DATE_FORMAT));
	}
	
	public static StringifiedValue of(GeneralDateTime value) {
		if (value == null) return nullValue();
		return of(value.toString(DATETIME_FORMAT));
	}
	
	public static StringifiedValue create(Object value) {
		
		if (value == null) {
			return nullValue();
		}
		
		if (value instanceof String) {
			return of((String) value);
		}
		
		if (value instanceof Integer) {
			return of((Integer) value);
		}
		
		if (value instanceof Long) {
			return of((Long) value);
		}
		
		if (value instanceof Double) {
			return of((Double) value);
		}
		
		if (value instanceof BigDecimal) {
			return of((BigDecimal) value);
		}
		
		if (value instanceof GeneralDate) {
			return of((GeneralDate) value);
		}
		
		if (value instanceof GeneralDateTime) {
			return of((GeneralDateTime) value);
		}
		
		throw new RuntimeException("not supported: " + value);
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public String asString() {
		return value;
	}
	
	public Integer asInteger() {
		if (value == null) return null;
		return Integer.parseInt(value);
	}
	
	public Long asLong() {
		if (value == null) return null;
		return Long.parseLong(value);
	}
	
	public Double asDouble() {
		if (value == null) return null;
		return Double.parseDouble(value);
	}
	
	public BigDecimal asBigDecimal() {
		if (value == null) return null;
		return new BigDecimal(value);
	}
	
	public GeneralDate asGeneralDate() {
		if (value == null) return null;
		return GeneralDate.fromString(value, DATE_FORMAT);
	}
	
	public Object asGeneralDateTime() {
		if (value == null) return null;
		return GeneralDateTime.fromString(value, DATETIME_FORMAT);
	}
	
	public Object asTypeOf(ItemType type) {
		switch (type) {
		case STRING: return asString();
		case REAL: return asBigDecimal();
		case INT:
		case TIME_DURATION:
		case TIME_POINT:
			return asLong();
		case DATE: return asGeneralDate();
		default: throw new RuntimeException("unknown: " + type);
		}
	}


}
