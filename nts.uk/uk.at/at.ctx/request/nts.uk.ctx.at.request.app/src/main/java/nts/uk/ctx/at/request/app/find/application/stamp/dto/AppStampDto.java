package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class AppStampDto {
	
	private Long version;
    
	private String appID;
    
    private String applicationDate;
    
    private String detailReason;
    
    private String employeeID;
    
    private String inputEmpID;
	
	private Integer stampRequestMode;
	
	private List<AppStampGoOutPermitDto> appStampGoOutPermitCmds;
	
	private List<AppStampWorkDto> appStampWorkCmds;
	
	private List<AppStampCancelDto> appStampCancelCmds;
	
	private AppStampOnlineRecordDto appStampOnlineRecordCmd;
	
	private String employeeName;
	
	private String inputEmpName;
	
	private String inputDate;
	
	private Boolean reflected;
	
	public static AppStampDto convertToDto(AppStamp appStamp, String employeeName, String inputEmpName){
		if(appStamp == null) return null;
		return new AppStampDto(
				appStamp.getVersion(),
				appStamp.getApplication_New().getAppID(), 
				appStamp.getApplication_New().getAppDate().toString("yyyy/MM/dd"), 
				appStamp.getApplication_New().getAppReason().v(), 
				appStamp.getApplication_New().getEmployeeID(), 
				appStamp.getApplication_New().getEnteredPersonID(), 
				appStamp.getStampRequestMode().value, 
				appStamp.getAppStampGoOutPermits().stream().map(x -> AppStampGoOutPermitDto.convertToDto(x)).collect(Collectors.toList()), 
				appStamp.getAppStampWorks().stream().map(x -> AppStampWorkDto.convertToDto(x)).collect(Collectors.toList()), 
				appStamp.getAppStampCancels().stream().map(x -> AppStampCancelDto.convertToDto(x)).collect(Collectors.toList()), 
				AppStampOnlineRecordDto.convertToDto(appStamp.getAppStampOnlineRecord().orElse(null)),
				employeeName,
				inputEmpName,
				appStamp.getApplication_New().getInputDate().toString("yyyy/MM/dd"),
				appStamp.getApplication_New().getReflectionInformation().getStateReflectionReal()==ReflectedState_New.REFLECTED?true:false);
	}
}

