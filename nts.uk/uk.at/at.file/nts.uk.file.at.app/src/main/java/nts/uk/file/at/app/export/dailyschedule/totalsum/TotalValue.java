package nts.uk.file.at.app.export.dailyschedule.totalsum;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 合算値
 * @author HoangNDH
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotalValue {
	/** The Constant INTEGER. */
	public static final int INTEGER = 0;
	
	/** The Constant STRING. */
	public static final int STRING = 1;
	
	/** The Constant BIG_DECIMAL. */
	public static final int DECIMAL = 2;
	
	/** The Constant DATE. */
	public static final int DATE = 3;
	
	/** The Constant STRING. */
	public static final int BOOLEAN = 4;
	
	/** The Constant BIG_DECIMAL. */
	public static final int DOUBLE = 5;
	
	
	// 勤怠項目ID
	private int attendanceId;
	// 値
	private String value;
	
	private int valueType;
	
	/**
	 * Value.
	 *
	 * @param <T> the generic type
	 * @return the t
	 */
	public <T> T value() {
		if(value == null){
			return null;
		}
		switch (this.valueType) {
		case INTEGER:
			return this.value.isEmpty() ? null : (T) new Integer(this.value);
		case STRING:
			return (T) this.value;
		case DOUBLE:
			return this.value.isEmpty() ? null : (T) new Double(this.value);
		case DECIMAL:
			return this.value.isEmpty() ? null : (T) new BigDecimal(this.value);
		case DATE:
			return this.value.isEmpty() ? null : (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		default:
			throw new RuntimeException("invalid type: " + this.valueType);
		}
	}
}
