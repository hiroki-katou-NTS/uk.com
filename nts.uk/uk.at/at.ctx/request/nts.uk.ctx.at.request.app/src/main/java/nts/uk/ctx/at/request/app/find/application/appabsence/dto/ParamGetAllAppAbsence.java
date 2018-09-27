package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParamGetAllAppAbsence {
	private String startAppDate;
	private String endAppDate;
	private String employeeID;
	private boolean displayHalfDayValue;
	private Integer holidayType;
	private int alldayHalfDay;
	private String workTypeCode;
	private int prePostAtr;
	private String workTimeCode;
	private String relationCD;
}
