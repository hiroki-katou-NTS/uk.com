package nts.uk.ctx.at.request.app.find.application.appabsence.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
@Data
public class ParamInitAppAbsence {
	private String[] appDate;
	private String employeeID;
	private List<String> employeeIDs;
	// KAF006_ver35
	private AppDispInfoStartupDto appDispInfo;
}
