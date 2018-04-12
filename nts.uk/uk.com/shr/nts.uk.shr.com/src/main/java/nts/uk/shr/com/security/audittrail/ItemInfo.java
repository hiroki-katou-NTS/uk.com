package nts.uk.shr.com.security.audittrail;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
 * 項目情報
 */
@RequiredArgsConstructor
public class ItemInfo {

	/** 項目名 */
	@Getter
	private final String name;
	
	/** 修正前 */
	@Getter
	private final String rawValueBefore;
	
	/** 修正後 */
	@Getter
	private final String rawValueAfter;
	
	
	public static ItemInfo of(String name, int valueBefore, int valueAfter) {
		return new ItemInfo(name, String.valueOf(valueBefore), String.valueOf(valueAfter));
	}
	
	public static ItemInfo of(String name, IntegerPrimitiveValue<?> valueBefore,  IntegerPrimitiveValue<?> valueAfter) {
		return of(name, valueBefore.v(), valueAfter.v());
	}
	
	@RequiredArgsConstructor
	public static class Value {
		
		private final RawValue rawValue;
		private final String textValue;
	
	}
	
	@RequiredArgsConstructor
	@Getter
	public static class RawValue {
		
		private final String asString;
		private final Integer asInt;
		private final Double asDouble;
		private final BigDecimal asDecimal;
		private final GeneralDate asDate;
		
		@RequiredArgsConstructor
		public enum Type {
			STRING(1),
			INTEGER(2),
			DOUBLE(3),
			DECIMAL(4),
			DATE(5),
			;
			public final int value;
		}
	}
}
