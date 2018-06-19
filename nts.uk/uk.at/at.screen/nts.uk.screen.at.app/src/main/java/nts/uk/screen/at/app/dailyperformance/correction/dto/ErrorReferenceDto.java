package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@AllArgsConstructor
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
}
