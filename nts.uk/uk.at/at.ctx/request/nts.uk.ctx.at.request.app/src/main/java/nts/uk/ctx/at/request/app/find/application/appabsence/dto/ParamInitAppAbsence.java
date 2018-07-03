package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.Data;
@Data
public class ParamInitAppAbsence {
	private String appDate;
	private String employeeID;
	private List<String> employeeIDs;
}
