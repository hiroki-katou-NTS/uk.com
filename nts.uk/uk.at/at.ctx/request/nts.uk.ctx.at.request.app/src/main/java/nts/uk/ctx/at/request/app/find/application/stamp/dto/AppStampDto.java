package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.List;

import lombok.Data;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
public class AppStampDto {
    
    private String inputDate;
    
    private String enteredPerson;
    
    private String applicationDate;
    
    private String applicationReason;
    
    private String employeeID;
	
	private Integer stampRequestMode;
	
	private List<AppStampGoOutPermitDto> appStampGoOutPermitCmds;
	
	private List<AppStampWorkDto> appStampWorkCmds;
	
	private List<AppStampCancelDto> appStampCancelCmds;
	
	private AppStampOnlineRecordDto appStampOnlineRecordCmd;
}

