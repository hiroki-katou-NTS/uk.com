package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@Data
@NoArgsConstructor
public class DPItemValue {
	
	private String rowId;
	
	private String columnKey;
	
	private int itemId;
	
	private String value;
	
	private String valueType;
	
	private String layoutCode;
	
	private String employeeId;
	
	private GeneralDate date;
	
	private Integer typeGroup;
	
	private String message;
	
	public DPItemValue(String rowId, String employeeId, GeneralDate date, int itemId){
		this.rowId = rowId;
		this.employeeId = employeeId;
		this.date = date;
		this.itemId = itemId;
	}
	
	public DPItemValue(String rowId, String employeeId, GeneralDate date, int itemId, String value, String message){
		this.rowId = rowId;
		this.employeeId = employeeId;
		this.date = date;
		this.itemId = itemId;
		this.value = value;
		this.valueType = message;
	}

	public DPItemValue(String rowId, String columnKey, int itemId, String value, String valueType, String layoutCode,
			String employeeId, GeneralDate date, Integer typeGroup) {
		super();
		this.rowId = rowId;
		this.columnKey = columnKey;
		this.itemId = itemId;
		this.value = value;
		this.valueType = valueType;
		this.layoutCode = layoutCode;
		this.employeeId = employeeId;
		this.date = date;
		this.typeGroup = typeGroup;
	}
	
	public DPItemValue(String employeeId, String message){
		this.employeeId = employeeId;
		this.message = message;
	}
}
