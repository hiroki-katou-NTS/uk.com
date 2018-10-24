package nts.uk.ctx.at.request.pub.application.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class ApplicationPeriodInfo {
	
	private String employeeID;
	
	private GeneralDate appDate;
	
	private Integer appType;
	
	private AppDispNameExport appDispName;
	
	private Integer reflectState;
	
}
