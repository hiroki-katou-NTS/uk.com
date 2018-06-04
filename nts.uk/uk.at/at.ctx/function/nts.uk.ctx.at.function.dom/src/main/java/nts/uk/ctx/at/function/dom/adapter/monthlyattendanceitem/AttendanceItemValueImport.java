package nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem;

import java.math.BigDecimal;

import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
public class AttendanceItemValueImport {
	private String value;

	/** INTEGER(0, "INTEGER", "回数、時間、時刻"),
	STRING(1, "STRING", "コード、文字"),
	DECIMAL(2, "DECIMAL", "回数"),
	DATE(3, "DATE", "年月日"),
	BOOLEAN(4, "BOOLEAN", "年月日"),
	DOUBLE(5, "DOUBLE", "日数") */
	private int valueType;
	
	private int itemId;
	
	public AttendanceItemValueImport(int valueType, Integer itemId, Object value) {
		this.valueType = valueType;
		this.itemId = itemId;
		value(value);
	}

	@SuppressWarnings("unchecked")
	public <T> T value() {
		if(value == null){
			return null;
		}
		switch (this.valueType) {
		case 0:
			return this.value == null || this.value.isEmpty() ? null : (T) new Integer(this.value);
		case 1:
			return (T) this.value;
		case 2:
			return this.value == null || this.value.isEmpty() ? null : (T) new BigDecimal(this.value);
		case 3:
			return this.value == null || this.value.isEmpty() ? null : (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		case 4:
			return this.value == null || this.value.isEmpty() ? null : (T) Boolean.valueOf(this.value);
		case 5:
			return this.value == null || this.value.isEmpty() ? null : (T) new Double(this.value);
		default:
			throw new RuntimeException("invalid type: " + this.valueType);
		}
	}
	
	public void value(Object value){
		this.value = value == null ? null : value.toString();
	}

	public boolean isNumber() {
		return valueType == 0 || valueType == 2 || valueType == 5;
	}
}
