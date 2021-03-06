package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Collections;
import java.util.List;

import nts.uk.ctx.at.request.app.command.application.common.AppDispInfoStartupCmd;
import nts.uk.ctx.at.request.app.command.application.overtime.DisplayInfoOverTimeCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime.OvertimeAppSetCommand;
import nts.uk.ctx.at.shared.app.command.worktype.WorkTypeCommandBase;

public class ParamOverTimeChangeDate {
	
	public String companyId;
	
	public String dateOp;
	
	public Integer overtimeAppAtr;
	
	public String employeeId;
	
	public AppDispInfoStartupCmd appDispInfoStartupDto;
	
	public Integer startTimeSPR;
	
	public Integer endTimeSPR;
	
	public OvertimeAppSetCommand overTimeAppSet;
	
	public List<WorkTypeCommandBase> worktypes = Collections.emptyList();
	
	public Integer prePost;
	
	public DisplayInfoOverTimeCommand displayInfoOverTime;
	
	public Boolean agent;
	
	
}
