package nts.uk.ctx.at.record.pub.dailyattendanceitem;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Data
public class AttendanceItemValue {
	private String value;

	/** reference ValueType */
	private int valueType;
	
	private int itemId;
	
	public AttendanceItemValue(int valueType, Integer itemId, Object value){
		this.valueType = valueType;
		this.itemId = itemId;
		value(value);
	}

	@SuppressWarnings("unchecked")
	public <T> T value() {
		if(value == null || this.value.isEmpty()){
			return null;
		}
		ValueType valueType = EnumAdaptor.valueOf(this.valueType, ValueType.class);
		if (valueType.isInteger()) {
			return (T) new Integer(this.value);
		}
		if (valueType.isBoolean()) {
			return (T) new Boolean(this.value);
		}
		if (valueType.isDate()) {
			return (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		}
		if (valueType.isDouble()) {
			return (T) new Double(this.value);
		}
		if (valueType.isString()) {
			return (T) this.value;
		}
		throw new RuntimeException("invalid type: " + this.valueType);
	}
	
	public void value(Object value){
		this.value = value == null ? null : value.toString();
	}
}
