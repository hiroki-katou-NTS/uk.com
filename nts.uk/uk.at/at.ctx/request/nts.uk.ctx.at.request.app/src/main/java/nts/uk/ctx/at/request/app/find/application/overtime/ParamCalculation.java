package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.ApplicationTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkContentCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetCommand;

@AllArgsConstructor
@NoArgsConstructor
public class ParamCalculation {
	
	public String companyId;
	
	public String employeeId;
	
	public String dateOp;
	
	public Integer prePostInitAtr;
	
	public OvertimeLeaveAppCommonSetCommand overtimeLeaveAppCommonSet;
	
	public ApplicationTimeCommand advanceApplicationTime;
	
	public ApplicationTimeCommand achieveApplicationTime;
	
	public WorkContentCommand workContent;
}
