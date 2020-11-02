package nts.uk.ctx.at.function.dom.adapter.monthlyattendanceitem;

//import java.math.BigDecimal;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;

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

	public void value(Object value){
		this.value = value == null ? null : value.toString();
	}

	public boolean isNumber() {
		ValueType valueType = EnumAdaptor.valueOf(this.valueType, ValueType.class);
		return valueType.isInteger() || valueType.isDouble();
	}
}
