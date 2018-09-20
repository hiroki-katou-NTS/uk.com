package nts.uk.ctx.at.shared.dom.attendance.util.item;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"itemId", "valueType", "value"})
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
		if(value == null || this.value.isEmpty()){
			return null;
		}
		if (this.valueType.isInteger()) {
			return (T) new Integer(this.value);
		}
		if (this.valueType.isBoolean()) {
			return (T) new Boolean(this.value);
		}
		if (this.valueType.isDate()) {
			return (T) GeneralDate.fromString(this.value, "yyyyMMdd");
		}
		if (this.valueType.isDouble()) {
			return (T) new Double(this.value);
		}
		if (this.valueType.isString()) {
			return (T) this.value;
		}
		throw new RuntimeException("invalid type: " + this.valueType);
	}
	
	public Object valueAsObjet() {
		return value;
	}
	
	public <T> T valueOrDefault(T defaultVal) {
		if(value == null || this.value.isEmpty()){
			return defaultVal;
		}
		return value();
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
