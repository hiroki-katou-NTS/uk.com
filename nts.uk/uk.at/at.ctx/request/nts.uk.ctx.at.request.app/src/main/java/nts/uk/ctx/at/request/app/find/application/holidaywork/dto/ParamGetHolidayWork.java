package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import java.util.List;

import lombok.Data;

@Data
public class ParamGetHolidayWork {
	private String appDate;
	private int uiType;
	private List<String> lstEmployee;
	private Integer payoutType;
	private String employeeID;
}
