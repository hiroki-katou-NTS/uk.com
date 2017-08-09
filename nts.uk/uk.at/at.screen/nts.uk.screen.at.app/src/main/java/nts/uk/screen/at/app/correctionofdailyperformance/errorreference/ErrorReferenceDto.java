package nts.uk.screen.at.app.correctionofdailyperformance.errorreference;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ErrorReferenceDto {
	public String idAuto;
	public String employeeCode;
	public String employeeName;
	public GeneralDate date;
	public String code;
	public String message;
	public String itemName;
}
