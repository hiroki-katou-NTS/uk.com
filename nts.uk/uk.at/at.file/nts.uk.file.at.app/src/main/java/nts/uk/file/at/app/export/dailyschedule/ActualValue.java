package nts.uk.file.at.app.export.dailyschedule;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 実績値.
 *
 * @author HoangNDH
 */
@Getter
@Setter
public class ActualValue {
	
	/** The Constant INTEGER. */
	public static final int INTEGER = 0;
	
	/** The Constant STRING. */
	public static final int STRING = 1;
	
	/** The Constant BIG_DECIMAL. */
	public static final int BIG_DECIMAL = 2;
	
	/** The Constant DATE. */
	public static final int DATE = 3;
	
	// 勤怠項目ID
	private int attendanceId;
	
	// 値
	private String value;
	
	/** The value type. */
	private int valueType;
	
	/**
	 * Value.
	 *
	 * @param <T> the generic type
	 * @return the t
	 */
	public <T> T value() {
//		if(value == null){
//			return null;
//		}
		switch (this.valueType) {
		case INTEGER:
			return this.value == null || this.value.isEmpty() ? (T) new Integer(0) : (T) new Integer(this.value);
		case STRING:
			return (T) this.value;
		case BIG_DECIMAL:
			return this.value == null || this.value.isEmpty() ? (T) new BigDecimal(0) : (T) new BigDecimal(this.value);
		case DATE:
			return this.value == null || this.value.isEmpty() ? null : (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		default:
			throw new RuntimeException("invalid type: " + this.valueType);
		}
	}

	public ActualValue() {
		super();
	}

	public ActualValue(int attendanceId, String value, int valueType) {
		super();
		this.attendanceId = attendanceId;
		this.value = value;
		this.valueType = valueType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object val) {
		if (val instanceof ActualValue) {
			ActualValue actualValue = (ActualValue) val;
			return this.attendanceId == actualValue.attendanceId && StringUtils.equals(this.value, actualValue.value) && this.valueType == actualValue.valueType;
		}
		return false;
	}
	
}
