package nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable;

import lombok.Data;

@Data
public class AttendanceItemDtoValue {
    private Object value;

    /** reference ValueType */
    private int valueType;

    private int itemId;

    public AttendanceItemDtoValue(int valueType, Integer itemId, Object value){
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
