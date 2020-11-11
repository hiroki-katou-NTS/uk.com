package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.overtime.ApplicationTimeCommand;
import nts.uk.ctx.at.request.app.command.application.overtime.WorkContentCommand;
import nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSetCommand;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * Refactor5
 * @author hoangnd
 *
 */
@AllArgsConstructor
@NoArgsConstructor
public class ParamOverTimeStart {
	
	public String companyId;
	
	public String dateOp;
	
	public Integer overtimeAppAtr;
	
	public AppDispInfoStartupDto appDispInfoStartupDto;
	
	public Integer startTimeSPR;
	
	public Integer endTimeSPR;
	
	public Boolean isProxy;
	
	public String employeeId;
	
	public Integer prePostInitAtr;
	
	public OvertimeLeaveAppCommonSetCommand overtimeLeaveAppCommonSet;
	
	public ApplicationTimeCommand advanceApplicationTime;
	
	public ApplicationTimeCommand achieveApplicationTime;
	
	public WorkContentCommand workContent;
	
}
