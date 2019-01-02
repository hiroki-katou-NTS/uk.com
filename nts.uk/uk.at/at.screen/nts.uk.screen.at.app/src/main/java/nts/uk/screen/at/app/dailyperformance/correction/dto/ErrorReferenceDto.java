package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReferenceDto {
	private String id;
	private String employeeId;
	private String employeeCode;
	private String employeeName;
	private GeneralDate date;
	private String errorCode;
	private String message;
	private Integer itemId;
	private String itemName;
	private boolean boldAtr;
	private String messageColor;
	private String submitedName;
	
	public ErrorReferenceDto(String id, String employeeId, GeneralDate date, String errorCode, String message){
		this.id = id;
		this.employeeId = employeeId;
		this.date = date;
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public ErrorReferenceDto(String id, String employeeId, GeneralDate date, String errorCode, String message, boolean boldAtr, String messageColor, String submitedName){
		this.id = id;
		this.employeeId = employeeId;
		this.date = date;
		this.errorCode = errorCode;
		this.message = message;
		this.boldAtr = boldAtr;
		this.messageColor = messageColor;
		this.submitedName = submitedName;
	}
}
