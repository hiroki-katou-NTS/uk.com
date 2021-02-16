package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParamGetAllAppAbsence {
//	private String startAppDate;
//	private String endAppDate;
//	private boolean displayHalfDayValue;
//	private Integer holidayType;
//	private int alldayHalfDay;
//	private int prePostAtr;
//	private String relationCD;
//    private String employeeID;
    private String date;
	private String workTypeCode;
	private String workTimeCode;
	private AppAbsenceStartInfoDto appAbsenceStartInfoDto;
}
