package nts.uk.ctx.at.shared.app.util.attendanceitem.type;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@RequiredArgsConstructor
public class ItemValue {

	private String value;

	@Getter
	private final ValueType valueType;

	@Getter
	private final String layoutCode;
	
	@Getter
	private final Integer itemId;
	
	private String pathLink;
	
	public ItemValue(Object value, ValueType valueType, String layoutCode, Integer itemId){
		this(valueType, layoutCode, itemId, value);
	}
	
	public ItemValue(ValueType valueType, String layoutCode, Integer itemId, Object value){
		value(value);
		this.valueType = valueType;
		this.layoutCode = layoutCode;
		this.itemId = itemId;
	}

	@SuppressWarnings("unchecked")
	public <T> T value() {
		if(value == null){
			return null;
		}
		switch (this.valueType) {
		case INTEGER:
			return this.value == null || this.value.isEmpty() ? null : (T) new Integer(this.value);
		case STRING:
			return (T) this.value;
		case DECIMAL:
			return this.value == null || this.value.isEmpty() ? null : (T) new BigDecimal(this.value);
		case DATE:
			return this.value == null || this.value.isEmpty() ? null : (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		default:
			throw new RuntimeException("invalid type: " + this.valueType);
		}
	}
	
	public void value(Object value){
		this.value = value == null ? null : value.toString();
	}
	
	public void withPath(String path){
		this.pathLink = path;
	}
	
	public String path(){
		return this.pathLink;
	}
}
