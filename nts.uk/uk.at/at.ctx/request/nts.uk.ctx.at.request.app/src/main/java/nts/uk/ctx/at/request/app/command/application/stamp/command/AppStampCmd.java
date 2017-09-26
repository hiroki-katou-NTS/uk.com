package nts.uk.ctx.at.request.app.command.application.stamp.command;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.app.command.application.common.appapprovalphase.AppApprovalPhaseCmd;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampCmd {
	
	private String appID;
    
    private String inputDate;
    
    private String enteredPerson;
    
    private String applicationDate;
    
    private String titleReason;
    
    private String detailReason;
    
    private String employeeID;
	
	private Integer stampRequestMode;
	
	private List<AppStampGoOutPermitCmd> appStampGoOutPermitCmds;
	
	private List<AppStampWorkCmd> appStampWorkCmds;
	
	private List<AppStampCancelCmd> appStampCancelCmds;
	
	private AppStampOnlineRecordCmd appStampOnlineRecordCmd;
	
	private List<AppApprovalPhaseCmd> appApprovalPhaseCmds;
}

