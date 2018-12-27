package nts.uk.ctx.at.record.pub.dailyattendanceitem;

import lombok.Data;

@Data
public class AttendanceItemValue {
	private Object value;

	/** reference ValueType */
	private int valueType;
	
	private int itemId;
	
	public AttendanceItemValue(int valueType, Integer itemId, Object value){
		this.valueType = valueType;
		this.itemId = itemId;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public <T> T value() {
		return (T) value;
	}
	
	public String getValue() {
		return this.value == null ? null : this.value.toString();
	}
}
