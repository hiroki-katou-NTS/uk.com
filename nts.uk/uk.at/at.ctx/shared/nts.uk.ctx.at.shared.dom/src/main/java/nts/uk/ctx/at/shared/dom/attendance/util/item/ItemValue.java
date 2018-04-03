package nts.uk.ctx.at.shared.dom.attendance.util.item;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ItemValue {

	private String value;

	private ValueType valueType;

	private String layoutCode;
	
	private int itemId;
	
	private String pathLink;
	
	private ItemValue(int itemId, String path){
		this.itemId = itemId;
		this.pathLink = path;
	}
	
	public ItemValue(Object value, ValueType valueType, String layoutCode, Integer itemId){
		this(valueType, layoutCode, itemId, value);
	}
	
	public ItemValue(ValueType valueType, String layoutCode, Integer itemId, Object value){
		this.valueType = valueType;
		this.layoutCode = layoutCode;
		this.itemId = itemId;
		value(value);
	}

	@SuppressWarnings("unchecked")
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
	
	public ItemValue value(Object value){
		this.value = value == null ? null : value.toString();
		return this;
	}
	
	public ItemValue withPath(String path){
		this.pathLink = path;
		return this;
	}
	
	public ItemValue valueType(ValueType type){
		this.valueType = type;
		return this;
	}
	public ItemValue layout(String layoutCode){
		this.layoutCode = layoutCode;
		return this;
	}
	
	public ItemValue itemId(int itemId){
		this.itemId = itemId;
		return this;
	}
	
	public ItemValue completed(){
		return this;
	}
	
	public int itemId(){
		return this.itemId;
	}
	
	public String layoutCode(){
		return this.layoutCode;
	}
	
	public String path(){
		return this.pathLink;
	}
	
	public static ItemValue builder(){
		return new ItemValue();
	}
	
	public static ItemValue build(String path, int id){
		return new ItemValue(id, path);
	}
	
}
