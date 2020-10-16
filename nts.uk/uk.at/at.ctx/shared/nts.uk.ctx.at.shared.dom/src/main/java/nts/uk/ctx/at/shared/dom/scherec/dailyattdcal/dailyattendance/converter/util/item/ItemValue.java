package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item;

import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeFormatterBuilder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.arc.time.GeneralDate;

//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
@EqualsAndHashCode(of = {"itemId", "valueType", "value"})
public class ItemValue implements Cloneable {
	
	@Override
	public ItemValue clone() {
		return new ItemValue(value, valueType, layoutCode, itemId, pathLink, false);
	}

	private static final String DATE_FORMAT = "yyyyMMdd";

	@Getter
	private String value;

	@Getter
	private ValueType valueType;

	@Getter
	private String layoutCode;

	@Getter
	private int itemId;

	@Getter
	private String pathLink;
	
	private boolean isFixed;
	
	private ItemValue(int itemId, String path){
		this.itemId = itemId;
		this.pathLink = path;
	}
	
	public ItemValue() {
	}
	
	public ItemValue(Object value, ValueType valueType, String layoutCode, int itemId){
		this(value, valueType, layoutCode, itemId, "", false);
	}
	
	public ItemValue(Object value, ValueType valueType, String layoutCode, int itemId, String path){
		this(value, valueType, layoutCode, itemId, path, false);
	}
	
	private ItemValue(Object value, ValueType valueType, String layoutCode, int itemId, String pathLink, boolean isFixed){
		this.valueType = valueType;
		this.layoutCode = layoutCode;
		this.itemId = itemId;
		this.pathLink = pathLink;
		this.isFixed = isFixed;
		value(value);
	}
	
	public ItemValue beFixedItem() {
		this.isFixed = true;
		return this;
	}
	
	public ValueType type() {
		return this.valueType;
	}

	@SuppressWarnings("unchecked")
	public <T> T value() {
		if(!isHaveValue()){
			return null;
		}
		if (this.valueType.isInteger()) {
			return (T) new Integer(this.value);
		}
		if (this.valueType.isBoolean()) {
			return (T) new Boolean(this.value);
		}
		if (this.valueType.isDate()) {
			return (T) GeneralDate.fromString(this.value, DATE_FORMAT);
		}
		if (this.valueType.isDouble()) {
			return (T) new Double(this.value);
		}
		if (this.valueType.isString()) {
			return (T) this.value;
		}
		throw new RuntimeException("invalid type: " + this.valueType);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T valueOrDefault() {
		if (this.valueType.isInteger()) {
			return (T) getIntOrDefault();
		}
		if (this.valueType.isBoolean()) {
			return (T) getBooleanOrDefault();
		}
		if (this.valueType.isDate()) {
			return (T) getDateOrDefault();
		}
		if (this.valueType.isDouble()) {
			return (T) getDoubleOrDefault();
		}
		if (this.valueType.isString()) {
			return (T) getStringOrDefault();
		}
		throw new RuntimeException("invalid type: " + this.valueType);
	}
	
	public Integer getIntOrDefault() {
		return isHaveValue() ? new Integer(this.value) : 0;
	}
	
	public String getStringOrDefault() {
		return isHaveValue() ? this.value : "";
	}
	
	public Boolean getBooleanOrDefault() {
		return isHaveValue() ? new Boolean(this.value) : false;
	}
	
	public GeneralDate getDateOrDefault() {
		return isHaveValue() ? GeneralDate.fromString(this.value, DATE_FORMAT) : null;
	}
	
	public Double getDoubleOrDefault() {
		return isHaveValue() ? new Double(this.value) : 0;
	}

	private boolean isHaveValue() {
		return value != null && !this.value.isEmpty();
	}
	
	public Object valueAsObjet() {
		return value;
	}
	
	public <T> T valueOrDefault(T defaultVal) {
		if(!isHaveValue()){
			return defaultVal;
		}
		return value();
	}
	
	public ItemValue value(Object value) {
		if (this.isFixed) {
			return this;
		}
		this.value = value == null ? null : toValue(value);
		return this;
	}

	/**
	 * @param value
	 * @return
	 */
	private String toValue(Object value) {
		if(value instanceof GeneralDate) {
			return ((GeneralDate) value).localDate().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
		}
		return value.toString();
	}
	
	public ItemValue withPath(String path){
		if (this.isFixed) {
			return this;
		}
		this.pathLink = path;
		return this;
	}
	
	public ItemValue valueType(ValueType type){
		if (this.isFixed) {
			return this;
		}
		this.valueType = type;
		return this;
	}
	
	public ItemValue layout(String layoutCode){
		if (this.isFixed) {
			return this;
		}
		this.layoutCode = layoutCode;
		return this;
	}
	
	public ItemValue itemId(int itemId){
		if (this.isFixed) {
			return this;
		}
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
