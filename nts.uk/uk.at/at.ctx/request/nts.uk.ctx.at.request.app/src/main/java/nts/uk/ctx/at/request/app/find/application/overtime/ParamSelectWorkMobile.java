package nts.uk.ctx.at.request.app.find.application.overtime;


import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetCommand;
import nts.uk.ctx.at.request.app.find.application.common.service.other.output.ActualContentDisplayDto;

public class ParamSelectWorkMobile {
	
	public String companyId;
	
	public String employeeId;
	
	public String dateOp;
	
	public String workTypeCode;
	
	public String workTimeCode;
	
	public Integer startTimeSPR;
	
	public Integer endTimeSPR;
	
	public ActualContentDisplayDto actualContentDisplay;
	
	public OvertimeAppSetCommand overtimeAppSet;
}
